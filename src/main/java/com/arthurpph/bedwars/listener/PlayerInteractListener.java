package com.arthurpph.bedwars.listener;

import com.arthurpph.bedwars.wizard.WizardContext;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.generator.GeneratorType;
import com.saicone.rtag.RtagItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    private final WizardManager<WizardContext> wizardManager;

    public PlayerInteractListener(WizardManager<WizardContext> wizardManager) {
        this.wizardManager = wizardManager;
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();
        if(item == null) return;
        final RtagItem rtag = new RtagItem(item);
        final String id = rtag.get("wizardselector");
        if(id == null) return;
        final String generatorType = rtag.get("generator", "type");
        if(generatorType == null) return;
        final Player player = event.getPlayer();
        final WizardContext context = new WizardContext(player, GeneratorType.valueOf(generatorType));
        wizardManager.accept(id, context);
    }
}
