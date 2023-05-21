package me.arjona.balloons.mascot.menu.create;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.mascot.impl.Body;
import me.arjona.customutilities.CC;
import me.arjona.customutilities.compatibility.material.CompatibleMaterial;
import me.arjona.customutilities.item.ItemBuilder;
import me.arjona.customutilities.menu.Button;
import me.arjona.customutilities.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Map;

public class CustomizeBalloonMenu extends Menu {

    @Override
    public int getSize() {
        return 9*5;
    }

    @Override
    public String getTitle(Player player) {
        return CC.GREEN + "Create a balloon";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(getSlot(4, 1), new HeadButton());

        return buttons;
    }

    @RequiredArgsConstructor
    private class HeadButton extends Button {

        private final Body body;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(CompatibleMaterial.HUMAN_SKULL.getMaterial())
                    .name(CC.GREEN + CC.BOLD + "Head")
                    .lore("",
                            "&6Left-Click to create a Custom Head",
                            "&6Right-Click with a head in your hand to select it")
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            ItemStack book = new ItemStack(Material.BOOK_AND_QUILL);
            BookMeta meta = (BookMeta) book.getItemMeta();

            meta.setDisplayName(CC.GREEN + CC.BOLD + "Create a balloon");
            meta.setAuthor("Arjona");
            meta.setPages("Remove this and insert your texture.");
        }
    }
}
