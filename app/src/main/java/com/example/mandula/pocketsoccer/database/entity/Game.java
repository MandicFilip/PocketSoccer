package com.example.mandula.pocketsoccer.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "game")
public class Game {

    @PrimaryKey(autoGenerate = true)
    private int gameID;

    private long dateTime;

    private String homeUser;
    private String awayUser;

    private int homeGoals;
    private int awayGoals;

    public Game(int gameID, long dateTime, String homeUser, String awayUser, int homeGoals, int awayGoals) {
        this.gameID = gameID;
        this.dateTime = dateTime;
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

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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
