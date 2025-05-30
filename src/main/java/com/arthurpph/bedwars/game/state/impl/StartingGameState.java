package com.arthurpph.bedwars.game.state.impl;

import com.arthurpph.bedwars.Bedwars;
import com.arthurpph.bedwars.game.Game;
import com.arthurpph.bedwars.game.state.GameState;
import com.arthurpph.bedwars.game.task.GameCountdown;

public class StartingGameState implements GameState {
    private static final int COUNTDOWN_SECONDS = 20;

    private final Bedwars plugin;
    private final Game game;

    public StartingGameState(Bedwars plugin, Game game) {
        this.plugin = plugin;
        this.game = game;
    }

    @Override
    public void onEnable() {
        new GameCountdown(plugin, game, COUNTDOWN_SECONDS, game::nextState).runTaskTimerAsynchronously(plugin, 0L, 20L);
    }

    @Override
    public void onDisable() {}

    @Override
    public GameState getNextGameState() {
        return new InProgressGameState(plugin, game);
    }
}
