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

    private final Bat bat;
    private final Slime slime;
    private final ArmorStand armorStand;

    private final boolean active = false;

    public Balloon(Main plugin, Player player) {
        this.player = player;

        this.bat = player.getWorld().spawn(player.getLocation(), Bat.class);
        this.slime = player.getWorld().spawn(player.getLocation(), Slime.class);
        this.armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);

        bat.setCustomNameVisible(false);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));

        // set pathfinder
        /*EntityInsentient entity = ((EntityInsentient)((CraftEntity)bat).getHandle());
        entity.goalSelector.a(1, new PathfinderFlyer(entity, (EntityLiving) ((CraftEntity)player).getHandle()));*/

        /*((EntityInsentient)((CraftEntity)bat).getHandle()).getAttributeInstance(GenericAttributes.)*/
        ((EntityInsentient)((CraftEntity)bat).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);


        slime.setCustomNameVisible(false);asd
        slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        /*slime.setSize(-2);*/
        ((CraftEntity)slime).getHandle().setSize(-2, -2);

        armorStand.setCustomNameVisible(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        armorStand.setHelmet(plugin.getBalloonSkull(head));

        slime.setPassenger(armorStand);
        bat.setPassenger(slime);

        NBTTagCompound nbtNoAi = new NBTTagCompound();
        nbtNoAi.setBoolean("noAI", true);

        ((CraftEntity)slime).getHandle().f(nbtNoAi);

        NBTTagCompound nbtSilent = new NBTTagCompound();
        nbtSilent.setBoolean("Silent", true);

        ((CraftEntity)slime).getHandle().f(nbtSilent);

        nbtSilent.setFloat("flySpeed", 0.05F);
        ((CraftEntity)bat).getHandle().f(nbtSilent);
        ((EntityInsentient) ((CraftEntity)bat).getHandle()).a(nbtSilent);

        bat.setLeashHolder(player);
    }
}
