package com.arthurpph.bedwars.wizard.selector.impl;

import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.arthurpph.bedwars.generator.GeneratorType;
import com.saicone.rtag.RtagItem;
import org.bukkit.Material;

public class GeneratorWizardSelector extends WizardSelector {
    private final GeneratorType generatorType;

    public GeneratorWizardSelector(Material material, GeneratorType generatorType) {
        super(material);
        this.generatorType = generatorType;
        registerGeneratorRtag();
    }

    private void registerGeneratorRtag() {
        RtagItem rtag = new RtagItem(itemStack);
        rtag.set(generatorType.toString(), "generator", "type");
        rtag.load();
    }
}
