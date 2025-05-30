package com.arthurpph.bedwars.wizard.view;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.game.team.TeamColor;
import com.arthurpph.bedwars.util.ItemStackUtils;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.loader.impl.IslandWizardLoader;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.ViewType;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.context.SlotClickContext;
import me.devnatan.inventoryframework.state.State;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WizardIslandSelectorView extends View {
    private final Bedwars plugin;
    private final WizardManager wizardManager;
    private final ConfigurationManager configManager;
//    State<Bedwars> plugin = initialState("plugin");
//    State<WizardManager> wizardManager = initialState("wizardManager");
//    State<ConfigurationManager> configManager = initialState("configManager");
    private State<Runnable> onTeamClick = initialState("onTeamClick");

    public WizardIslandSelectorView(Bedwars plugin, WizardManager wizardManager, ConfigurationManager configManager) {
        this.plugin = plugin;
        this.wizardManager = wizardManager;
        this.configManager = configManager;
    }

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.type(ViewType.CHEST);
        config.size(3);
        config.title("Select Island");
        config.cancelInteractions();
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {
        int rowCounter = 2;
        int columnCounter = 1;
        for(TeamColor color : TeamColor.values()) {
            render.slot(rowCounter, columnCounter,
                    new ItemStackUtils.Builder(color.getWoolMaterial())
                            .setDisplayName(color.getChatColor() + color.getFormattedName())
                            .build()
            );
            columnCounter++;
            if(columnCounter > 9) {
                columnCounter = 1;
                rowCounter++;
            }
        }

        render.slot(rowCounter, columnCounter, new ItemStackUtils.Builder(Material.BARRIER)
                .setDisplayName(ChatColor.GREEN + "Exit")
                .build());
    }

    @Override
    public void onClick(@NotNull SlotClickContext click) {
        if(click.isOnEntityContainer()) return;

        final Player player = click.getPlayer();
        final ItemStack itemStack = click.getItem();
        if(itemStack == null) return;
        TeamColor teamColor;
        try {
            teamColor = TeamColor.fromWoolMaterial(itemStack.getType());
        } catch (IllegalArgumentException e) {
            click.closeForPlayer();
            return;
        }

        Runnable onTeamClickState = onTeamClick.get(click);
        if(onTeamClickState != null)
            onTeamClickState.run();

        new IslandWizardLoader(plugin, wizardManager, configManager, getFramework(), teamColor, player).load();
        click.closeForPlayer();
    }
}