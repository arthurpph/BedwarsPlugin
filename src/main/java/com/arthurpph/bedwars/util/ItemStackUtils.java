package com.arthurpph.bedwars.util;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.List;

public final class ItemStackUtils {
    public static void setDisplayName(ItemStack item, String displayName) {
        final ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
    }

    public static final class Builder {
        private ItemStack stack;

        public Builder(Material mat) {
            this.stack = new ItemStack(mat);
        }

        public Builder(ItemStack stack) {
            this.stack = stack.clone();
        }

        public Builder setColor(Color color) {
            ItemMeta meta = stack.getItemMeta();
            if (meta instanceof LeatherArmorMeta) {
                ((LeatherArmorMeta) meta).setColor(color);
                stack.setItemMeta(meta);
            }
            return this;
        }

        public Builder setGlow(boolean glow) {
            ItemMeta meta = stack.getItemMeta();
            if (meta == null) return this;

            if (glow) {
                meta.addEnchant(Enchantment.LUCK_OF_THE_SEA, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                for (Enchantment ench : meta.getEnchants().keySet()) {
                    meta.removeEnchant(ench);
                }
            }
            return setItemMeta(meta);
        }

        public Builder setDisplayName(String name) {
            ItemMeta meta = stack.getItemMeta();
            if (meta == null) return this;
            meta.setDisplayName(name);
            return setItemMeta(meta);
        }

        public Builder setLore(List<String> lore) {
            ItemMeta meta = stack.getItemMeta();
            if (meta == null) return this;
            meta.setLore(lore);
            return setItemMeta(meta);
        }

        public Builder addEnchant(Enchantment enchant, int level) {
            ItemMeta meta = stack.getItemMeta();
            if (meta == null) return this;
            meta.addEnchant(enchant, level, true);
            return setItemMeta(meta);
        }

        public Builder addItemFlag(ItemFlag flag) {
            ItemMeta meta = stack.getItemMeta();
            if (meta == null) return this;
            meta.addItemFlags(flag);
            return setItemMeta(meta);
        }

        public Builder setUnbreakable(boolean unbreakable) {
            ItemMeta meta = stack.getItemMeta();
            if (meta == null) return this;
            meta.setUnbreakable(unbreakable);
            return setItemMeta(meta);
        }

        public Builder setItemMeta(ItemMeta meta) {
            if (meta != null) {
                stack.setItemMeta(meta);
            }
            return this;
        }

        public Builder setAmount(int amount) {
            stack.setAmount(amount);
            return this;
        }

        public ItemStack build() {
            return stack;
        }
    }
}
