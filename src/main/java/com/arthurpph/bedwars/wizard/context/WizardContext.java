package com.arthurpph.bedwars.wizard.context;

import org.bukkit.entity.Player;

public class WizardContext {
    private final Player player;

    public WizardContext(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
