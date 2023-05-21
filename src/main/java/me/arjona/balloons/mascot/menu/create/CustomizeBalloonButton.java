package me.arjona.balloons.mascot.menu.create.buttons;

import lombok.RequiredArgsConstructor;
import me.arjona.customutilities.CC;
import me.arjona.customutilities.compatibility.material.CompatibleMaterial;
import me.arjona.customutilities.item.ItemBuilder;
import me.arjona.customutilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class CustomizeBalloonButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(CompatibleMaterial.HUMAN_SKULL.getMaterial())
                .name(CC.GREEN + "Click to create Balloon")
                .lore("&eWith this option you can",
                        "&ecreate a new ballon with",
                        "&eyour own custom head",
                        "",
                        "&6Click to open the menu")
                .build();
    }


}
