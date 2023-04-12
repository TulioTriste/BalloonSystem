package me.arjona.balloons;

import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

public class BatEntity extends EntityBat {

    private final Player owner;

    public BatEntity(World world, Player owner) {
        super(world);
        this.owner = owner;

        this.noclip = true;
    }

    @Override
    public void move(double d0, double d1, double d2) {
        // set owner location
        if (owner != null && !isNear()) {
            this.getBukkitEntity().setVelocity(owner.getLocation().toVector().subtract(this.getBukkitEntity().getLocation().toVector()).normalize().multiply(0.5));
            System.out.printf("move");
            return;
        }
        super.move(owner.getLocation().getX(), owner.getLocation().getY(), owner.getLocation().getZ());
    }

    // follow the owner with override methods
    @Override
    public void g(double d0, double d1, double d2) {
        if (owner != null && !isNear()) {
            this.getBukkitEntity().setVelocity(owner.getLocation().toVector().subtract(this.getBukkitEntity().getLocation().toVector()).normalize().multiply(0.5));
            System.out.printf("g");
            return;
        }
        super.g(d0, d1, d2);
    }


    public static BatEntity spawn(Player player) {
        Location location = player.getLocation();
        World mcWorld = ((CraftWorld) location.getWorld()).getHandle();
        BatEntity batEntity = new BatEntity(mcWorld, player);
        batEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        mcWorld.addEntity(batEntity);
        return batEntity;
    }

    // is near to the owner
    public boolean isNear() {
        return getBukkitEntity().getLocation().distanceSquared(owner.getLocation()) < 5;
    }
}
