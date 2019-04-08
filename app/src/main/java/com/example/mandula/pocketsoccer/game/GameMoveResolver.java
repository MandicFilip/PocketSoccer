package com.example.mandula.pocketsoccer.game;

import com.example.mandula.pocketsoccer.game.gamedata.Disk;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;

public class GameMoveResolver {

    private static final float MOVE_TO_SPEED_CONST = 1f; //TODO change

    private Disk currentDisk = null;
    private float startX;
    private float startY;

    private GameState gameState;
    private GameThreadWorker gameThreadWorker;

    private ComputerPlayer computerPlayer;

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

        float vx = currentDisk.getVx();
        float vy = currentDisk.getVy();

        vx += (x - startX) * MOVE_TO_SPEED_CONST;
        vy += (y - startY) * MOVE_TO_SPEED_CONST;

        currentDisk.setVx(vx);
        currentDisk.setVy(vy);

        manageTurn();
    }

    private void manageTurn() {
        gameState.changeTurn();
        gameThreadWorker.restartTimeForMove();
//TODO bot
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);  //computer player thinking
//
//                    computerPlayer.playMove();
//
//                    gameState.changeTurn();
//                    gameThreadWorker.restartTimeForMove();
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
