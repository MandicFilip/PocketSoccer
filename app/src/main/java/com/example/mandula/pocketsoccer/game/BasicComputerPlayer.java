package com.example.mandula.pocketsoccer.game;

import com.example.mandula.pocketsoccer.game.gamedata.Ball;
import com.example.mandula.pocketsoccer.game.gamedata.Disk;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;

import java.util.ArrayList;

public class BasicComputerPlayer extends ComputerPlayer {

    private static final float DISTANCE_TO_SPEED_CONST = 0.5f;

    public BasicComputerPlayer(GameState gameState) {
        super(gameState);
    }

    @Override
    public void playStandardMove() {
        //pick disk
        ArrayList<Disk> disks = gameState.getAwayDisks();
        Disk disk0 = disks.get(0);
        Disk disk1 = disks.get(1);
        Disk disk2 = disks.get(2);
        Disk diskToUse = disk0;
        Ball ball = gameState.getBall();

        float disk0BallDistance = calcTwoDotsDistance(disk0.getX(), disk0.getY(),
                ball.getX(), ball.getY());
        float disk1BallDistance = calcTwoDotsDistance(disk1.getX(), disk1.getY(),
                ball.getX(), ball.getY());
        float disk2BallDistance = calcTwoDotsDistance(disk2.getX(), disk2.getY(),
                ball.getX(), ball.getY());

        //closest disk which is right from the ball
        boolean foundDiskRightFromBall = false;
        float distance = 0;
        if (disk0.getX() > ball.getX()) {
            foundDiskRightFromBall = true;
            distance = disk0BallDistance;
            diskToUse = disk0;
        }

        if ((disk1.getX() > ball.getX()) && (!foundDiskRightFromBall || disk1BallDistance < distance)) {
            foundDiskRightFromBall = true;
            distance = disk1BallDistance;
            diskToUse = disk1;
        }

        if ((disk2.getX() > ball.getX()) && (!foundDiskRightFromBall || disk2BallDistance < distance)) {
            foundDiskRightFromBall = true;
            diskToUse = disk2;
        }

        if (!foundDiskRightFromBall) {
            if (disk0BallDistance < disk1BallDistance && disk0BallDistance < disk2BallDistance) {
                diskToUse = disk0;
            }
            if (disk1BallDistance < disk0BallDistance && disk1BallDistance < disk2BallDistance) {
                diskToUse = disk1;
            }
            if (disk2BallDistance < disk0BallDistance && disk2BallDistance < disk1BallDistance) {
                diskToUse = disk2;
            }
        }
        float correction = ball.getRadius();
        makeMove(diskToUse, ball, correction);
    }

    @Override
    public void playFirstMove() {
        makeMove(gameState.getAwayDisks().get(0), gameState.getBall(), 0);
    }

    private void makeMove(Disk disk, Ball ball, float correction) {

        float hitPositionX = ball.getX() + correction;
        float hitPositionY = ball.getY();

        disk.setVx((hitPositionX - disk.getX()) * DISTANCE_TO_SPEED_CONST);
        disk.setVy((hitPositionY - disk.getY()) * DISTANCE_TO_SPEED_CONST);
    }

    private float calcTwoDotsDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
