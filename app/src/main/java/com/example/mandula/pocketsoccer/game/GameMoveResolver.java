package com.example.mandula.pocketsoccer.game;

import com.example.mandula.pocketsoccer.common.GameType;
import com.example.mandula.pocketsoccer.game.gamedata.Disk;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;

public class GameMoveResolver {

    private static final float MOVE_TO_SPEED_CONST = 0.5f;

    private Disk currentDisk = null;
    private float startX;
    private float startY;

    private GameState gameState;
    private GameThreadWorker gameThreadWorker;

    private ComputerPlayer computerPlayer;

    public static final int HOME_TURN_FLAG = 0;
    public static final int AWAY_TURN_FLAG = 1;

    public GameMoveResolver(GameState gameState,
                            GameThreadWorker gameThreadWorker, ComputerPlayer computerPlayer) {
        this.gameState = gameState;
        this.gameThreadWorker = gameThreadWorker;
        this.computerPlayer = computerPlayer;
    }

    public void startMove(Disk disk, float x, float y, int type) {
        if (type == gameState.getTurn()) {
            currentDisk = disk;
            startX = x;
            startY = y;
        } else currentDisk = null;
    }

    public void endMove(float x, float y) {
        if (currentDisk == null) return;

        if (gameState.getTurn() == AWAY_TURN_FLAG &&
                gameState.getGameParameters().getGameType() == GameType.SINGLE_PLAYER) {
            currentDisk = null;
            return;
        }

        float vx = currentDisk.getVx();
        float vy = currentDisk.getVy();

        vx += (x - startX) * MOVE_TO_SPEED_CONST;
        vy += (y - startY) * MOVE_TO_SPEED_CONST;

        currentDisk.setVx(vx);
        currentDisk.setVy(vy);

        int nextTurn = gameState.getTurn();

        if (nextTurn == HOME_TURN_FLAG) nextTurn = AWAY_TURN_FLAG;
        else nextTurn = HOME_TURN_FLAG;

        manageTurn(nextTurn);
    }

    public void discardMove() {
        currentDisk = null;
    }

    public void manageTurn(int turn) {
        gameState.setTurn(turn);
        gameThreadWorker.restartTimeForMove();

        if (turn == AWAY_TURN_FLAG && gameState.getGameParameters().getGameType() == GameType.SINGLE_PLAYER)
            callComputerPlayer();
    }

    public void callComputerPlayer() {
//TODO bot
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);  //computer player thinking

                    computerPlayer.playMove();

                    gameState.setTurn(HOME_TURN_FLAG);
                    gameThreadWorker.restartTimeForMove();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
