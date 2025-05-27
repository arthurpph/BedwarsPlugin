package com.arthurpph.bedwars.game.state.impl;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.game.Game;
import com.arthurpph.bedwars.game.state.GameState;

public class LobbyGameState implements GameState {
    private final Bedwars plugin;
    private final Game game;

    public LobbyGameState(Bedwars plugin, Game game) {
        this.plugin = plugin;
        this.game = game;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public GameState getNextGameState() {
        return new StartingGameState(plugin, game);
    }
}
