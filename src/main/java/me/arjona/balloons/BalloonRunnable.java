package me.arjona.balloons;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

@Getter
public class BalloonRunnable extends BukkitRunnable {

    private final Map<UUID, Balloon> balloonEntity;

    public BalloonRunnable(Main plugin) {
        this.balloonEntity = Maps.newHashMap();
    }

    @Override
    public void run() {
        for (Map.Entry<UUID, Balloon> uuidBalloonEntityEntry : balloonEntity.entrySet()) {
            UUID uuid = uuidBalloonEntityEntry.getKey();
            Balloon balloon = uuidBalloonEntityEntry.getValue();

            if (balloon == null) {
                this.balloonEntity.remove(uuid);
                return;
            }

            if (Bukkit.getPlayer(uuid) == null) {
                this.balloonEntity.remove(uuid);
            }

            if (!balloon.tick()) {
                this.balloonEntity.remove(uuid);
            }
        }
    }
}
