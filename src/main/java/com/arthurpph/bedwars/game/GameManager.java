package com.arthurpph.bedwars.game;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.game.state.GameState;
import com.arthurpph.bedwars.util.WorldUtils;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {
    private final Bedwars plugin;
    private final Map<UUID, Game> games;

    public GameManager(Bedwars plugin) {
        this.plugin = plugin;
        this.games = new HashMap<>();
    }

    public Game createNewGame(String worldName) {
        final GameWorld gameWorld = WorldUtils.createGameWorld(worldName);
        final Game newGame = new Game(plugin, gameWorld);
        games.put(newGame.getUniqueId(), newGame);
        return newGame;
    }

    public void addPlayer(UUID gameId, Player player) {
        getGame(gameId).ifPresent(game -> game.addPlayer(player.getUniqueId()));
    }

    public boolean isPlayerInGame(Player player) {
        return getGame(player.getUniqueId()).isPresent();
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
                .filter(game -> game.getPlayers().contains(uniqueId))
                .findFirst();
    }

    private Optional<Game> getGame(UUID id) {
        return Optional.ofNullable(games.get(id));
    }
}
