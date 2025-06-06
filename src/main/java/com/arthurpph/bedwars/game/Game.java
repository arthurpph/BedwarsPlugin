package com.arthurpph.bedwars.game;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.game.state.GameState;
import com.arthurpph.bedwars.game.state.impl.LobbyGameState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    private final UUID id;
    private final GameWorld gameWorld;
    private final List<GamePlayer> players;
    private GameState state;

    public Game(Bedwars plugin, GameWorld gameWorld) {
        this.id = UUID.randomUUID();
        this.gameWorld = gameWorld;
        this.players = new ArrayList<>();
        this.state = new LobbyGameState(plugin, this);
        state.onEnable();
    }

    public UUID getUniqueId() {
        return id;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public GameState getState() {
        return state;
    }

    public void nextState() {
        state.onDisable();
        state = state.getNextGameState();
        state.onEnable();
    }

    public void addPlayer(GamePlayer gamePlayer) {
        players.add(gamePlayer);
    }

    public void stop() {
        gameWorld.getWorld().getPlayers().forEach(player -> player.kickPlayer("The game has been stopped."));
        state.onDisable();
        players.clear();
    }
}
