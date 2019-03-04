package com.example.mandula.pocketsoccer.database.entities;

import java.sql.Date;
import java.sql.Time;

public class Game {
    private int gameID;

    private Date date;

    private String homeUser;
    private String awayUser;

    private int homeGoals;
    private int awayGoals;

    public Game(int gameID, Date date, String homeUser, String awayUser, int homeGoals, int awayGoals) {
        this.gameID = gameID;
        this.date = date;
        this.homeUser = homeUser;
        this.awayUser = awayUser;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public Game() {
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHomeUser() {
        return homeUser;
    }

    public void setHomeUser(String homeUser) {
        this.homeUser = homeUser;
    }

    public String getAwayUser() {
        return awayUser;
    }

    public void setAwayUser(String awayUser) {
        this.awayUser = awayUser;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }
}
