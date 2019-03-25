package com.example.mandula.pocketsoccer.common;

import java.io.Serializable;

public class GameOutcome implements Serializable {
    private int homePlayerGoals;
    private int awayPlayerGoals;

    private String homePlayerName;
    private String awayPlayerName;

    private Outcome outcome;

    public GameOutcome(int homePlayerGoals, int awayPlayerGoals, String homePlayerName, String awayPlayerName) {
        this.homePlayerGoals = homePlayerGoals;
        this.awayPlayerGoals = awayPlayerGoals;
        this.homePlayerName = homePlayerName;
        this.awayPlayerName = awayPlayerName;

        this.outcome = Outcome.TIE;
    }

    public GameOutcome(String homePlayerName, String awayPlayerName) {
        this.homePlayerGoals = 0;
        this.awayPlayerGoals = 0;
        this.homePlayerName = homePlayerName;
        this.awayPlayerName = awayPlayerName;

        this.outcome = Outcome.TIE;
    }

    public void addFirstPlayerGoal() {
        homePlayerGoals++;

        if (homePlayerGoals > awayPlayerGoals) outcome = Outcome.HOME_WIN;
        else if (homePlayerGoals < awayPlayerGoals) outcome = Outcome.AWAY_WIN;
        else outcome = Outcome.TIE;
    }

    public void addSecondPlayerGoal() {
        awayPlayerGoals++;

        if (homePlayerGoals > awayPlayerGoals) outcome = Outcome.HOME_WIN;
        else if (homePlayerGoals < awayPlayerGoals) outcome = Outcome.AWAY_WIN;
        else outcome = Outcome.TIE;
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

    public Outcome getOutcome() {
        return outcome;
    }
}
