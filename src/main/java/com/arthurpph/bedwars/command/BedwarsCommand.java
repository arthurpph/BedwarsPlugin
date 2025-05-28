package com.arthurpph.bedwars.command;

import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.game.Game;
import com.arthurpph.bedwars.game.GameManager;
import com.arthurpph.bedwars.game.state.GameState;
import com.arthurpph.bedwars.game.state.impl.StartingGameState;
import com.arthurpph.bedwars.wizard.context.WizardContext;
import com.arthurpph.bedwars.wizard.WizardManager;
import com.arthurpph.bedwars.wizard.loader.impl.DefaultWizardLoader;
import me.devnatan.inventoryframework.ViewFrame;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class BedwarsCommand {
    private final GameManager gameManager;
    private final WizardManager wizardManager;
    private final ConfigurationManager configManager;
    private final ViewFrame viewFrame;

    public BedwarsCommand(GameManager gameManager, WizardManager wizardManager, ConfigurationManager configManager, ViewFrame viewFrame) {
        this.gameManager = gameManager;
        this.wizardManager = wizardManager;
        this.configManager = configManager;
        this.viewFrame = viewFrame;
    }

    @Command(
        name = "bedwars",
        description = "Setup the bedwars",
        target = CommandTarget.PLAYER
    )
    public void handleCommand(Context<Player> context) {
        final Player player = context.getSender();
        final Game newGame = gameManager.createNewGame("bedwarsone");
        gameManager.addPlayer(newGame.getUniqueId(), player);
        player.teleport(newGame.getGameWorld().getWorld().getSpawnLocation());
        new DefaultWizardLoader(wizardManager, configManager, viewFrame, player).load();
    }

    @Command(
            name = "bedwars.start",
            description = "Start the bedwars",
            target = CommandTarget.PLAYER
    )
    public void handleCommandStart(Context<Player> context) {
        final Player player = context.getSender();
        final boolean isPlayerInGame = gameManager.isPlayerInGame(player);
        if(!isPlayerInGame) {
            context.sendMessage(ChatColor.RED + "You are not in a bedwars!");
            return;
        }
        final Optional<GameState> nextGameState = gameManager.getNextGameState(player);
        if(nextGameState.isEmpty() || !(nextGameState.get() instanceof StartingGameState)) {
            context.sendMessage(ChatColor.RED + "The game next state does not match with the command.");
            return;
        }
        gameManager.moveToNextState(StartingGameState.class, player);
        context.sendMessage(ChatColor.GREEN + "Bedwars started!");
    }
}
