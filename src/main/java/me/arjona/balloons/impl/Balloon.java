package me.arjona.balloons.impl;

import lombok.Getter;
import me.arjona.balloons.Main;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

@Getter
public class Balloon {

    private final Player player;
    private final Heads head = Heads.BLUE_BALLOON;

    private final ArmorStand armorStand;

    private final boolean active = false;

    public Balloon(Main plugin, Player player) {
        this.player = player;

        this.armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);

        armorStand.setCustomNameVisible(false);
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        armorStand.setBasePlate(false);
        armorStand.setHelmet(plugin.getBalloonSkull(head));
    }

    public boolean tick() {
        if (armorStand.isDead()) {
            die();
            player.sendMessage("isdead??????????");
            return false;
        }

        Location pLoc = player.getLocation();

        armorStand.teleport(pLoc.subtract(pLoc.getDirection().normalize().multiply(2).setY(0)));

        return true;
    }

    public void die() {
        armorStand.remove();
    }
}
