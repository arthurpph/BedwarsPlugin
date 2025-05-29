package com.arthurpph.bedwars.wizard.loader;

import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class WizardLoader {
    protected final WizardManager wizardManager;
    protected final Player player;

    protected WizardLoader(WizardManager wizardManager, Player player) {
        this.wizardManager = wizardManager;
        this.player = player;
    }

    protected <C extends WizardContext, T extends WizardSelector<C>> T registerSelector(T selector) {
        wizardManager.register(selector, selector.getCallback());
        return selector;
    }

    public void disable() {
        final Inventory inventory = player.getInventory();
        for (ItemStack item : inventory.getContents()) {
            wizardManager.unregister(item);
        }
        inventory.clear();
    }

    public abstract void load();
}
