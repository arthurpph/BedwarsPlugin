package com.arthurpph.bedwars.game;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.config.ConfigurationManager;
import com.arthurpph.bedwars.game.state.GameState;
import com.arthurpph.bedwars.util.WorldUtils;
import com.arthurpph.bedwars.wizard.WizardManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GameManager {
    private final Bedwars plugin;
    private final WizardManager wizardManager;
    private final ConfigurationManager configManager;
    private final Map<UUID, Game> games;

    public GameManager(Bedwars plugin, WizardManager wizardManager, ConfigurationManager configManager) {
        this.plugin = plugin;
        this.wizardManager = wizardManager;
        this.configManager = configManager;
        this.games = new HashMap<>();
    }

    public Game createNewGame(String worldName) {
        try {
            final GameWorld gameWorld = new GameWorld(WorldUtils.createGameWorld(worldName), configManager);
            final Game newGame = new Game(plugin, gameWorld);
            games.put(newGame.getUniqueId(), newGame);
            return newGame;
        } catch (IllegalStateException e ) {
            plugin.getLogger().severe("Failed to create game: " + e.getMessage());
            return null;
        }
    }

    public void stopGame(Player player) {
        final Game game = getGame(player).orElse(null);
        if(game == null) return;

        game.stop();
        games.remove(game.getUniqueId());
        plugin.getLogger().info("Game with ID " + game.getUniqueId() + " has been stopped.");
    }

    public void addPlayer(UUID gameId, Player player) {
        final Inventory inventory = player.getInventory();
        for(ItemStack item : inventory.getContents()) {
            wizardManager.unregister(item);
        }
        inventory.clear();

        getGame(gameId).ifPresent(game -> {
            game.addPlayer(new GamePlayer(player.getUniqueId()));
            player.teleport(game.getGameWorld().getWorld().getSpawnLocation());
        });
    }

    public boolean isPlayerInGame(Player player) {
        return getGame(player).isPresent();
    }

    public void moveToNextState(Class<? extends GameState> exceptedState, Player player) {
        final Game game = getGame(player).orElse(null);
        if(game == null) return;
        if(!exceptedState.isInstance(game.getState().getNextGameState())) return;
        game.nextState();
    }

    public Optional<GameState> getNextGameState(Player player) {
        return getGame(player)
                .map(Game::getState)
                .map(GameState::getNextGameState);
    }

    private Optional<Game> getGame(Player player) {
        final UUID uniqueId = player.getUniqueId();
        return games.values().stream()
                .filter(game -> game.getPlayers().stream()
                        .anyMatch(gamePlayer -> gamePlayer.getUuid().equals(uniqueId)))
                .findFirst();
    }

    private Optional<Game> getGame(UUID id) {
        return Optional.ofNullable(games.get(id));
    }
}
