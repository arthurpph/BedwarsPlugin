package com.arthurpph.bedwars.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemStackUtils {
    public static void setDisplayName(ItemStack item, String displayName) {
        final ItemMeta meta = item.getItemMeta();
        if(meta == null) return;
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
    }
}
