package com.example.mandula.pocketsoccer.common;

public class GameParameters {
    private String firstPlayerName;
    private String secondPlayerName;

    private int firstPlayerFlag;  //resource id
    private int secondPlayerFlag; //resource id

    private Background background;
    private GameEndCondition gameEndCondition;
    private Speed ballSpeed;
    private Speed disksSpeed;

    private int minutesToPlay;
    private int goalsLimit;

    public void setPlayersInfo(String firstName, String secondName, int firstFlag, int secondFlag) {
        this.firstPlayerName = firstName;
        this.secondPlayerName = secondName;

        this.firstPlayerFlag = firstFlag;
        this.secondPlayerFlag = secondFlag;
    }

    public void setTimeGameInfo(Background background, Speed ballSpeed, Speed disksSpeed, int minutes) {
        this.background = background;
        this.ballSpeed = ballSpeed;
        this.disksSpeed =disksSpeed;

        gameEndCondition = GameEndCondition.TIME;
        minutesToPlay = minutes;

        goalsLimit = -1;
    }

    public void setGoalsGameInfo(Background background, Speed ballSpeed, Speed disksSpeed, int goals) {
        this.background = background;
        this.ballSpeed = ballSpeed;
        this.disksSpeed =disksSpeed;

        gameEndCondition = GameEndCondition.GOALS;
        minutesToPlay = -1;

        goalsLimit = goals;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public int getFirstPlayerFlag() {
        return firstPlayerFlag;
    }

    public int getSecondPlayerFlag() {
        return secondPlayerFlag;
    }

    public Background getBackground() {
        return background;
    }

    public GameEndCondition getGameEndCondition() {
        return gameEndCondition;
    }

    public Speed getBallSpeed() {
        return ballSpeed;
    }

    public Speed getDisksSpeed() {
        return disksSpeed;
    }

    public int getMinutesToPlay() {
        return minutesToPlay;
    }

    public int getGoalsLimit() {
        return goalsLimit;
    }
}
