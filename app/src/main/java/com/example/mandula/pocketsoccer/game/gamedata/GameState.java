package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Color;

import com.example.mandula.pocketsoccer.common.GameOutcome;
import com.example.mandula.pocketsoccer.common.GameParameters;

import java.util.ArrayList;

public class GameState {
    private ArrayList<Disk> homeDisks;
    private ArrayList<Disk> awayDisks;

    private Ball ball;

    private GameOutcome gameOutcome;
    private GameParameters gameParameters;

    private ArrayList<GoalPost> leftGoalPosts;
    private ArrayList<GoalPost> rightGoalPosts;

    private int timeLeft;

    public GameState(GameParameters gameParameters) {
        this.gameParameters = gameParameters;

        String homeUser = gameParameters.getFirstPlayerName();
        String awayUser = gameParameters.getSecondPlayerName();

        gameOutcome = new GameOutcome(0, 0, homeUser, awayUser);

        homeDisks = new ArrayList<>();
        awayDisks = new ArrayList<>();

        homeDisks.add(new Disk(Color.BLUE, 0.2f, 0.25f));
        homeDisks.add(new Disk(Color.BLUE, 0.4f, 0.5f));
        homeDisks.add(new Disk(Color.BLUE, 0.2f, 0.75f));

        awayDisks.add(new Disk(Color.RED, 0.8f, 0.25f));
        awayDisks.add(new Disk(Color.RED, 0.6f, 0.5f));
        awayDisks.add(new Disk(Color.RED, 0.8f, 0.75f));

        ball = new Ball(0.5f, 0.5f);

        timeLeft = gameParameters.getMinutesToPlay() * 60;

        leftGoalPosts = new ArrayList<>();
        leftGoalPosts.add(new GoalPost(0.1f, 0f, 0.3f, 4));
        leftGoalPosts.add(new GoalPost(0.1f, 0f, 0.7f, 4));

        rightGoalPosts = new ArrayList<>();
        rightGoalPosts.add(new GoalPost(0.1f, 0.9f, 0.3f, 4));
        rightGoalPosts.add(new GoalPost(0.1f, 0.9f, 0.7f, 4));
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

    public ArrayList<GoalPost> getLeftGoalPosts() {
        return leftGoalPosts;
    }

    public ArrayList<GoalPost> getRightGoalPosts() {
        return rightGoalPosts;
    }
}
