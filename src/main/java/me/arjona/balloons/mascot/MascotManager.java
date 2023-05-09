package me.arjona.balloons.mascot;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.impl.Body;
import me.arjona.balloons.mascot.impl.Heads;
import me.arjona.balloons.mascot.impl.Mascot;
import me.arjona.balloons.mascot.listener.MascotListener;
import me.arjona.balloons.mascot.task.MascotRunnable;
import me.arjona.customutilities.Logger;
import me.arjona.customutilities.serializer.ItemStackSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
public class MascotManager {

    private final Map<UUID, Mascot> mascots;
    private final MascotRunnable mascotRunnable;

    private final Set<Body> bodies = Sets.newConcurrentHashSet();

    public MascotManager(Main plugin) {
        mascots = Maps.newHashMap();

        init(plugin);

        plugin.getServer().getPluginManager().registerEvents(new MascotListener(plugin, this), plugin);

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, mascotRunnable = new MascotRunnable(this),
                1L, 1L);
    }

    private void init(Main plugin) {
        FileConfiguration config = plugin.getBalloonConfig().getConfiguration();

        for (String key : config.getKeys(false)) {
            for (String s : config.getConfigurationSection(key).getKeys(false)) {
                String path = key + "." + s + ".";
                try {
                    Heads head = new Heads(config.getString(path + "texture"));

                    ItemStack chestPlate = null;
                    if (config.contains(path + "chestplate")) {
                        try {
                            chestPlate = ItemStackSerializer.itemFrom64(config.getString(path + "chestplate"));
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to deserialize chestplate for " + key + "." + s);
                        }
                    }

                    bodies.add(new Body(head, s.replace("_", " "), config.getString(path + "displayName"), chestPlate));
                    Logger.deb("Loaded mascot " + key + "." + s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setMascot(UUID uuid, Mascot mascot) {
        mascots.put(uuid, mascot);
    }

    public boolean isUsing(UUID uuid) {
        return mascots.containsKey(uuid);
    }

    public boolean isSpecificUsing(UUID uuid, String name) {
        return isUsing(uuid) && mascots.get(uuid).getBody().getName().equalsIgnoreCase(name);
    }

    public Body getDefaultBody() {
        return new Body(
                new Heads(
                        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM1ZWY5MjMwMDMxNzY5YTJlODE5MmFiNDZhMTcxNDQxMGQ1NmMzYjliMzhhMDMwMmEwNThlNDc5NzNkN2M0ZCJ9fX0="),
                "default",
                "&cDefault Head",
                new ItemStack(Material.DIAMOND_CHESTPLATE));
    }
}
