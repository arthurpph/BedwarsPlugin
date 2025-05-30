package com.arthurpph.bedwars.game.state;

public interface GameState {
    void onEnable();
    void onDisable();
    GameState getNextGameState();
//    PRELOBBY, LOBBY, STARTING, INGAME, WON
}
