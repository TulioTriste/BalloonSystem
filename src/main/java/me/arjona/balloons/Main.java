package me.arjona.balloons;

import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import com.jonahseguin.drink.provider.spigot.CommandSenderProvider;
import com.jonahseguin.drink.provider.spigot.PlayerProvider;
import lombok.Getter;
import me.arjona.balloons.commands.TestCommand;
import me.arjona.balloons.mascot.MascotManager;
import me.arjona.customutilities.file.FileConfig;
import me.arjona.customutilities.menu.listener.MenuListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin implements CommandExecutor {

    private FileConfig balloonConfig;

    private MascotManager mascotManager;

    @Override
    public void onEnable() {
        registerConfig();

        registerManagers();
        registerListener();
        registerCommands();
    }

    private void registerConfig() {
        this.balloonConfig = new FileConfig(this, "balloons.yml");
    }

    private void registerManagers() {
        this.mascotManager = new MascotManager(this);
    }

    private void registerListener() {
        new MenuListener(this);
    }

    private void registerCommands() {
        CommandService drink = Drink.get(this);

        drink.bind(Main.class).toInstance(this);
        drink.bind(Player.class).toProvider(new PlayerProvider(this));
        drink.bind(CommandSender.class).toProvider(new CommandSenderProvider());

        drink.register(new TestCommand(), "testcmd");

        drink.registerCommands();
    }

}
