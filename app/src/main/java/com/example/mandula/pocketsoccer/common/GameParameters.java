package com.example.mandula.pocketsoccer.common;

import java.io.Serializable;

public class GameParameters implements Serializable {
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

    public void setDefaultParameters() {
        firstPlayerName = "Home";
        firstPlayerFlag = 1;

        secondPlayerName = "Away";
        secondPlayerFlag = 2;

        background = Background.GRASS;

        ballSpeed = Speed.MEDIUM;
        disksSpeed = Speed.MEDIUM;

        gameEndCondition = GameEndCondition.GOALS;

        goalsLimit = 10;
        minutesToPlay = -1;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();

        GameParameters gameParameters = new GameParameters();

        gameParameters.setPlayersInfo(firstPlayerName, secondPlayerName,
                firstPlayerFlag, secondPlayerFlag);

        if (gameEndCondition == GameEndCondition.TIME) {
            gameParameters.setTimeGameInfo(background, ballSpeed, disksSpeed, minutesToPlay);
        } else {
            gameParameters.setGoalsGameInfo(background, ballSpeed, disksSpeed, goalsLimit);
        }

        return gameParameters;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstPlayerName);
        stringBuilder.append(";");

        stringBuilder.append(secondPlayerName);
        stringBuilder.append(";");

        stringBuilder.append(firstPlayerFlag);
        stringBuilder.append(";");

        stringBuilder.append(secondPlayerFlag);
        stringBuilder.append(";");

        stringBuilder.append(backgroundToString(background));
        stringBuilder.append(";");

        stringBuilder.append(speedToString(ballSpeed));
        stringBuilder.append(";");

        stringBuilder.append(speedToString(disksSpeed));
        stringBuilder.append(";");

        stringBuilder.append(conditionToString(gameEndCondition));
        stringBuilder.append(";");

        stringBuilder.append(Integer.toString(minutesToPlay));
        stringBuilder.append(";");

        stringBuilder.append(Integer.toString(goalsLimit));

        return stringBuilder.toString();
    }

    public static GameParameters unpackFromString(String stringFrom) {
        String[] arr = stringFrom.split(";");

        GameParameters gameParameters = new GameParameters();

        gameParameters.setFirstPlayerName(arr[0]);
        gameParameters.setSecondPlayerName(arr[1]);
        gameParameters.setFirstPlayerFlag(Integer.parseInt(arr[2]));
        gameParameters.setSecondPlayerFlag(Integer.parseInt(arr[3]));

        gameParameters.setBackground(stringToBackground(arr[4]));
        gameParameters.setBallSpeed(stringToSpeed(arr[5]));
        gameParameters.setDisksSpeed(stringToSpeed(arr[6]));
        gameParameters.setGameEndCondition(stringToCondition(arr[7]));

        gameParameters.setMinutesToPlay(Integer.parseInt(arr[8]));
        gameParameters.setGoalsLimit(Integer.parseInt(arr[9]));

        return gameParameters;
    }

    private static String backgroundToString(Background background) {
        int value = 0;
        if (background == Background.HARD) value = 1;
        if (background == Background.INDOOR) value = 2;
        return Integer.toString(value);
    }

    private static String speedToString(Speed speed) {
        int value = 0;
        if (speed == Speed.MEDIUM) value = 1;
        if (speed == Speed.FAST) value = 2;
        return Integer.toString(value);
    }

    private static String conditionToString(GameEndCondition gameEndCondition) {
        int value = (gameEndCondition == GameEndCondition.GOALS) ? 1 : 0;
        return Integer.toString(value);
    }

    private static Background stringToBackground(String s) {
        int value = Integer.parseInt(s);
        if (value == 0) return Background.GRASS;
        if (value == 1) return Background.HARD;
        return Background.INDOOR;
    }

    private static Speed stringToSpeed(String s) {
        int value = Integer.parseInt(s);
        if (value == 0) return Speed.SLOW;
        if (value == 1) return Speed.MEDIUM;
        return Speed.FAST;
    }

    private static GameEndCondition stringToCondition(String s) {
        int value = Integer.parseInt(s);
        if (value == 0) return GameEndCondition.TIME;
        return GameEndCondition.GOALS;
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

    public void setFirstPlayerName(String firstPlayerName) {
        this.firstPlayerName = firstPlayerName;
    }

    public void setSecondPlayerName(String secondPlayerName) {
        this.secondPlayerName = secondPlayerName;
    }

    public void setFirstPlayerFlag(int firstPlayerFlag) {
        this.firstPlayerFlag = firstPlayerFlag;
    }

    public void setSecondPlayerFlag(int secondPlayerFlag) {
        this.secondPlayerFlag = secondPlayerFlag;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setGameEndCondition(GameEndCondition gameEndCondition) {
        this.gameEndCondition = gameEndCondition;
    }

    public void setBallSpeed(Speed ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public void setDisksSpeed(Speed disksSpeed) {
        this.disksSpeed = disksSpeed;
    }

    public void setMinutesToPlay(int minutesToPlay) {
        this.minutesToPlay = minutesToPlay;
    }

    public void setGoalsLimit(int goalsLimit) {
        this.goalsLimit = goalsLimit;
    }
}
