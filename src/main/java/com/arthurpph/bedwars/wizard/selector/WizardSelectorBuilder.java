package com.arthurpph.bedwars.wizard.selector;

import com.arthurpph.bedwars.util.ItemStackUtils;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.selector.impl.GeneratorWizardSelector;
import com.arthurpph.bedwars.generator.GeneratorType;
import org.bukkit.Material;

import java.util.function.Consumer;

public class WizardSelectorBuilder<T> {
    private final WizardManager<T> wizardManager;

    private Material material;
    private String displayName;
    private Consumer<T> callback;
    private GeneratorType generatorType;

    public static <T> WizardSelectorBuilder<T> builder(WizardManager<T> wizardManager) {
        return new WizardSelectorBuilder<>(wizardManager);
    }

    public WizardSelectorBuilder(WizardManager<T> wizardManager) {
        this.wizardManager = wizardManager;
    }

    public WizardSelectorBuilder<T> material(Material material) {
        this.material = material;
        return this;
    }

    public WizardSelectorBuilder<T> displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public WizardSelectorBuilder<T> callback(Consumer<T> callback) {
        this.callback = callback;
        return this;
    }

    public WizardSelectorBuilder<T> generatorType(GeneratorType generatorType) {
        this.generatorType = generatorType;
        return this;
    }

    public WizardSelector build() {
        if(material == null) {
            throw new IllegalStateException("Material must be set before building a WizardSelector.");
        }

        if(callback == null) {
            throw new IllegalStateException("Callback must be set before building a WizardSelector.");
        }

        final WizardSelector wizardSelector = createSelector();
        if(displayName != null)
            ItemStackUtils.setDisplayName(wizardSelector.getItemStack(), displayName);
        wizardManager.register(wizardSelector, callback);
        return wizardSelector;
    }

    private WizardSelector createSelector() {
        if(generatorType != null) {
            return new GeneratorWizardSelector(material, generatorType);
        }

        throw new IllegalStateException("No WizardSelector found with the provided state.");
    }
}
