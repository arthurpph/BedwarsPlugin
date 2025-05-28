package com.arthurpph.bedwars.wizard.selector;

import com.arthurpph.bedwars.util.ItemStackUtils;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.saicone.rtag.RtagItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class WizardSelector<T extends WizardContext> {
    private final String id;
    protected ItemStack itemStack;

    protected WizardSelector(Material material, String displayName) {
        this.id = UUID.randomUUID().toString();
        this.itemStack = new ItemStack(material);
        ItemStackUtils.setDisplayName(itemStack, displayName);
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

    public abstract Consumer<T> getCallback();
}
