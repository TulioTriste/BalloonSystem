package me.arjona.balloons.mascot.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.arjona.customutilities.CC;
import me.arjona.customutilities.compatibility.material.CompatibleMaterial;
import me.arjona.customutilities.compatibility.particle.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Body {

    private final Heads head;
    private final String name, displayName;
    private final ItemStack[] armor;
    private final List<String> lore;

    @Setter private boolean particle = false;
    @Setter private Particle compatibleParticleEffect;

    public void apply(Mascot mascot) {
        mascot.getArmorStand().setHelmet(getHeadSkull());

        //if (armor != null) {
            for (ItemStack itemStack : armor) {
                if (itemStack == null) continue;
                if (itemStack.getType().name().contains("CHESTPLATE")) {
                    mascot.getArmorStand().setChestplate(itemStack);
                }
                else if (itemStack.getType().name().contains("LEGGINGS")) {
                    mascot.getArmorStand().setLeggings(itemStack);
                }
                else if (itemStack.getType().name().contains("BOOTS")) {
                    mascot.getArmorStand().setBoots(itemStack);
                }
            }
        //}
    }

    public ItemStack getHeadSkull() {
        ItemStack skull = new ItemStack(CompatibleMaterial.HUMAN_SKULL.getMaterial(), 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setDisplayName(CC.translate(displayName));

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

    public boolean isUnlocked(Player player) {
        return player.isOp() || player.hasPermission("*") || player.hasPermission(getPermission());
    }

    public String getPermission() {
        return "balloons.mascot." + name.toLowerCase().replace(" ", "_");
    }
}
