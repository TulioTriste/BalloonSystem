package me.arjona.balloons;

import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import com.jonahseguin.drink.provider.spigot.CommandSenderProvider;
import com.jonahseguin.drink.provider.spigot.PlayerProvider;
import lombok.Getter;
import me.arjona.balloons.commands.MascotsCommand;
import me.arjona.balloons.mascot.MascotManager;
import me.arjona.balloons.generic_profile.ProfileManager;
import me.arjona.customutilities.file.FileConfig;
import me.arjona.customutilities.menu.listener.MenuListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin implements CommandExecutor {

    /*private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;*/

    private FileConfig databaseConfig, mascotsConfig, messagesConfig;

    private MascotManager mascotManager;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        registerConfig();
        //initDatabase();

        registerManagers();
        registerListener();
        registerCommands();
    }

    private void registerConfig() {
        this.databaseConfig = new FileConfig(this, "database.yml");
        this.mascotsConfig = new FileConfig(this, "mascots.yml");
        this.messagesConfig = new FileConfig(this, "messages.yml");
    }

    private void registerManagers() {
        this.profileManager = new ProfileManager(this);
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

        drink.register(new MascotsCommand(), "testcmd");

        drink.registerCommands();
    }

    /*private void initDatabase() {
        try {
            MongoClientURI uri = new MongoClientURI(this.databaseConfig.getString("mongodb_uri"));

            if (uri.getDatabase() == null) {
                Logger.err("&cDatabase name is not set in the URI.");
                Bukkit.getServer().shutdown();
                return;
            }

            this.mongoClient = new MongoClient(uri);
            this.mongoDatabase = mongoClient.getDatabase(uri.getDatabase());

            Logger.log("MongoDB connection successfully.");
        }
        catch (MongoException | IllegalArgumentException | NullPointerException ignored) {
            Logger.log("MongoDB failed to connect.");
            Bukkit.getServer().shutdown();
        }
    }*/
}
