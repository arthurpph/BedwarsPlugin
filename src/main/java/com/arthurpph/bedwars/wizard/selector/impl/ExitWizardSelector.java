package com.arthurpph.bedwars.wizard.selector.impl;

import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.loader.WizardLoader;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import org.bukkit.Material;

import java.util.function.Consumer;

public class ExitWizardSelector extends WizardSelector<WizardContext> {
    private final WizardLoader wizardLoader;
    private Runnable onExit;

    public ExitWizardSelector(Material material, String displayName, WizardLoader wizardLoader) {
        super(material, displayName);
        this.wizardLoader = wizardLoader;
    }

    public ExitWizardSelector(Material material, String displayName, WizardLoader wizardLoader, Runnable onExit) {
        super(material, displayName);
        this.wizardLoader = wizardLoader;
        this.onExit = onExit;
    }

    @Override
    public Consumer<WizardContext> getCallback() {
        return context -> {
            if (context.getPlayer() == null) return;
            wizardLoader.disable();
            if(onExit != null) onExit.run();
        };
    }
}
