package com.arthurpph.bedwars.wizard.selector.impl;

import com.arthurpph.bedwars.view.WizardIslandSelectorView;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.Material;

import java.util.function.Consumer;

public class SelectIslandWizardSelector extends WizardSelector<WizardContext> {
    private final ViewFrame viewFrame;

    public SelectIslandWizardSelector(Material material, String displayName, ViewFrame viewFrame) {
        super(material, displayName);
        this.viewFrame = viewFrame;
    }

    @Override
    public Consumer<WizardContext> getCallback() {
        return context -> {
            viewFrame.open(WizardIslandSelectorView.class, context.getPlayer());
        };
    }
}
