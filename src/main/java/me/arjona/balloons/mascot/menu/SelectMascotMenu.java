package me.arjona.balloons.mascot.menu;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import me.arjona.balloons.mascot.MascotManager;
import me.arjona.balloons.mascot.impl.Body;
import me.arjona.customutilities.CC;
import me.arjona.customutilities.Logger;
import me.arjona.customutilities.menu.Button;
import me.arjona.customutilities.menu.pagination.PageButton;
import me.arjona.customutilities.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
public class SelectMascotMenu extends PaginatedMenu {

    private final MascotManager mascotManager;

    @Override
    public boolean isFillBorders() {
        return true;
    }

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
        return 21;
    }

    @Override
    public int getSize() {
        return 9*5;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(getSlot(0, 4), new PageButton(-1, this));
        buttons.put(getSlot(8, 4), new PageButton(1, this));

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        Iterator<Body> iterator = mascotManager.getBodies().iterator();
        for (int index = 0; index < getSize(); index++) {
            if (!iterator.hasNext())
                break;

            if (index < 9 || index >= getSize() - 9 || index % 9 == 0 || index % 9 == 8)
                continue;

            if (buttons.containsKey(index))
                continue;

            buttons.put(index, Button.fromItem(iterator.next().getHeadSkull()));
        }

        return buttons;
    }
}
