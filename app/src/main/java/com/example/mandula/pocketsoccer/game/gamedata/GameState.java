package com.example.mandula.pocketsoccer.game.gamedata;

import com.example.mandula.pocketsoccer.common.GameOutcome;
import com.example.mandula.pocketsoccer.common.GameParameters;

import java.util.ArrayList;

public class GameState {
    private ArrayList<Disk> homeDisks;
    private ArrayList<Disk> awayDisks;

    private Ball ball;

    private GameOutcome gameOutcome;
    private GameParameters gameParameters;

    public GameState(GameParameters gameParameters) {
        this.gameParameters = gameParameters;

        String homeUser = gameParameters.getFirstPlayerName();
        String awayUser = gameParameters.getSecondPlayerName();
    }

    private int timeLeft;

    public ArrayList<Disk> getHomeDisks() {
        return homeDisks;
    }

    public ArrayList<Disk> getAwayDisks() {
        return awayDisks;
    }

    public Ball getBall() {
        return ball;
    }

    public GameOutcome getGameOutcome() {
        return gameOutcome;
    }

    public GameParameters getGameParameters() {
        return gameParameters;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
