package me.arjona.balloons.mascot;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.impl.Body;
import me.arjona.balloons.mascot.impl.Heads;
import me.arjona.balloons.mascot.impl.Mascot;
import me.arjona.balloons.mascot.listener.MascotListener;
import me.arjona.balloons.mascot.task.MascotRunnable;
import me.arjona.customutilities.JavaUtil;
import me.arjona.customutilities.Logger;
import me.arjona.customutilities.compatibility.material.MaterialUtil;
import me.arjona.customutilities.compatibility.particle.Particle;
import me.arjona.customutilities.item.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class MascotManager {

    private final Main plugin;

    private final Map<UUID, Mascot> mascots;
    private final MascotRunnable mascotRunnable;

    private final List<Body> setBodies;

    public MascotManager(Main plugin) {
        this.plugin = plugin;
        mascots = Maps.newHashMap();
        setBodies = Lists.newArrayList();

        loadOrRefresh(plugin);

        plugin.getServer().getPluginManager().registerEvents(new MascotListener(plugin, this), plugin);

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, mascotRunnable = new MascotRunnable(this),
                1L, 1L);
    }

    private void loadOrRefresh(Main plugin) {
        FileConfiguration config = plugin.getMascotsConfig().getConfiguration();

        for (String key : config.getConfigurationSection("mascots").getKeys(false)) {
            String path = "mascots." + key + ".";
            String title = config.getString(path + "title");

            Particle particle = null;
            if (config.getString(path + "particle") != null && !config.getString(path + "particle").isEmpty()) {
                try {
                    particle = Particle.fromName(config.getString(path + "particle"));
                } catch (Exception e) {
                    Logger.err("&cFailed to load particle for " + key);
                }
            }

            Heads head;
            try {
                head = new Heads(config.getString(path + "head-texture"));
            } catch (Exception e) {
                Logger.err("&cFailed to load head for " + key);
                continue;
            }

            List<ItemStack> armors = Lists.newArrayList();

            ItemStack chestplate = getItemFromConfig(config, key, "chestplate");
            if (chestplate != null)
                armors.add(chestplate);

            ItemStack leggings = getItemFromConfig(config, key, "leggings");
            if (leggings != null)
                armors.add(leggings);

            ItemStack boots = getItemFromConfig(config, key, "boots");
            if (boots != null)
                armors.add(boots);

            List<String> lore = config.getStringList(path + "lore");

            Body body = new Body(head, key, title, armors.toArray(new ItemStack[0]), lore);

            if (particle != null) {
                body.setParticle(true);
                body.setCompatibleParticleEffect(particle);
            }

            this.setBodies.add(body);
        }
    }

    public void onDisable() {
        mascots.values().forEach(Mascot::onDisable);
    }

    public Body getDefaultBody() {
        return new Body(
                new Heads(
                        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM1ZWY5MjMwMDMxNzY5YTJlODE5MmFiNDZhMTcxNDQxMGQ1NmMzYjliMzhhMDMwMmEwNThlNDc5NzNkN2M0ZCJ9fX0="),
                "default",
                "&cDefault Head",
                new ItemStack[] {new ItemStack(Material.DIAMOND_CHESTPLATE)},
                Arrays.asList("default", "default2"));
    }

    public boolean exists(String name) {
        return getSetBodies().stream().anyMatch(body -> body.getName().equalsIgnoreCase(name));
    }

    public Body getBody(String name) {
        return getSetBodies().stream().filter(body -> body.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Nullable
    private ItemStack getItemFromConfig(FileConfiguration config, String key, String type) {
        String path = "mascots." + key + ".";
        ItemStack leggings = null;
        if (config.contains(path + type + "-item")) {
            try {
                ItemBuilder itemBuilder = new ItemBuilder(MaterialUtil.getMaterial(config.getString(path + type + "-item.material")));
                if (itemBuilder.build().getType().name().contains("LEATHER")) {
                    int[] rgb = JavaUtil.getRGB(config.getString(path  + type+ "-item.color"));
                    itemBuilder.armorColor(Color.fromBGR(rgb[0], rgb[1], rgb[2]));
                }
                itemBuilder.enchant(config.getBoolean(path + type + "-item.enchanted"));

                leggings = itemBuilder.build();
            } catch (Exception e) {
                Logger.err("Failed to load " + type + " for " + key);
                return null;
            }
        }

        return leggings;
    }
}
