package me.arjona.balloons.mascot.menu;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.MascotManager;
import me.arjona.balloons.mascot.impl.Body;
import me.arjona.balloons.mascot.impl.Mascot;
import me.arjona.customutilities.CC;
import me.arjona.customutilities.item.ItemBuilder;
import me.arjona.customutilities.menu.Button;
import me.arjona.customutilities.menu.pagination.PageButton;
import me.arjona.customutilities.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SelectMascotMenu extends PaginatedMenu {

    private final Main plugin;
    private final MascotManager mascotManager;

    @Override
    public String getTitle(Player player) {
        return CC.GREEN + "Select a mascot";
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return getTitle(player);
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 27;
    }

    @Override
    public int getSize() {
        return 9*4;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(getSlot(0, 3), new PageButton(-1, this));
        buttons.put(getSlot(8, 3), new PageButton(1, this));

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (Body body : mascotManager.getBodies()) {
            buttons.put(buttons.size(), new MascotButton(body));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private class MascotButton extends Button {

        private final Body body;

        @Override
        public ItemStack getButtonItem(Player player) {
            List<String> lore = plugin.getMessagesConfig().getStringList("mascots_lore.locked");

            if (mascotManager.isSpecificUsing(player.getUniqueId(), body.getName())) {
                lore = plugin.getMessagesConfig().getStringList("mascots_lore.used");
            }
            else if (body.isUnlocked(player)) {
                lore = plugin.getMessagesConfig().getStringList("mascots_lore.unused");
            }

            return new ItemBuilder(body.getHeadSkull())
                    .lore(lore)
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (mascotManager.isSpecificUsing(player.getUniqueId(), body.getName())) {
                mascotManager.getMascots().get(player.getUniqueId()).die();
                for (String s : plugin.getMessagesConfig().getStringList("mascots_message.unequipped")) {
                    player.sendMessage(CC.translate(s
                            .replace("{mascot}", body.getName())
                            .replace("{mascot-displayname}", body.getDisplayName())));
                }
            }
            else if (!body.isUnlocked(player)) {
                for (String s : plugin.getMessagesConfig().getStringList("mascots_message.remove")) {
                    player.sendMessage(CC.translate(s
                            .replace("{mascot}", body.getName())
                            .replace("{mascot-displayname}", body.getDisplayName())));
                }
            } else {
                mascotManager.setMascot(player.getUniqueId(), new Mascot(plugin, player, body));
                for (String s : plugin.getMessagesConfig().getStringList("mascots_message.equip")) {
                    player.sendMessage(CC.translate(s
                            .replace("{mascot}", body.getName())
                            .replace("{mascot-displayname}", body.getDisplayName())));
                }
            }

            player.closeInventory();
        }
    }
}
