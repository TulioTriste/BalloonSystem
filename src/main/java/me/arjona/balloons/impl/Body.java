package me.arjona.balloons.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class Body {

    private final Heads head;
    private ItemStack chestPlate;

    public void apply(Main plugin, Balloon balloon) {
        balloon.getArmorStand().setHelmet(plugin.getBalloonSkull(head));

        if (chestPlate != null) balloon.getArmorStand().setChestplate(chestPlate);
    }
}
