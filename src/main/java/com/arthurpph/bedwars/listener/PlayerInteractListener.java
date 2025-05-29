package com.arthurpph.bedwars.listener;

import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.game.generator.GeneratorType;
import com.arthurpph.bedwars.wizard.context.impl.GeneratorWizardContext;
import com.saicone.rtag.RtagItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    private final WizardManager wizardManager;

    public PlayerInteractListener(WizardManager wizardManager) {
        this.wizardManager = wizardManager;
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();
        if(item == null) return;
        final RtagItem rtag = new RtagItem(item);
        final String id = rtag.get("wizardselector");
        if(id == null) return;
        final String generatorType = rtag.get("generator", "type");
        if(generatorType != null) {
            handleGeneratorWizardSelector(id, player, generatorType);
        }
        handleSelectIslandWizardSelector(id, player);
    }

    private void handleGeneratorWizardSelector(String id, Player player, String generatorType) {
        final WizardContext context = new GeneratorWizardContext(player, GeneratorType.valueOf(generatorType));
        wizardManager.accept(id, context);
    }

    private void handleSelectIslandWizardSelector(String id, Player player) {
        final WizardContext context = new WizardContext(player);
        wizardManager.accept(id, context);
    }
}
