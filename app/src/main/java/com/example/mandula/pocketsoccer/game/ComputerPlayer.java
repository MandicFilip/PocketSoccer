package com.example.mandula.pocketsoccer.game;

import com.example.mandula.pocketsoccer.game.gamedata.GameState;

public abstract class ComputerPlayer {
    protected GameState gameState;

    public ComputerPlayer(GameState gameState) {
        this.gameState = gameState;
    }

    public abstract void playStandardMove();

    public abstract void playFirstMove();
}
