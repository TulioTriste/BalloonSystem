package me.arjona.balloons;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;

@Getter
public class Balloon {

    private final Player player;
    private final Heads head = Heads.BLUE_BALLOON;

    private final BatEntity bat;
    private final Slime slime;
    private final ArmorStand armorStand;

    private final boolean active = false;

    public Balloon(Main plugin, Player player) {
        this.player = player;

        this.bat = BatEntity.spawn(player);
        this.slime = player.getWorld().spawn(player.getLocation(), Slime.class);
        this.armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);

        Bat batEntity = (Bat) bat.getBukkitEntity();

        bat.setCustomNameVisible(false);
        batEntity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));

        player.sendMessage("aaaa");

        // set pathfinder
        /*EntityInsentient batEntityIns = ((EntityInsentient)((CraftEntity)batEntity).getHandle());
        batEntityIns.goalSelector.a(1, new PathfinderFlyer(batEntityIns, (EntityLiving) ((CraftEntity)player).getHandle()));*/

        //((EntityInsentient)((CraftEntity)bat).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.001D);

        slime.setCustomNameVisible(false);
        slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        slime.setSize(-2);

        armorStand.setCustomNameVisible(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        armorStand.setHelmet(plugin.getBalloonSkull(head));

        slime.setPassenger(armorStand);
        batEntity.setPassenger(slime);

        /*NBTTagCompound nbtNoAi = new NBTTagCompound();
        nbtNoAi.setBoolean("noAI", true);

        ((CraftEntity)slime).getHandle().f(nbtNoAi);*/

        /*NBTTagCompound nbtSilent = new NBTTagCompound();
        nbtSilent.setBoolean("Silent", true);

        //((CraftEntity)slime).getHandle().f(nbtSilent);

        nbtSilent.setFloat("flySpeed", 0.05F);
        ((CraftEntity)bat).getHandle().f(nbtSilent);*/
        //((EntityInsentient) ((CraftEntity)bat).getHandle()).a(nbtSilent);

        batEntity.setLeashHolder(player);
    }

    public boolean tick() {
        if (bat.getBukkitEntity().isDead() || slime.isDead() || armorStand.isDead()) {
            die();
            return false;
        }


        return true;
    }

    public void die() {
        bat.getBukkitEntity().remove();
        slime.remove();
        armorStand.remove();
    }
}
