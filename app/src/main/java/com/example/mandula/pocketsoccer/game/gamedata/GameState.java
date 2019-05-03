package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Color;
import android.util.Log;

import com.example.mandula.pocketsoccer.common.GameOutcome;
import com.example.mandula.pocketsoccer.common.GameParameters;
import com.example.mandula.pocketsoccer.game.GameMoveResolver;

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
    public float goalPostHeight;
    private boolean heightSet = false;

    private boolean isFirstMove = false;
    private boolean isGoalScored = false;

    private static final float BALL_X = 0.5f;
    private static final float BALL_Y = 0.5f;

    private static final float HOME_DISK_0_X = 0.15f;
    private static final float HOME_DISK_0_Y = 0.2f;

    private static final float HOME_DISK_1_X = 0.25f;
    private static final float HOME_DISK_1_Y = 0.5f;

    private static final float HOME_DISK_2_X = 0.15f;
    private static final float HOME_DISK_2_Y = 0.8f;

    private static final float AWAY_DISK_0_X = 0.85f;
    private static final float AWAY_DISK_0_Y = 0.2f;

    private static final float AWAY_DISK_1_X = 0.75f;
    private static final float AWAY_DISK_1_Y = 0.5f;

    private static final float AWAY_DISK_2_X = 0.85f;
    private static final float AWAY_DISK_2_Y = 0.8f;

    public GameState(GameParameters gameParameters) {
        this.gameParameters = gameParameters;

        String homeUser = gameParameters.getFirstPlayerName();
        String awayUser = gameParameters.getSecondPlayerName();

        gameOutcome = new GameOutcome(0, 0, homeUser, awayUser);

        homeDisks = new ArrayList<>();
        awayDisks = new ArrayList<>();

        homeDisks.add(new Disk(Color.BLUE, HOME_DISK_0_X, HOME_DISK_0_Y * screenHeight));
        homeDisks.add(new Disk(Color.BLUE, HOME_DISK_1_X, HOME_DISK_1_Y * screenHeight));
        homeDisks.add(new Disk(Color.BLUE, HOME_DISK_2_X, HOME_DISK_2_Y * screenHeight));

        awayDisks.add(new Disk(Color.RED, AWAY_DISK_0_X, AWAY_DISK_0_Y * screenHeight));
        awayDisks.add(new Disk(Color.RED, AWAY_DISK_1_X, AWAY_DISK_1_Y * screenHeight));
        awayDisks.add(new Disk(Color.RED, AWAY_DISK_2_X, AWAY_DISK_2_Y * screenHeight));

        ball = new Ball(BALL_X, BALL_Y * screenHeight);

        timeLeft = gameParameters.getMinutesToPlay() * 60;

        leftGoalPosts = new ArrayList<>();
        leftGoalPosts.add(new GoalPost(0.1f, 0f, 0.3f * screenHeight));
        leftGoalPosts.add(new GoalPost(0.1f, 0f, 0.7f * screenHeight));

        rightGoalPosts = new ArrayList<>();
        rightGoalPosts.add(new GoalPost(0.1f, 0.9f, 0.3f * screenHeight));
        rightGoalPosts.add(new GoalPost(0.1f, 0.9f, 0.7f * screenHeight));
    }

    public void moveToInitialPositions() {
        homeDisks.get(0).setX(HOME_DISK_0_X);
        homeDisks.get(0).setY(HOME_DISK_0_Y * screenHeight);
        homeDisks.get(0).setVx(0f);
        homeDisks.get(0).setVy(0f);

        homeDisks.get(1).setX(HOME_DISK_1_X);
        homeDisks.get(1).setY(HOME_DISK_1_Y * screenHeight);
        homeDisks.get(1).setVx(0f);
        homeDisks.get(1).setVy(0f);

        homeDisks.get(2).setX(HOME_DISK_2_X);
        homeDisks.get(2).setY(HOME_DISK_2_Y * screenHeight);
        homeDisks.get(2).setVx(0f);
        homeDisks.get(2).setVy(0f);

        awayDisks.get(0).setX(AWAY_DISK_0_X);
        awayDisks.get(0).setY(AWAY_DISK_0_Y * screenHeight);
        awayDisks.get(0).setVx(0f);
        awayDisks.get(0).setVy(0f);

        awayDisks.get(1).setX(AWAY_DISK_1_X);
        awayDisks.get(1).setY(AWAY_DISK_1_Y * screenHeight);
        awayDisks.get(1).setVx(0f);
        awayDisks.get(1).setVy(0f);

        awayDisks.get(2).setX(AWAY_DISK_2_X);
        awayDisks.get(2).setY(AWAY_DISK_2_Y * screenHeight);
        awayDisks.get(2).setVx(0f);
        awayDisks.get(2).setVy(0f);

        ball.setX(BALL_X);
        ball.setY(BALL_Y * screenHeight);
        ball.setVx(0f);
        ball.setVy(0f);
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

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setScreenHeightProportion(float screenHeight, int absHeight) {
        this.screenHeight = screenHeight;
        this.goalPostHeight = GoalPost.STROKE_SIZE / absHeight;
        if (screenHeight != 0) this.heightSet = true;
    }

    public boolean isHeightSet() {
        return heightSet;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public boolean isGoalScored() {
        return isGoalScored;
    }

    public void setGoalScored(boolean goalScored) {
        isGoalScored = goalScored;
    }

    public void logDisksAndBall() {
        Log.d("Home 0 x: ", "" + homeDisks.get(0).getX());
        Log.d("Home 0 y: ", "" + homeDisks.get(0).getY());

        Log.d("Home 1 x: ", "" + homeDisks.get(1).getX());
        Log.d("Home 1 y: ", "" + homeDisks.get(1).getY());

        Log.d("Home 2 x: ", "" + homeDisks.get(2).getX());
        Log.d("Home 2 y: ", "" + homeDisks.get(2).getY());


        Log.d("Away 0 x: ", "" + awayDisks.get(0).getX());
        Log.d("Away 0 y: ", "" + awayDisks.get(0).getY());

        Log.d("Away 1 x: ", "" + awayDisks.get(1).getX());
        Log.d("Away 1 y: ", "" + awayDisks.get(1).getY());

        Log.d("Away 2 x: ", "" + awayDisks.get(2).getX());
        Log.d("Away 2 y: ", "" + awayDisks.get(2).getY());

        Log.d("Ball x: ", "" + ball.getX());
        Log.d("Ball y: ", "" + ball.getY());

    }
}
