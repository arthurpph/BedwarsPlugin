package com.arthurpph.bedwars.wizard.selector.impl;

import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.team.TeamColor;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.selector.WizardSelector;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class SetLocationWizardSelector extends WizardSelector<WizardContext> {
    private final ConfigurationManager configManager;
    private final TeamColor teamColor;
    private final String configFieldName;

    public SetLocationWizardSelector(Material material, String displayName, ConfigurationManager configManager, TeamColor teamColor, String configFieldName) {
        super(material, displayName);
        this.configManager = configManager;
        this.teamColor = teamColor;
        this.configFieldName = configFieldName;
    }

    @Override
    public Consumer<WizardContext> getCallback() {
        return context -> {
            final Player player = context.getPlayer();
            final Location location = player.getLocation();
            configManager.setIslandLocation(teamColor, configFieldName, location);
            player.sendMessage(ChatColor.GREEN + "Set " + configFieldName + "!");
        };
    }
}
