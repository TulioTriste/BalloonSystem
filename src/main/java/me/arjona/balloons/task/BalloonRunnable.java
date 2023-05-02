package me.arjona.balloons.task;

import lombok.Getter;
import me.arjona.balloons.impl.Balloon;
import me.arjona.balloons.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

@Getter
public class BalloonRunnable extends BukkitRunnable {

    private final Main plugin;

    public BalloonRunnable(Main plugin) {
        this.plugin = plugin;
        System.out.println("BalloonRunnable created!");
    }

    @Override
    public void run() {
        for (Map.Entry<UUID, Balloon> uuidBalloonEntityEntry : plugin.getBalloons().entrySet()) {
            UUID uuid = uuidBalloonEntityEntry.getKey();
            Balloon balloon = uuidBalloonEntityEntry.getValue();

            if (balloon == null) {
                plugin.getBalloons().remove(uuid);
                return;
            }

            if (Bukkit.getPlayer(uuid) == null) {
                plugin.getBalloons().remove(uuid);
                return;
            }

            if (!balloon.tick()) {
                plugin.getBalloons().remove(uuid);
            }
        }
    }
}
