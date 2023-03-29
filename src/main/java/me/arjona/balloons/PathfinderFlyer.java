package me.arjona.balloons;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathfinderGoal;

public class PathfinderFlyer extends PathfinderGoal {

    private final EntityLiving flyingEntity;
    private EntityLiving target;

    public PathfinderFlyer(EntityLiving entity, EntityLiving target) {
        this.flyingEntity = entity;
        this.target = target;
    }

    @Override
    public boolean a() {
        return true;
    }

    @Override
    public void e() {
        if (target != null) {
            ((EntityInsentient)flyingEntity).getNavigation().a(target, 1.0F);
        } else {
            ((EntityInsentient)flyingEntity).getNavigation().n();
        }

    }

    @Override
    public boolean b() {
        return target != null && !target.dead;
    }

    public void setTarget(EntityLiving target) {
        this.target = target;
    }
}