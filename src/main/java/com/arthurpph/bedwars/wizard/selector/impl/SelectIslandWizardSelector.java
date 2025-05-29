package com.arthurpph.bedwars.wizard.selector.impl;

import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.view.WizardIslandSelectorView;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.google.common.collect.ImmutableMap;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.Material;

import java.util.function.Consumer;

public class SelectIslandWizardSelector extends WizardSelector<WizardContext> {
    private final WizardManager wizardManager;
    private final ConfigurationManager configManager;
    private final ViewFrame viewFrame;

    public SelectIslandWizardSelector(WizardManager wizardManager, ConfigurationManager configManager, Material material, String displayName, ViewFrame viewFrame) {
        super(material, displayName);
        this.wizardManager = wizardManager;
        this.configManager = configManager;
        this.viewFrame = viewFrame;
    }

    @Override
    public Consumer<WizardContext> getCallback() {
        return context -> {
            viewFrame.open(WizardIslandSelectorView.class, context.getPlayer(), ImmutableMap.of("wizardManager", wizardManager, "configManager", configManager));
        };
    }
}
