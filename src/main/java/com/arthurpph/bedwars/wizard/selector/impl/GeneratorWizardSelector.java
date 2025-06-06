package com.arthurpph.bedwars.wizard.selector.impl;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.game.generator.Generator;
import com.arthurpph.bedwars.wizard.context.impl.GeneratorWizardContext;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.arthurpph.bedwars.game.generator.GeneratorType;
import com.saicone.rtag.RtagItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class GeneratorWizardSelector extends WizardSelector<GeneratorWizardContext> {
    private final Bedwars plugin;
    private final GeneratorType generatorType;
    private final ConfigurationManager configManager;

    public GeneratorWizardSelector(Bedwars plugin, Material material, String displayName, GeneratorType generatorType, ConfigurationManager configManager) {
        super(material, displayName);
        this.plugin = plugin;
        this.generatorType = generatorType;
        this.configManager = configManager;
        registerGeneratorRtag();
    }

    @Override
    public Consumer<GeneratorWizardContext> getCallback() {
        return context -> {
            final Player player = context.getPlayer();
            final Location location = player.getLocation();
            final GeneratorType generatorType = context.getGeneratorType();
            final Generator generator = new Generator(plugin, generatorType, location);
            configManager.setGenerator(generator);
            player.sendMessage(ChatColor.GREEN + "Generator created!");
        };
    }

    private void registerGeneratorRtag() {
        RtagItem rtag = new RtagItem(itemStack);
        rtag.set(generatorType.toString(), "generator", "type");
        rtag.load();
    }
}
