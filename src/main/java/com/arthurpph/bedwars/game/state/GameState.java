package com.arthurpph.bedwars.game.state;

public interface GameState {
    void onEnable();
    void onDisable();
    GameState getNextGameState();
    default void onTick() {}
//    PRELOBBY, LOBBY, STARTING, INGAME, WON
}
