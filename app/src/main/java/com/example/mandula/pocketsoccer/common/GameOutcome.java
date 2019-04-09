package com.example.mandula.pocketsoccer.common;

import java.io.Serializable;

public class GameOutcome implements Serializable {
    private int homePlayerGoals;
    private int awayPlayerGoals;

    private String homePlayerName;
    private String awayPlayerName;

    public GameOutcome(int homePlayerGoals, int awayPlayerGoals, String homePlayerName, String awayPlayerName) {
        this.homePlayerGoals = homePlayerGoals;
        this.awayPlayerGoals = awayPlayerGoals;
        this.homePlayerName = homePlayerName;
        this.awayPlayerName = awayPlayerName;
    }

    public void addHomePlayerGoal() {
        homePlayerGoals++;
    }

    public void addAwayPlayerGoal() {
        awayPlayerGoals++;
    }

    public int getHomePlayerGoals() {
        return homePlayerGoals;
    }

    public int getAwayPlayerGoals() {
        return awayPlayerGoals;
    }

    public String getHomePlayerName() {
        return homePlayerName;
    }

    public String getAwayPlayerName() {
        return awayPlayerName;
    }
}
