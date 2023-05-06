package me.arjona.balloons.impl;

import lombok.Getter;
import me.arjona.balloons.Main;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

@Getter
public class Balloon {

    private final Player player;
    private final ArmorStand armorStand;

    private final boolean active = false;

    private Body body = new Body(Heads.BLUE_BALLOON, null);

    public Balloon(Main plugin, Player player, @Nullable Body body) {
        this.player = player;

        if (body != null) this.body = body;

        this.armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);

        // invulnerable
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean("Invulnerable", true);
        ((CraftEntity) armorStand).getHandle().f(nbtTagCompound);

        armorStand.setCustomNameVisible(false);
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        armorStand.setBasePlate(false);

        this.body.apply(plugin, this);
    }

    public boolean tick(Main plugin) {
        if (armorStand.isDead()) {
            die(plugin);
            return false;
        }

        armorStand.teleport(calcLoc(player).join());
        return true;
    }

    private CompletableFuture<Location> calcLoc(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            Location location = player.getLocation();
            double x = location.getX(), y = location.getY(), z = location.getZ();
            float yaw = location.getYaw();

            // Calcular la dirección en la que está mirando el jugador
            double radians = Math.toRadians(yaw), xDirection = -Math.sin(radians), zDirection = Math.cos(radians);

            // Calcular la ubicación detrás del jugador
            double teleportX = x + (xDirection * -1), teleportZ = z + (zDirection * -1);

            return new Location(location.getWorld(), teleportX, y, teleportZ, yaw, 0);
        });
    }

    public void die(Main plugin) {
        //plugin.getBalloons().remove(player.getUniqueId());
        armorStand.remove();
    }
}
