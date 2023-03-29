package me.arjona.balloons;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

@Getter
public class BalloonRunnable /*extends BukkitRunnable*/ {

    /*private final Map<UUID, BalloonEntity> balloonEntity;

    public BalloonRunnable(Main plugin) {
        this.balloonEntity = Maps.newHashMap();
    }

    @Override
    public void run() {
        for (Map.Entry<UUID, BalloonEntity> uuidBalloonEntityEntry : balloonEntity.entrySet()) {
            UUID uuid = uuidBalloonEntityEntry.getKey();
            BalloonEntity balloonEntity = uuidBalloonEntityEntry.getValue();

            if (balloonEntity == null || !balloonEntity.isAlive()) {
                this.balloonEntity.remove(uuid);
                return;
            }

            if (Bukkit.getPlayer(uuid) == null) {

                balloonEntity.die();
                this.balloonEntity.remove(uuid);
            }
        }
    }*/
}
