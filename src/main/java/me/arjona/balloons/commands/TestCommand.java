package me.arjona.balloons.commands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.impl.Balloon;
import me.arjona.balloons.impl.Body;
import me.arjona.customutilities.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class TestCommand {

    @Command(name = "", desc = "Test command")
    public void test(Main plugin, @Sender Player player) {
        plugin.getBalloons().put(player.getUniqueId(), new Balloon(plugin, player, new Body(Heads.RED_BALLOON, new ItemStack(Material.DIAMOND_CHESTPLATE))));
        player.sendMessage(CC.RED + "Balloon spawned!");
    }
}
