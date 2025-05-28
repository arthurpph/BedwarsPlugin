package com.arthurpph.bedwars.wizard.loader.impl;

import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.loader.WizardLoader;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.arthurpph.bedwars.generator.GeneratorType;
import com.arthurpph.bedwars.wizard.selector.impl.GeneratorWizardSelector;
import com.arthurpph.bedwars.wizard.selector.impl.SelectIslandWizardSelector;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class DefaultWizardLoader implements WizardLoader {
    private final WizardManager wizardManager;
    private final ConfigurationManager configManager;
    private final ViewFrame viewFrame;
    private final Player player;

    public DefaultWizardLoader(WizardManager wizardManager, ConfigurationManager configManager, ViewFrame viewFrame, Player player) {
        this.wizardManager = wizardManager;
        this.configManager = configManager;
        this.viewFrame = viewFrame;
        this.player = player;
    }

    @Override
    public void load() {
        final Inventory inventory = player.getInventory();
        inventory.clear();
        final List<WizardSelector<? extends WizardContext>> wizardSelectors = List.of(
                createGeneratorWizardSelector(Material.DIAMOND_BLOCK, ChatColor.AQUA + "Set Diamond Generator", GeneratorType.DIAMOND),
                createGeneratorWizardSelector(Material.EMERALD_BLOCK, ChatColor.GREEN + "Set Emerald Generator", GeneratorType.EMERALD),
                createSelectIslandWizardSelector(Material.STICK, ChatColor.GREEN + "Select Island")
        );

        for (WizardSelector<? extends WizardContext> wizardSelector : wizardSelectors) {
            inventory.addItem(wizardSelector.getItemStack());
        }
    }

    private GeneratorWizardSelector createGeneratorWizardSelector(Material material, String displayName, GeneratorType generatorType) {
        GeneratorWizardSelector selector = new GeneratorWizardSelector(material, displayName, generatorType, configManager);
        wizardManager.register(selector, selector.getCallback());
        return selector;
    }

    private SelectIslandWizardSelector createSelectIslandWizardSelector(Material material, String displayName) {
        SelectIslandWizardSelector selector = new SelectIslandWizardSelector(material, displayName, viewFrame);
        wizardManager.register(selector, selector.getCallback());
        return selector;
    }
}
