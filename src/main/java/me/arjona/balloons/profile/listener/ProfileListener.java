package me.arjona.balloons.profile.listener;

import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.impl.Mascot;
import me.arjona.balloons.profile.Profile;
import me.arjona.customutilities.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ProfileListener implements Listener {

    private final Main plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) return;

        try {
            Profile profile = new Profile(event.getPlayer().getUniqueId());

            profile.setName(event.getPlayer().getName());

            profile.load(plugin);

            plugin.getProfileManager().getProfiles().put(event.getPlayer().getUniqueId(), profile);

            Logger.log("Loaded profile for " + profile.getName() + ".");
        } catch (Exception e) {
            e.printStackTrace();
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "An error occurred while loading your profile.");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().getProfile(event.getPlayer().getUniqueId());

        if (profile == null) {
            event.getPlayer().kickPlayer("An error occurred while loading your profile.");
            return;
        }

        if (profile.getMascotBody() != null) {
            plugin.getMascotManager().setMascot(player.getUniqueId(),
                    new Mascot(plugin, player, plugin.getMascotManager().getBody(profile.getMascotBody())));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Profile profile = plugin.getProfileManager().getProfile(event.getPlayer().getUniqueId());

        if (profile == null) return;

        profile.save(plugin);

        Logger.log("Saved profile for " + profile.getName() + ".");
    }
}
