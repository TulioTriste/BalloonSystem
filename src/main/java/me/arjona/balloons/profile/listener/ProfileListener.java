package me.arjona.balloons.profile.listener;

import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.profile.Profile;
import me.arjona.customutilities.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

@RequiredArgsConstructor
public class ProfileListener implements Listener {

    private final Main plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Logger.deb("?");
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) return;

        try {
            Profile profile = new Profile(event.getPlayer().getUniqueId(), event.getPlayer().getName());

            profile.load(plugin);

            Logger.log("Loaded profile for " + profile.getName() + ".");
        } catch (Exception e) {
            e.printStackTrace();
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "An error occurred while loading your profile.");
        }
    }

    @EventHandler
}
