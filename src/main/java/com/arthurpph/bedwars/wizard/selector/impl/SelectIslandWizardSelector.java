package com.arthurpph.bedwars.wizard.selector.impl;

import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.wizard.view.WizardIslandSelectorView;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.loader.WizardLoader;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.google.common.collect.ImmutableMap;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.Material;

import java.util.function.Consumer;

public class SelectIslandWizardSelector extends WizardSelector<WizardContext> {
    private final WizardManager wizardManager;
    private final ConfigurationManager configManager;
    private final WizardLoader wizardLoader;
    private final ViewFrame viewFrame;

    public SelectIslandWizardSelector(WizardManager wizardManager, ConfigurationManager configManager, WizardLoader wizardLoader, Material material, String displayName, ViewFrame viewFrame) {
        super(material, displayName);
        this.wizardManager = wizardManager;
        this.configManager = configManager;
        this.wizardLoader = wizardLoader;
        this.viewFrame = viewFrame;
    }

    @Override
    public Consumer<WizardContext> getCallback() {
        return context -> {
            viewFrame.open(WizardIslandSelectorView.class, context.getPlayer(), ImmutableMap.of("wizardManager", wizardManager, "configManager", configManager, "onTeamClick", (Runnable) this::onTeamClick));
        };
    }

    private void onTeamClick() {
        wizardLoader.disable();
    }
}
