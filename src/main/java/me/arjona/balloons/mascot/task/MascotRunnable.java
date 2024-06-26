package me.arjona.balloons.mascot.task;

import lombok.Getter;
import me.arjona.balloons.mascot.MascotManager;
import me.arjona.balloons.mascot.impl.Mascot;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;

@Getter
public class MascotRunnable implements Runnable {

    private final MascotManager mascotManager;

    public MascotRunnable(MascotManager mascotManager) {
        this.mascotManager = mascotManager;
    }

    @Override
    public void run() {
        for (Map.Entry<UUID, Mascot> uuidBalloonEntityEntry : mascotManager.getMascots().entrySet()) {
            UUID uuid = uuidBalloonEntityEntry.getKey();
            Mascot mascot = uuidBalloonEntityEntry.getValue();

            if (mascot == null) {
                mascotManager.getMascots().remove(uuid);
                return;
            }

            if (Bukkit.getPlayer(uuid) == null) {
                mascotManager.getMascots().remove(uuid);
                return;
            }

            if (!mascot.tick()) {
                mascotManager.getMascots().remove(uuid);
            }
        }
    }
}
