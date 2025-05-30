package com.arthurpph.bedwars.wizard.loader.impl;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.loader.WizardLoader;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.arthurpph.bedwars.game.generator.GeneratorType;
import com.arthurpph.bedwars.wizard.selector.impl.ExitWizardSelector;
import com.arthurpph.bedwars.wizard.selector.impl.GeneratorWizardSelector;
import com.arthurpph.bedwars.wizard.selector.impl.SelectIslandWizardSelector;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class DefaultWizardLoader extends WizardLoader {
    private final Bedwars plugin;
    private final ConfigurationManager configManager;
    private final ViewFrame viewFrame;

    public DefaultWizardLoader(Bedwars plugin, WizardManager wizardManager, ConfigurationManager configManager, ViewFrame viewFrame, Player player) {
        super(wizardManager, player);
        this.plugin = plugin;
        this.configManager = configManager;
        this.viewFrame = viewFrame;
    }

    @Override
    public void load() {
        final Inventory inventory = player.getInventory();
        inventory.clear();

        final List<WizardSelector<? extends WizardContext>> wizardSelectors = List.of(
                createGeneratorWizardSelector(Material.DIAMOND_BLOCK, ChatColor.AQUA + "Set Diamond Generator", GeneratorType.DIAMOND),
                createGeneratorWizardSelector(Material.EMERALD_BLOCK, ChatColor.GREEN + "Set Emerald Generator", GeneratorType.EMERALD),
                createSelectIslandWizardSelector(Material.STICK, ChatColor.GREEN + "Select Island"),
                createExitWizardSelector()
        );

        for (WizardSelector<? extends WizardContext> wizardSelector : wizardSelectors) {
            inventory.addItem(wizardSelector.getItemStack());
        }
    }

    private GeneratorWizardSelector createGeneratorWizardSelector(Material material, String displayName, GeneratorType generatorType) {
        GeneratorWizardSelector selector = new GeneratorWizardSelector(plugin, material, displayName, generatorType, configManager);
        return registerSelector(selector);
    }

    private SelectIslandWizardSelector createSelectIslandWizardSelector(Material material, String displayName) {
        SelectIslandWizardSelector selector = new SelectIslandWizardSelector(wizardManager, configManager, this, material, displayName, viewFrame);
        return registerSelector(selector);
    }

    private ExitWizardSelector createExitWizardSelector() {
        ExitWizardSelector selector = new ExitWizardSelector(Material.BARRIER, ChatColor.GREEN + "Exit Wizard", this, () -> player.sendMessage(ChatColor.GREEN + "Exit wizard..."));
        wizardManager.register(selector, selector.getCallback());
        return registerSelector(selector);
    }
}
