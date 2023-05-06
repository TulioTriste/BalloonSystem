package me.arjona.balloons.mascot;

import com.google.common.collect.Maps;
import lombok.Getter;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.impl.Body;
import me.arjona.balloons.mascot.impl.Heads;
import me.arjona.balloons.mascot.impl.Mascot;
import me.arjona.balloons.mascot.task.MascotRunnable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

@Getter
public class MascotManager {

    private final Map<UUID, Mascot> mascots;
    private final MascotRunnable mascotRunnable;

    public MascotManager(Main plugin) {
        mascots = Maps.newHashMap();

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, mascotRunnable = new MascotRunnable(this),
                1L, 1L);
    }

    private void init() {

    }

    public Body getDefaultBody() {
        return new Body(
                new Heads("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM1ZWY5MjMwMDMxNzY5YTJlODE5MmFiNDZhMTcxNDQxMGQ1NmMzYjliMzhhMDMwMmEwNThlNDc5NzNkN2M0ZCJ9fX0="),
                new ItemStack(Material.DIAMOND_CHESTPLATE));
    }
}
