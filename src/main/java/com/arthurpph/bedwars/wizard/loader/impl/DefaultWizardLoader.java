package com.arthurpph.bedwars.wizard.loader.impl;

import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.wizard.WizardContext;
import com.arthurpph.bedwars.wizard.loader.WizardLoader;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.arthurpph.bedwars.wizard.selector.WizardSelectorBuilder;
import com.arthurpph.bedwars.generator.Generator;
import com.arthurpph.bedwars.generator.GeneratorType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.Consumer;

public class DefaultWizardLoader implements WizardLoader {
    private final WizardManager<WizardContext> wizardManager;
    private final ConfigurationManager configManager;
    private final Player player;

    public DefaultWizardLoader(WizardManager<WizardContext> wizardManager, ConfigurationManager configManager, Player player) {
        this.wizardManager = wizardManager;
        this.configManager = configManager;
        this.player = player;
    }

    @Override
    public void load() {
        final Inventory inventory = player.getInventory();
        inventory.clear();
        final List<WizardSelector> wizardSelectors = List.of(
                createGeneratorWizardSelector(Material.DIAMOND_BLOCK, ChatColor.AQUA + "Set Diamond Generator", GeneratorType.DIAMOND, this::createGenerator),
                createGeneratorWizardSelector(Material.EMERALD_BLOCK, ChatColor.GREEN + "Set Emerald Generator", GeneratorType.EMERALD, this::createGenerator)
        );

        for (WizardSelector wizardSelector : wizardSelectors) {
            inventory.addItem(wizardSelector.getItemStack());
        }
    }

    private WizardSelector createGeneratorWizardSelector(Material material, String displayName, GeneratorType generatorType, Consumer<WizardContext> callback) {
        return WizardSelectorBuilder
            .builder(wizardManager)
            .material(material)
            .displayName(displayName)
            .callback(callback)
            .generatorType(generatorType)
            .build();
    }

    private void createGenerator(WizardContext context) {
        final Location location = context.player().getLocation();
        final GeneratorType generatorType = context.generatorType();
        final Generator generator = new Generator(generatorType, location);
        configManager.setGenerator(generator);
        player.sendMessage(ChatColor.GREEN + "Generator created!");
    }
}
