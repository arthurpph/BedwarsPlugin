package com.arthurpph.bedwars.wizard.loader.impl;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.game.team.TeamColor;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.loader.WizardLoader;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import com.arthurpph.bedwars.wizard.selector.impl.ExitWizardSelector;
import com.arthurpph.bedwars.wizard.selector.impl.SelectIslandWizardSelector;
import com.arthurpph.bedwars.wizard.selector.impl.SetLocationWizardSelector;
import me.devnatan.inventoryframework.ViewFrame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class IslandWizardLoader extends WizardLoader {
    private final Bedwars plugin;
    private final ConfigurationManager configManager;
    private final ViewFrame viewFrame;
    private final TeamColor teamColor;

    public IslandWizardLoader(Bedwars plugin, WizardManager wizardManager, ConfigurationManager configManager, ViewFrame viewFrame, TeamColor teamColor, Player player) {
        super(wizardManager, player);
        this.plugin = plugin;
        this.configManager = configManager;
        this.viewFrame = viewFrame;
        this.teamColor = teamColor;
    }

    @Override
    public void load() {
        final Inventory inventory = player.getInventory();
        inventory.clear();

        final List<WizardSelector<? extends WizardContext>> wizardSelectors = List.of(
                createSetLocationSelector(Material.STICK, ChatColor.GREEN + "First Corner Stick", "firstCornerLocation"),
                createSetLocationSelector(Material.BLAZE_ROD, ChatColor.GREEN + "Second Corner Stick", "secondCornerLocation"),
                createSetLocationSelector(Material.MAGMA_CREAM, ChatColor.GREEN + "Set Bed Location", "bedLocation"),
                createSetLocationSelector(Material.BOWL, ChatColor.GREEN + "Set Spawn Location", "spawnLocation"),
                createSetLocationSelector(Material.DIAMOND_SWORD, ChatColor.GREEN + "Set Team Upgrade Location", "teamUpgradeLocation"),
                createSetLocationSelector(Material.IRON_INGOT, ChatColor.GREEN + "Set Generator Location", "generatorLocation"),
                createSetLocationSelector(Material.EGG, ChatColor.GREEN + "Set Shop Location", "shopLocation"),
                createSelectIslandSelector(teamColor.getWoolMaterial(), ChatColor.GREEN + "Change Island"),
                createExitWizardSelector()
        );

        for (WizardSelector<? extends WizardContext> wizardSelector : wizardSelectors) {
            inventory.addItem(wizardSelector.getItemStack());
        }
    }

    private SetLocationWizardSelector createSetLocationSelector(Material material, String displayName, String configFieldName) {
        SetLocationWizardSelector selector = new SetLocationWizardSelector(material, displayName, configManager, teamColor, configFieldName);
        return registerSelector(selector);
    }

    private SelectIslandWizardSelector createSelectIslandSelector(Material material, String displayName) {
        SelectIslandWizardSelector selector = new SelectIslandWizardSelector(wizardManager, configManager, this, material, displayName, viewFrame);
        return registerSelector(selector);
    }

    private ExitWizardSelector createExitWizardSelector() {
        ExitWizardSelector selector = new ExitWizardSelector(Material.BARRIER, ChatColor.RED + "Exit Wizard", this, () -> new DefaultWizardLoader(plugin, wizardManager, configManager, viewFrame, player).load());
        return registerSelector(selector);
    }
}
