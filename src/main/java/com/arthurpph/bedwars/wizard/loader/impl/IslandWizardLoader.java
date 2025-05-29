package com.arthurpph.bedwars.wizard.loader.impl;

import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.team.TeamColor;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.loader.WizardLoader;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.arthurpph.bedwars.wizard.selector.impl.SelectIslandWizardSelector;
import com.arthurpph.bedwars.wizard.selector.impl.SetLocationWizardSelector;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class IslandWizardLoader implements WizardLoader {
    private final WizardManager wizardManager;
    private final ConfigurationManager configManager;
    private final ViewFrame viewFrame;
    private final TeamColor teamColor;
    private final Player player;

    public IslandWizardLoader(WizardManager wizardManager, ConfigurationManager configManager, ViewFrame viewFrame, TeamColor teamColor, Player player) {
        this.wizardManager = wizardManager;
        this.configManager = configManager;
        this.viewFrame = viewFrame;
        this.teamColor = teamColor;
        this.player = player;
    }

    @Override
    public void load() {
        final Inventory inventory = player.getInventory();
        inventory.clear();
        final List<WizardSelector<? extends WizardContext>> wizardSelectors = List.of(
                createSetLocationSelector(Material.STICK, ChatColor.GREEN + "First Corner Stick", "firstcorner"),
                createSetLocationSelector(Material.BLAZE_ROD, ChatColor.GREEN + "Second Corner Stick", "secondcorner"),
                createSetLocationSelector(Material.MAGMA_CREAM, ChatColor.GREEN + "Set Bed Location", "bed"),
                createSetLocationSelector(Material.BOWL, ChatColor.GREEN + "Set Spawn Location", "spawn"),
                createSetLocationSelector(Material.DIAMOND_SWORD, ChatColor.GREEN + "Set Team Upgrade Location", "teamupgrade"),
                createSetLocationSelector(Material.IRON_INGOT, ChatColor.GREEN + "Set Generator Location", "generator"),
                createSetLocationSelector(Material.EGG, ChatColor.GREEN + "Set Shop Location", "shop"),
                createSelectIslandSelector(teamColor.getWoolMaterial(), ChatColor.GREEN + "Change Island")
        );

        for (WizardSelector<? extends WizardContext> wizardSelector : wizardSelectors) {
            inventory.addItem(wizardSelector.getItemStack());
        }
    }

    private SetLocationWizardSelector createSetLocationSelector(Material material, String displayName, String configFieldName) {
        SetLocationWizardSelector selector = new SetLocationWizardSelector(material, displayName, configManager, teamColor, configFieldName);
        wizardManager.register(selector, selector.getCallback());
        return selector;
    }

    private SelectIslandWizardSelector createSelectIslandSelector(Material material, String displayName) {
        SelectIslandWizardSelector selector = new SelectIslandWizardSelector(wizardManager, configManager, material, displayName, viewFrame);
        wizardManager.register(selector, selector.getCallback());
        return selector;
    }
}
