package me.arjona.balloons.commands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.impl.Balloon;
import me.arjona.balloons.utilities.CC;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TestCommand {

    @Command(name = "", desc = "Test command")
    public void test(Main plugin, @Sender Player player) {
        plugin.getBalloons().put(player.getUniqueId(), new Balloon(plugin, player));
        player.sendMessage(CC.RED + "Balloon spawned!");
    }
}
