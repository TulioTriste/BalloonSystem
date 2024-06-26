package me.arjona.balloons.mascot.menu;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.Main;
import me.arjona.balloons.generic_profile.Profile;
import me.arjona.balloons.mascot.MascotManager;
import me.arjona.balloons.mascot.impl.Body;
import me.arjona.customutilities.CC;
import me.arjona.customutilities.item.ItemBuilder;
import me.arjona.customutilities.menu.Button;
import me.arjona.customutilities.menu.pagination.PageButton;
import me.arjona.customutilities.menu.pagination.PaginatedMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class SelectMascotMenu extends PaginatedMenu {

    private final Main plugin;
    private final MascotManager mascotManager;

    @Override
    public String getTitle(Player player) {
        return ChatColor.GREEN + "Select a mascot";
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

        buttons.put(getSlot(4, 3), new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.BARRIER)
                        .name(ChatColor.RED + "Remove Mascot")
                        .build();
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
                if (profile.getMascotBody() == null) {
                    player.sendMessage(ChatColor.RED + "You do not have a mascot.");
                    return;
                }
                profile.removeMascot();
                player.sendMessage(ChatColor.GREEN + "You have removed your mascot.");
                player.closeInventory();
            }
        });

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (Body body : mascotManager.getSetBodies()) {
            buttons.put(buttons.size(), new MascotButton(body));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private class MascotButton extends Button {

        private final Body body;

        @Override
        public ItemStack getButtonItem(Player player) {
            // Para ahorrarles trabajo
            /*List<String> lore = plugin.getMessagesConfig().getStringList("mascots_lore.locked");

            if (mascotManager.isSpecificUsing(player.getUniqueId(), body.getName())) {
                lore = plugin.getMessagesConfig().getStringList("mascots_lore.used");
            }
            else if (body.isUnlocked(player)) {
                lore = plugin.getMessagesConfig().getStringList("mascots_lore.unused");
            }*/

            return new ItemBuilder(body.getHeadSkull())
                    .lore(body.getLore())
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());

            for (String s : plugin.getMascotsConfig().getStringList("select-mascot-message")) {
                player.sendMessage(CC.translate(s.replace("<name>", body.getName())));
            }

            if (profile.getMascotModel() != null)
                profile.removeMascot();

            profile.applyMascot(plugin, player, body);

            player.closeInventory();
        }
    }
}
