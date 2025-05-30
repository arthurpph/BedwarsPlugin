package com.arthurpph.bedwars.game;

import com.arthurpph.bedwars.game.team.TeamColor;

import java.util.UUID;

public class GamePlayer {
    private final UUID uuid;
    private TeamColor teamColor;

    public GamePlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(TeamColor teamColor) {
        this.teamColor = teamColor;
    }
}

