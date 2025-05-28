package com.arthurpph.bedwars.view;

import com.arthurpph.bedwars.team.TeamColor;
import com.arthurpph.bedwars.util.ItemStackUtils;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.ViewType;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.context.SlotClickContext;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WizardIslandSelectorView extends View {
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

        final ItemStack itemStack = click.getItem();
        if(itemStack == null) return;
        TeamColor color;
        try {
            color = TeamColor.fromWoolMaterial(itemStack.getType());
        } catch (IllegalArgumentException e) {
            click.closeForPlayer();
            return;
        }

        click.getPlayer().sendMessage(color.name());
        click.closeForPlayer();
    }
}