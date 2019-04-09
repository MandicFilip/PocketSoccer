package com.example.mandula.pocketsoccer.common;

import java.io.Serializable;

public class GameParameters implements Serializable, Cloneable {
    private String firstPlayerName;
    private String secondPlayerName;

    private String firstPlayerCountry;  //resource id
    private String secondPlayerCountry; //resource id

    private Background background;
    private GameEndCondition gameEndCondition;
    private Speed gameSpeed;
    private GameType gameType;

    private int minutesToPlay;
    private int goalsLimit;

    public void setPlayersInfo(String firstName, String secondName, String firstFlag, String secondFlag) {
        this.firstPlayerName = firstName;
        this.secondPlayerName = secondName;

        this.firstPlayerCountry = firstFlag;
        this.secondPlayerCountry = secondFlag;
    }

    public void setTimeGameInfo(Background background, Speed gameSpeed, GameType gameType, int minutes) {
        this.background = background;
        this.gameSpeed = gameSpeed;
        this.gameType = gameType;

        gameEndCondition = GameEndCondition.TIME;
        minutesToPlay = minutes;

        goalsLimit = 1;
    }

    public void setGoalsGameInfo(Background background, Speed gameSpeed, GameType gameType, int goals) {
        this.background = background;
        this.gameSpeed = gameSpeed;
        this.gameType = gameType;

        gameEndCondition = GameEndCondition.GOALS;
        minutesToPlay = 0;

        goalsLimit = goals;
    }

    public void setDefaultParameters() {
        firstPlayerName = "Home";
        firstPlayerCountry = "France";

        secondPlayerName = "Away";
        secondPlayerCountry = "Germany";

        background = Background.GRASS;

        gameSpeed = Speed.MEDIUM;
        gameType = GameType.SINGLE_PLAYER;

        gameEndCondition = GameEndCondition.GOALS;

        goalsLimit = 10;
        minutesToPlay = 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();

        GameParameters gameParameters = new GameParameters();

        gameParameters.setPlayersInfo(firstPlayerName, secondPlayerName,
                firstPlayerCountry, secondPlayerCountry);

        if (gameEndCondition == GameEndCondition.TIME) {
            gameParameters.setTimeGameInfo(background, gameSpeed, gameType, minutesToPlay);
        } else {
            gameParameters.setGoalsGameInfo(background, gameSpeed, gameType, goalsLimit);
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

        stringBuilder.append(firstPlayerCountry);
        stringBuilder.append(";");

        stringBuilder.append(secondPlayerCountry);
        stringBuilder.append(";");

        stringBuilder.append(backgroundToString(background));
        stringBuilder.append(";");

        stringBuilder.append(speedToString(gameSpeed));
        stringBuilder.append(";");

        stringBuilder.append(gameTypeToString(gameType));
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
        gameParameters.setFirstPlayerCountry(arr[2]);
        gameParameters.setSecondPlayerCountry(arr[3]);

        gameParameters.setBackground(stringToBackground(arr[4]));
        gameParameters.setGameSpeed(stringToSpeed(arr[5]));
        gameParameters.setGameType(stringToGameType(arr[6]));
        gameParameters.setGameEndCondition(stringToCondition(arr[7]));

        gameParameters.setMinutesToPlay(Integer.parseInt(arr[8]));
        gameParameters.setGoalsLimit(Integer.parseInt(arr[9]));

        return gameParameters;
    }

    private static String backgroundToString(Background background) {
        String ret = "GRASS";
        if (background == Background.HARD) ret = "HARD";
        if (background == Background.INDOOR) ret = "INDOOR";
        return ret;
    }

    private static String speedToString(Speed speed) {
        String ret = "SLOW";
        if (speed == Speed.MEDIUM) ret = "MEDIUM";
        if (speed == Speed.FAST) ret = "FAST";
        return ret;
    }

    private static String gameTypeToString(GameType gameType) {
        if (gameType == GameType.SINGLE_PLAYER) return "SINGLE_PLAYER";
        else return "MULTI_PLAYER";
    }

    private static String conditionToString(GameEndCondition gameEndCondition) {
        return (gameEndCondition == GameEndCondition.GOALS) ? "GOALS" : "TIME";
    }

    private static Background stringToBackground(String s) {
        if ("GRASS".equals(s)) return Background.GRASS;
        if ("HARD".equals(s)) return Background.HARD;
        return Background.INDOOR;
    }

    private static Speed stringToSpeed(String s) {
        if ("SLOW".equals(s)) return Speed.SLOW;
        if ("MEDIUM".equals(s)) return Speed.MEDIUM;
        return Speed.FAST;
    }

    private static GameEndCondition stringToCondition(String s) {
        if ("GOALS".equals(s)) return GameEndCondition.GOALS;
        return GameEndCondition.TIME;
    }

    private static GameType stringToGameType(String s) {
        if ("SINGLE_PLAYER".equals(s)) return GameType.SINGLE_PLAYER;
        return GameType.MULTI_PLAYER;
    }


    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public String getFirstPlayerCountry() {
        return firstPlayerCountry;
    }

    public String getSecondPlayerCountry() {
        return secondPlayerCountry;
    }

    public Background getBackground() {
        return background;
    }

    public GameEndCondition getGameEndCondition() {
        return gameEndCondition;
    }

    public Speed getGameSpeed() {
        return gameSpeed;
    }

    public GameType getGameType() {
        return gameType;
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

    public void setFirstPlayerCountry(String firstPlayerCountry) {
        this.firstPlayerCountry = firstPlayerCountry;
    }

    public void setSecondPlayerCountry(String secondPlayerCountry) {
        this.secondPlayerCountry = secondPlayerCountry;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setGameEndCondition(GameEndCondition gameEndCondition) {
        this.gameEndCondition = gameEndCondition;
    }

    public void setGameSpeed(Speed gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void setMinutesToPlay(int minutesToPlay) {
        this.minutesToPlay = minutesToPlay;
    }

    public void setGoalsLimit(int goalsLimit) {
        this.goalsLimit = goalsLimit;
    }
}
