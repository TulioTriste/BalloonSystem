package me.arjona.balloons;

import com.google.common.collect.Maps;
import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import com.jonahseguin.drink.provider.spigot.CommandSenderProvider;
import com.jonahseguin.drink.provider.spigot.PlayerProvider;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import me.arjona.balloons.commands.TestCommand;
import me.arjona.balloons.impl.Balloon;
import me.arjona.balloons.impl.Heads;
import me.arjona.balloons.task.BalloonRunnable;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class Main extends JavaPlugin implements CommandExecutor {

    private Map<UUID, Balloon> balloons;
    private BalloonRunnable balloonRunnable;

    @Override
    public void onEnable() {
        balloons = Maps.newHashMap();

        registerCommands();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, balloonRunnable = new BalloonRunnable(this),
                1L, 1L);
    }

    private void registerCommands() {
        CommandService drink = Drink.get(this);

        drink.bind(Main.class).toInstance(this);
        drink.bind(Player.class).toProvider(new PlayerProvider(this));
        drink.bind(CommandSender.class).toProvider(new CommandSenderProvider());

        drink.register(new TestCommand(), "testcmd");

        drink.registerCommands();
    }

    public ItemStack getBalloonSkull(Heads heads) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", heads.getTexture()));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }

}
