package com.arthurpph.bedwars.wizard;

import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.saicone.rtag.RtagItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WizardManager {
    private final Map<String, Consumer<? super WizardContext>> callbacks;

    public WizardManager() {
        callbacks = new HashMap<>();
    }

    public <T extends WizardContext> void accept(String id, T context) {
        Consumer<? super WizardContext> consumer = callbacks.get(id);
        if(consumer == null) return;

        try {
            consumer.accept(context);
        } catch (ClassCastException ignored) {}

        System.out.println(callbacks.size());
    }

    @SuppressWarnings("unchecked")
    public <T extends WizardContext> void register(WizardSelector<T> wizardSelector, Consumer<? super T> callback) {
        callbacks.put(wizardSelector.getId(), (Consumer<? super WizardContext>) callback);
    }

    public void unregister(ItemStack itemStack) {
        if(itemStack == null) return;
        final RtagItem rtag = new RtagItem(itemStack);
        final String id = rtag.get("wizardselector");
        if(id == null) return;
        callbacks.remove(id);
    }
}
