package me.arjona.balloons.commands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.impl.Mascot;
import me.arjona.balloons.mascot.impl.Body;
import me.arjona.customutilities.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class TestCommand {

    @Command(name = "", desc = "Test command")
    public void test(Main plugin, @Sender Player player) {
        plugin.getMascotManager().getMascots().put(player.getUniqueId(), new Mascot(plugin, player, plugin.getMascotManager().getDefaultBody()));
        player.sendMessage(CC.RED + "Balloon spawned!");
    }
}
