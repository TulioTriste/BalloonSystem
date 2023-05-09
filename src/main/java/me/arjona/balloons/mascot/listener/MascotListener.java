package me.arjona.balloons.mascot.listener;

import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.MascotManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class MascotListener implements Listener {

    private final Main plugin;
    private final MascotManager mascotManager;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (mascotManager.getMascots().containsKey(event.getPlayer().getUniqueId())) {
            mascotManager.getMascots().get(event.getPlayer().getUniqueId()).die();
        }
    }
}
