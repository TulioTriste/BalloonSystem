package me.arjona.balloons.mascot.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.arjona.customutilities.CC;
import me.arjona.customutilities.compatibility.material.CompatibleMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Body {

    private final Heads head;
    private ItemStack chestPlate;

    public void apply(Mascot mascot) {
        mascot.getArmorStand().setHelmet(getHeadSkull());

        if (chestPlate != null) mascot.getArmorStand().setChestplate(chestPlate);
    }

    public ItemStack getHeadSkull() {
        ItemStack skull = new ItemStack(CompatibleMaterial.HUMAN_SKULL.getMaterial(), 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setDisplayName(CC.translate(head.getDisplayName()));

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", head.getTexture()));

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
