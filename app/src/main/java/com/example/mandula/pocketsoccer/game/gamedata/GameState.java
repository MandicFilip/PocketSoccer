package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Color;

import com.example.mandula.pocketsoccer.common.GameOutcome;
import com.example.mandula.pocketsoccer.common.GameParameters;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private ArrayList<Disk> homeDisks;
    private ArrayList<Disk> awayDisks;

    private Ball ball;

    private GameOutcome gameOutcome;
    private GameParameters gameParameters;

    private ArrayList<GoalPost> leftGoalPosts;
    private ArrayList<GoalPost> rightGoalPosts;

    private int timeLeft;
    private int turn = 0; //home player plays first

    public final float screenWidth = 1f;
    public float screenHeight = 0.608f;

    public GameState(GameParameters gameParameters) {
        this.gameParameters = gameParameters;

        String homeUser = gameParameters.getFirstPlayerName();
        String awayUser = gameParameters.getSecondPlayerName();

        gameOutcome = new GameOutcome(0, 0, homeUser, awayUser);

        homeDisks = new ArrayList<>();
        awayDisks = new ArrayList<>();

        homeDisks.add(new Disk(Color.BLUE, 0.15f, 0.2f * screenHeight));
        homeDisks.add(new Disk(Color.BLUE, 0.3f, 0.5f * screenHeight));
        homeDisks.add(new Disk(Color.BLUE, 0.15f, 0.8f * screenHeight));

        awayDisks.add(new Disk(Color.RED, 0.85f, 0.2f * screenHeight));
        awayDisks.add(new Disk(Color.RED, 0.7f, 0.5f * screenHeight));
        awayDisks.add(new Disk(Color.RED, 0.85f, 0.8f * screenHeight));

        ball = new Ball(0.5f, 0.5f * screenHeight);

        timeLeft = gameParameters.getMinutesToPlay() * 60;

        leftGoalPosts = new ArrayList<>();
        leftGoalPosts.add(new GoalPost(0.1f, 0f, 0.3f * screenHeight));
        leftGoalPosts.add(new GoalPost(0.1f, 0f, 0.7f * screenHeight));

        rightGoalPosts = new ArrayList<>();
        rightGoalPosts.add(new GoalPost(0.1f, 0.9f, 0.3f * screenHeight));
        rightGoalPosts.add(new GoalPost(0.1f, 0.9f, 0.7f * screenHeight));
    }

    public void moveToInitialPositions() {
        homeDisks.get(0).setX(0.2f);
        homeDisks.get(0).setY(0.25f);

        homeDisks.get(1).setX(0.4f);
        homeDisks.get(1).setY(0.5f);

        homeDisks.get(2).setX(0.2f);
        homeDisks.get(2).setY(0.75f);

        awayDisks.get(0).setX(0.2f);
        awayDisks.get(0).setY(0.25f);

        awayDisks.get(1).setX(0.4f);
        awayDisks.get(1).setY(0.5f);

        awayDisks.get(2).setX(0.2f);
        awayDisks.get(2).setY(0.75f);

        ball.setX(0.5f);
        ball.setY(0.5f);
    }

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

    public void decreaseTime() {
        timeLeft--;
    }

    public ArrayList<GoalPost> getLeftGoalPosts() {
        return leftGoalPosts;
    }

    public ArrayList<GoalPost> getRightGoalPosts() {
        return rightGoalPosts;
    }

    public int getTurn() {
        return turn;
    }

    public void changeTurn() {
        if (turn == 0) turn = 1;
        else turn = 0;
    }

    public void setScreenHeightProportion(float screenHeight) {
        this.screenHeight = screenHeight;
    }
}
