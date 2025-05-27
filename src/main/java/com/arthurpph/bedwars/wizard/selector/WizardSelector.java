package com.arthurpph.bedwars.wizard.selector;

import com.saicone.rtag.RtagItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class WizardSelector {
    private final String id;
    protected ItemStack itemStack;

    protected WizardSelector(Material material) {
        this.id = UUID.randomUUID().toString();
        this.itemStack = new ItemStack(material);
        registerRtag();
    }

    public String getId() {
        return id;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private void registerRtag() {
        RtagItem rtag = new RtagItem(itemStack);
        rtag.set(id, "wizardselector");
        rtag.load();
    }
}
