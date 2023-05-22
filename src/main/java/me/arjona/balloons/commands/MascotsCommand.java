package me.arjona.balloons.commands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.menu.SelectMascotMenu;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MascotsCommand {

    @Require("balloonsystem.mascots")
    @Command(desc = "Test command")
    public void mascot(Main plugin, @Sender Player player) {
        new SelectMascotMenu(plugin, plugin.getMascotManager()).openMenu(player);
    }
}
