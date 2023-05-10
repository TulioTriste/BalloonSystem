package me.arjona.balloons.mascot.impl;

import lombok.Getter;
import me.arjona.balloons.Main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

@Getter
public class Mascot {

    private final Main plugin;

    private final Player player;
    private final ArmorStand armorStand;

    private final boolean active = false;

    private final Body body;

    private int i = 0;
    private boolean up = true;

    private boolean particle = true;

    public Mascot(Main plugin, Player player, @Nullable Body body) {
        this.plugin = plugin;
        this.player = player;

        if (body != null) this.body = body;
        else this.body = plugin.getMascotManager().getDefaultBody();

        this.armorStand = player.getWorld().spawn(calcLoc(player).join(), ArmorStand.class);

        // invulnerable
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean("Invulnerable", true);
        ((CraftEntity) armorStand).getHandle().f(nbtTagCompound);

        armorStand.setCustomNameVisible(false);
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        armorStand.setBasePlate(false);

        this.body.apply(this);
    }

    public boolean tick() {
        if (armorStand.isDead()) {
            die();
            return false;
        }

        if (i == 10) {
            up = false;
        }
        else if (i == 0) {
            up = true;
        }

        if (up) {
            i++;
        }
        else {
            i--;
        }

        armorStand.teleport(calcLoc(player).join());

        if (particle)
            playParticles().join();

        return true;
    }

    private CompletableFuture<Location> calcLoc(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            Location location = player.getLocation();
            double x = location.getX(), y = location.getY() - 0.35, z = location.getZ();
            float yaw = location.getYaw();

            // Calcular la dirección en la que está mirando el jugador
            double radians = Math.toRadians(yaw), xDirection = -Math.sin(radians), zDirection = Math.cos(radians);

            // Calcular la ubicación detrás del jugador
            double teleportX = x + (xDirection * -1), teleportZ = z + (zDirection * -1);

            // Calcular el movimiento en Y para mas smooth
            double teleportY = y + (i * 0.075);

            return new Location(location.getWorld(), teleportX, teleportY, teleportZ, yaw, 0);
        });
    }

    private CompletableFuture<Void> playParticles() {
        return CompletableFuture.runAsync(() -> {
            Location location = armorStand.getLocation().add(0, 0.3, 0);
            location.getWorld().spigot().playEffect(location, Effect.CLOUD, 0, 0, 0, 0, 0, 0, 1, 1);
        });
    }

    public void die() {
        armorStand.remove();
        plugin.getMascotManager().getMascots().remove(player.getUniqueId());
    }
}
