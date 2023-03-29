package me.arjona.balloons;

import lombok.RequiredArgsConstructor;
import org.bukkit.util.Vector;

@RequiredArgsConstructor
public class EntitiesRunnable implements Runnable{

    private final Main plugin;

    private boolean tick = false;

    @Override
    public void run() {
        /*for (BalloonEntity value : plugin.getBalloonRunnable().getBalloonEntity().values()) {
            if (tick) {
                value.getBukkitEntity().setVelocity(new Vector(0, 0.1, 0));
            } else {
                value.getBukkitEntity().setVelocity(new Vector(0, -0.1, 0));
            }
        }*/
    }
}
