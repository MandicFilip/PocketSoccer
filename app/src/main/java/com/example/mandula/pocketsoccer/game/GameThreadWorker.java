package com.example.mandula.pocketsoccer.game;

import com.example.mandula.pocketsoccer.activities.GameActivity;
import com.example.mandula.pocketsoccer.common.GameEndCondition;
import com.example.mandula.pocketsoccer.common.GameOutcome;
import com.example.mandula.pocketsoccer.game.gamedata.Ball;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;
import com.example.mandula.pocketsoccer.game.gamedata.GoalPost;

import java.util.ArrayList;

public class GameThreadWorker implements Runnable {

    private GameView gameView;
    private CollisionDetector collisionDetector;
    private boolean activeGame = false;
    private GameState gameState;
    private GameActivity gameActivity;

    public GameThreadWorker(GameActivity gameActivity, GameView gameView,
                            GameState gameState, CollisionDetector collisionDetector) {
        this.gameActivity = gameActivity;
        this.gameView = gameView;
        this.gameState = gameState;
        this.collisionDetector = collisionDetector;
    }

    public void setActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    @Override
    public void run() {
        while (activeGame) {

            checkIfGoalScored();
            checkGameCondition();

            collisionDetector.detectAndResolveCollisions();
            recalculateNewPositions();
            gameView.repaintState();

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void recalculateNewPositions() {
        //TODO implement
    }

    private void checkGameCondition() {
        GameEndCondition gameEndCondition = gameState.getGameParameters().getGameEndCondition();
        int timeLeft = gameState.getTimeLeft();
        int goals = Math.max(gameState.getGameOutcome().getHomePlayerGoals(),
                gameState.getGameOutcome().getAwayPlayerGoals());

        if (gameEndCondition == GameEndCondition.TIME && timeLeft <= 0) {
            gameActivity.finishGame();
        }

        if (gameEndCondition == GameEndCondition.GOALS && goals >= gameState.getGameParameters().getGoalsLimit()) {
            gameActivity.finishGame();
        }
    }

    private void checkIfGoalScored() {
        Ball ball = gameState.getBall();

        float leftGoalPostRightEnd = gameState.getLeftGoalPosts().get(0).getLength();
        ArrayList<GoalPost> leftGoalPosts = gameState.getLeftGoalPosts();

        float rightGoalPostLeftEnd = 1 - gameState.getRightGoalPosts().get(0).getLength();
        ArrayList<GoalPost> rightGoalPosts = gameState.getLeftGoalPosts();

        final GameOutcome gameOutcome = gameState.getGameOutcome();

        if (leftGoalPostRightEnd > ball.getX()) {
            if (ball.getY() > leftGoalPosts.get(0).getY() && ball.getY() < leftGoalPosts.get(1).getY()) {
                gameOutcome.addAwayPlayerGoal();
                relocateCircles();
                playCheerSound();
            }
        }

        if (rightGoalPostLeftEnd < ball.getX()) {
            if (ball.getY() > rightGoalPosts.get(0).getY() && ball.getY() < rightGoalPosts.get(1).getY()) {
                gameOutcome.addHomePlayerGoal();
                relocateCircles();
                playCheerSound();
            }
        }
    }

    private void relocateCircles() {
        (new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameState.moveToInitialPositions();
                //TODO maybe stop sound
            }
        })).start();

    }

    private void playCheerSound() {
        //TODO implement
    }
}
