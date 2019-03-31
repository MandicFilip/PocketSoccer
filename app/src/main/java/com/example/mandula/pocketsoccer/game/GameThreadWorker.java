package com.example.mandula.pocketsoccer.game;

public class GameThreadWorker implements Runnable {

    private GameView gameView;
    private CollisionDetector collisionDetector;
    private boolean activeGame = false;

    public GameThreadWorker(GameView gameView, CollisionDetector collisionDetector) {
        this.gameView = gameView;
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
        //TODO implement
    }

    private void checkIfGoalScored() {
        //TODO implement
    }
}
