package com.example.mandula.pocketsoccer.game;

import com.example.mandula.pocketsoccer.activities.GameActivity;
import com.example.mandula.pocketsoccer.common.GameEndCondition;
import com.example.mandula.pocketsoccer.common.GameOutcome;
import com.example.mandula.pocketsoccer.common.Speed;
import com.example.mandula.pocketsoccer.game.gamedata.Ball;
import com.example.mandula.pocketsoccer.game.gamedata.Disk;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;
import com.example.mandula.pocketsoccer.game.gamedata.GoalPost;

import java.util.ArrayList;

public class GameThreadWorker implements Runnable {

    //TODO fix speed parameters

    private class TimeForMoveTicker implements Runnable{

        private boolean activeMoveTimer = true;

        private static final int TIME_TO_PLAY_MOVE = 50; //5 seconds
        private int timeLeftToPlay = TIME_TO_PLAY_MOVE;

        @Override
        public void run() {
            while (activeMoveTimer) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeLeftToPlay--;
                if (timeLeftToPlay <= 0) {
                    gameMoveResolver.discardMove();

                    int turn = gameState.getTurn();
                    if (turn == GameMoveResolver.HOME_TURN_FLAG) turn = GameMoveResolver.AWAY_TURN_FLAG;
                    else turn = GameMoveResolver.HOME_TURN_FLAG;

                    gameMoveResolver.manageTurn(turn);
                }
            }
        }
    }


    private class GameTimeTicker implements Runnable{

        private boolean activeTimer = true;

        @Override
        public void run() {
            while (activeTimer) {
                try {
                    Thread.sleep(1000);
                    gameState.decreaseTime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final float FRICTION_FACTOR = 0.92f;

    private GameView gameView;
    private CollisionDetector collisionDetector;
    private boolean activeGame = false;
    private GameState gameState;
    private GameActivity gameActivity;
    private GameTimeTicker gameTimeTicker = new GameTimeTicker();
    private TimeForMoveTicker timeForMoveTicker = new TimeForMoveTicker();
    private GameMoveResolver gameMoveResolver;
    private int nextPlayer = GameMoveResolver.HOME_TURN_FLAG;

    public GameThreadWorker(GameActivity gameActivity,
                            GameState gameState, CollisionDetector collisionDetector) {
        this.gameActivity = gameActivity;
        this.gameState = gameState;
        this.collisionDetector = collisionDetector;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void setGameMoveResolver(GameMoveResolver gameMoveResolver) {
        this.gameMoveResolver = gameMoveResolver;
    }

    public void setActiveGame() {

        this.activeGame = true;

        if (gameState.getGameParameters().getGameEndCondition() == GameEndCondition.TIME) {
            (new Thread(gameTimeTicker)).start();
        }
        (new Thread(timeForMoveTicker)).start();
    }

    public void stopGame() {
        activeGame = false;
        gameTimeTicker.activeTimer = false;
        timeForMoveTicker.activeMoveTimer = false;
    }

    public void restartTimeForMove() {
        timeForMoveTicker.timeLeftToPlay = TimeForMoveTicker.TIME_TO_PLAY_MOVE;
    }

    @Override
    public void run() {
        while (activeGame) {

            if (!gameState.isGoalScored()) checkIfGoalScored();
            checkGameEndCondition();

            if (gameState.isHeightSet()) {
                collisionDetector.detectAndResolveCollisions();
                recalculateNewPositions();
            }
            gameView.repaintState();

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void recalculateNewPositions() {
        float speedConst = getConst();

        ArrayList<Disk> homeDisks = gameState.getHomeDisks();
        ArrayList<Disk> awayDisks = gameState.getAwayDisks();
        Ball ball = gameState.getBall();

        for (Disk disk: homeDisks) {
            float x = disk.getX();
            float y = disk.getY();

            x += disk.getVx() * speedConst;
            y += disk.getVy() * speedConst;

            disk.setX(x);
            disk.setY(y);
        }

        for (Disk disk: awayDisks) {
            float x = disk.getX();
            float y = disk.getY();

            x += disk.getVx() * speedConst;
            y += disk.getVy() * speedConst;

            disk.setX(x);
            disk.setY(y);
        }

        float x = ball.getX();
        float y = ball.getY();

        x += ball.getVx() * speedConst * FRICTION_FACTOR;
        y += ball.getVy() * speedConst * FRICTION_FACTOR;

        ball.setX(x);
        ball.setY(y);
    }

    private float getConst() {
        Speed speed = gameState.getGameParameters().getGameSpeed();
        if (speed == Speed.SLOW) return 0.2f;
        if (speed == Speed.MEDIUM) return 0.25f;
        return 0.3f;
    }

    private void checkGameEndCondition() {
        GameEndCondition gameEndCondition = gameState.getGameParameters().getGameEndCondition();
        int timeLeft = gameState.getTimeLeft();

        if (gameEndCondition == GameEndCondition.TIME && timeLeft <= 0) {
            gameView.setPrintStatusScreen(true);
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gameActivity.finishGame();
                }
            })).start();
        }

        int goals = Math.max(gameState.getGameOutcome().getHomePlayerGoals(),
                gameState.getGameOutcome().getAwayPlayerGoals());

        if (gameEndCondition == GameEndCondition.GOALS &&
                goals >= gameState.getGameParameters().getGoalsLimit()) {
            gameView.setPrintStatusScreen(true);
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gameActivity.finishGame();
                }
            })).start();
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
                nextPlayer = GameMoveResolver.HOME_TURN_FLAG;
                relocateCircles();
                playCheerSound();
                gameState.setGoalScored(true);
            }
        }

        if (rightGoalPostLeftEnd < ball.getX()) {
            if (ball.getY() > rightGoalPosts.get(0).getY() && ball.getY() < rightGoalPosts.get(1).getY()) {
                gameOutcome.addHomePlayerGoal();
                nextPlayer = GameMoveResolver.AWAY_TURN_FLAG;
                relocateCircles();
                playCheerSound();
                gameState.setGoalScored(true);
            }
        }
    }

    private void relocateCircles() {
        (new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameMoveResolver.interruptBotThread();
                gameState.moveToInitialPositions();
                gameState.setFirstMove(true);
                gameMoveResolver.manageTurn(nextPlayer);
                gameState.setGoalScored(false);
            }
        })).start();

    }

    private void playCheerSound() {
        GameAudioManager.getInstance().playGoalScoredSound();
    }
}
