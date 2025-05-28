package com.arthurpph.bedwars.wizard.context.impl;

import com.arthurpph.bedwars.generator.GeneratorType;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import org.bukkit.entity.Player;

public class GeneratorWizardContext extends WizardContext {
    private final GeneratorType generatorType;

    public GeneratorWizardContext(Player player, GeneratorType generatorType) {
        super(player);
        this.generatorType = generatorType;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }
}
