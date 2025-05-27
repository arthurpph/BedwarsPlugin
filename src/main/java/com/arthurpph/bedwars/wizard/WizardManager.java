package com.arthurpph.bedwars.wizard;

import com.arthurpph.bedwars.wizard.selector.WizardSelector;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WizardManager<T> {
    private final Map<String, Consumer<T>> callbacks;

    public WizardManager() {
        callbacks = new HashMap<>();
    }

    public void accept(String id, T context) {
        Consumer<T> consumer = callbacks.get(id);
        if(consumer == null) return;
        consumer.accept(context);
    }

    public void register(WizardSelector wizardSelector, Consumer<T> callback) {
        callbacks.put(wizardSelector.getId(), callback);
    }
}
