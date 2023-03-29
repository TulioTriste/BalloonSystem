package me.arjona.balloons;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

@Getter
public class Main extends JavaPlugin implements CommandExecutor {

    private Map<UUID, Balloon> balloons;
    private BalloonRunnable balloonRunnable;

    @Override
    public void onEnable() {
        balloons = Maps.newHashMap();

        /*balloonRunnable = new BalloonRunnable(this);
        balloonRunnable.runTaskTimerAsynchronously(this, 2L, 2L);*/

        getCommand("balloon").setExecutor(this);

        //getServer().getScheduler().runTaskTimerAsynchronously(this, new EntitiesRunnable(this), 20L, 20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        balloons.put(((Player) sender).getUniqueId(), new Balloon(this, (Player) sender));
        sender.sendMessage("§aBalloon created!");
        return true;
    }

    public ItemStack getBalloonSkull(Heads heads) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
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
