package me.arjona.balloons.commands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.menu.create.CustomizeBalloonMenu;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TestCommand {

    @Command(desc = "Test command")
    public void test(Main plugin, @Sender Player player) {
        //new SelectMascotMenu(plugin, plugin.getMascotManager()).openMenu(player);
        new CustomizeBalloonMenu().openMenu(player);
        /*plugin.getMascotManager().getMascots().put(player.getUniqueId(), new Mascot(plugin, player, plugin.getMascotManager().getDefaultBody()));
        player.sendMessage(CC.RED + "Balloon spawned!");*/
    }
}
