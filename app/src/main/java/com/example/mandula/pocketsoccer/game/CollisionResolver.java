package com.example.mandula.pocketsoccer.game;

import com.example.mandula.pocketsoccer.game.gamedata.Ball;
import com.example.mandula.pocketsoccer.game.gamedata.Disk;

public class CollisionResolver {

    private static final float COLLISION_BALL_SPEED_LOSS = 0.85f;
    private static final float COLLISION_DISK_SPEED_LOSS = 0.85f;

    private float calcVectorIntensity(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private float calcAngle(float vx, float vy) {
        if (vx > 0) {
            return (float) Math.atan(vy / vx);

        } if (vx < 0) {
            float angle = (float) Math.atan(vy / vx);
            if (angle > 0) angle -= Math.PI;
            else angle += Math.PI;
            return angle;

        } else {
            if (vy >= 0) {
                return (float) (Math.PI / 2);
            } else {
                return (float) Math.PI;
            }
        }
    }

    private void decreaseDiskSpeed(Disk disk) {
        float vx = disk.getVx();
        float vy = disk.getVy();

        vx *= COLLISION_DISK_SPEED_LOSS;
        vy *= COLLISION_DISK_SPEED_LOSS;

        disk.setVx(vx);
        disk.setVy(vy);
    }

    private void decreaseBallSpeed(Ball ball) {
        float vx = ball.getVx();
        float vy = ball.getVy();

        vx *= COLLISION_BALL_SPEED_LOSS;
        vy *= COLLISION_BALL_SPEED_LOSS;

        ball.setVx(vx);
        ball.setVy(vy);
    }

    public void resolveDisksCollision(Disk disk1, Disk disk2) {

        float disk1SpeedAngle = calcAngle(disk1.getVx(), disk1.getVy());
        float disk2SpeedAngle = calcAngle(disk2.getVx(), disk2.getVy());

        float xPositionsDiff = disk2.getX() - disk1.getX();
        float yPositionsDiff = disk2.getY() - disk1.getY();

        float disksDirectionDiffAngle = calcAngle(xPositionsDiff, yPositionsDiff);

        float disk1AngleDifference = disk1SpeedAngle - disksDirectionDiffAngle;
        float disk2AngleDifference = disk2SpeedAngle - disksDirectionDiffAngle;

        float disk1Speed = calcVectorIntensity(0, 0, disk1.getVx(), disk1.getVy());
        float disk2Speed = calcVectorIntensity(0, 0, disk2.getVx(), disk2.getVy());

        float normalOnDisk1 = (float) Math.abs(Math.cos(disk1AngleDifference) * disk1Speed);
        float normalOnDisk2 = (float) Math.abs(Math.cos(disk2AngleDifference) * disk2Speed);

        float midNormal = (normalOnDisk1 + normalOnDisk2) / 2;

        float vx1 = (float) (-1 * Math.cos(disksDirectionDiffAngle) * midNormal +
                                Math.cos(disksDirectionDiffAngle + Math.PI / 2));

        float vy1 = (float) (-1 * Math.sin(disksDirectionDiffAngle) * midNormal +
                Math.sin(disksDirectionDiffAngle + Math.PI / 2));

        float vx2 = (float) (Math.cos(disksDirectionDiffAngle) * midNormal +
                Math.cos(disksDirectionDiffAngle + Math.PI / 2));

        float vy2 = (float) (Math.cos(disksDirectionDiffAngle) * midNormal +
                Math.cos(disksDirectionDiffAngle + Math.PI / 2));

        disk1.setVx(vx1);
        disk1.setVy(vy1);

        disk2.setVx(vx2);
        disk2.setVy(vy2);

        decreaseDiskSpeed(disk1);
        decreaseDiskSpeed(disk2);
    }

    public void resolveDiskAndUpBorderCollision(Disk disk) {
        float vy = disk.getVy();
        vy = Math.abs(vy); //change direction
        disk.setVy(vy);

        decreaseDiskSpeed(disk);
    }

    public void resolveDiskAndDownBorderCollision(Disk disk) {
        float vy = disk.getVy();
        vy = -1 * Math.abs(vy); //change direction
        disk.setVy(vy);

        decreaseDiskSpeed(disk);
    }

    public void resolveDiskAndLeftBorderCollision(Disk disk) {
        float vx = disk.getVx();
        vx = Math.abs(vx); //change direction
        disk.setVx(vx);

        decreaseDiskSpeed(disk);
    }

    public void resolveDiskAndRightBorderCollision(Disk disk) {
        float vx = disk.getVx();
        vx = -1 * Math.abs(vx); //change direction
        disk.setVx(vx);

        decreaseDiskSpeed(disk);
    }

    public void resolveDiskAndGoalPostUpSizeCollision(Disk disk) {
        float vy = disk.getVy();
        vy = -1 * Math.abs(vy); //change direction
        disk.setVy(vy);

        decreaseDiskSpeed(disk);
    }

    public void resolveDiskAndGoalPostDownSizeCollision(Disk disk) {
        float vy = disk.getVy();
        vy = Math.abs(vy); //change direction
        disk.setVy(vy);

        decreaseDiskSpeed(disk);
    }

    public void resolveDiskAndGoalPostEdgeCollision(Disk disk, float postX, float postY) {
        float diskSpeedAngle = calcAngle(disk.getVx(), disk.getVy());

        float xPositionsDiff = postX - disk.getX();
        float yPositionsDiff = postY - disk.getY();

        float directionDiffAngle = calcAngle(xPositionsDiff, yPositionsDiff);

        float diskAngleDifference = diskSpeedAngle - directionDiffAngle;

        float diskSpeed = calcVectorIntensity(0, 0, disk.getVx(), disk.getVy());

        float normal = (float) Math.abs(Math.cos(diskAngleDifference) * diskSpeed);

        float vx = (float) (-1 * Math.cos(directionDiffAngle) * normal +
                Math.cos(directionDiffAngle + Math.PI / 2));

        float vy = (float) (-1 * Math.sin(directionDiffAngle) * normal +
                Math.sin(directionDiffAngle + Math.PI / 2));

        disk.setVx(vx);
        disk.setVy(vy);

        decreaseDiskSpeed(disk);
    }

    public void resolveDiskAndBallCollision(Disk disk, Ball ball) {
        float diskSpeedAngle = calcAngle(disk.getVx(), disk.getVy());
        float ballSpeedAngle = calcAngle(ball.getVx(), ball.getVy());

        float xPositionsDiff = ball.getX() - disk.getX();
        float yPositionsDiff = ball.getY() - disk.getY();

        float circlesDirectionDiffAngle = calcAngle(xPositionsDiff, yPositionsDiff);

        float diskAngleDifference = diskSpeedAngle - circlesDirectionDiffAngle;
        float ballAngleDifference = ballSpeedAngle - circlesDirectionDiffAngle;

        float diskSpeed = calcVectorIntensity(0, 0, disk.getVx(), disk.getVy());
        float ballSpeed = calcVectorIntensity(0, 0, ball.getVx(), ball.getVy());

        float normalOnDisk1 = (float) Math.abs(Math.cos(diskAngleDifference) * diskSpeed);
        float normalOnDisk2 = (float) Math.abs(Math.cos(ballAngleDifference) * ballSpeed);

        float radiusRatioSquare = (float) Math.pow(disk.getRadius() / ball.getRadius(), 2);

        float midNormal = (radiusRatioSquare * normalOnDisk1 + normalOnDisk2) / (radiusRatioSquare + 1);

        float vx1 = (float) (-1 * Math.cos(circlesDirectionDiffAngle) * midNormal +
                Math.cos(circlesDirectionDiffAngle + Math.PI / 2));

        float vy1 = (float) (-1 * Math.sin(circlesDirectionDiffAngle) * midNormal +
                Math.sin(circlesDirectionDiffAngle + Math.PI / 2));

        float vx2 = (float) (Math.cos(circlesDirectionDiffAngle) * midNormal +
                Math.cos(circlesDirectionDiffAngle + Math.PI / 2));

        float vy2 = (float) (Math.cos(circlesDirectionDiffAngle) * midNormal +
                Math.cos(circlesDirectionDiffAngle + Math.PI / 2));

        disk.setVx(vx1);
        disk.setVy(vy1);

        ball.setVx(vx2);
        ball.setVy(vy2);

        decreaseDiskSpeed(disk);
        decreaseBallSpeed(ball);
    }

    public void resolveBallAndUpBorderCollision(Ball ball) {
        float vy = ball.getVy();
        vy = Math.abs(vy); //change direction
        ball.setVy(vy);

        decreaseBallSpeed(ball);
    }

    public void resolveBallAndDownBorderCollision(Ball ball) {
        float vy = ball.getVy();
        vy = -1 * Math.abs(vy); //change direction
        ball.setVy(vy);

        decreaseBallSpeed(ball);
    }

    public void resolveBallAndLeftBorderCollision(Ball ball) {
        float vx = ball.getVx();
        vx = Math.abs(vx); //change direction
        ball.setVx(vx);

        decreaseBallSpeed(ball);
    }

    public void resolveBallAndRightBorderCollision(Ball ball) {
        float vx = ball.getVx();
        vx = -1 * Math.abs(vx); //change direction
        ball.setVx(vx);

        decreaseBallSpeed(ball);
    }

    public void resolveBallAndGoalPostUpCollision(Ball ball) {
        float vy = ball.getVy();
        vy = -1 * Math.abs(vy); //change direction
        ball.setVy(vy);

        decreaseBallSpeed(ball);
    }

    public void resolveBallAndGoalPostDownCollision(Ball ball) {
        float vy = ball.getVy();
        vy = Math.abs(vy); //change direction
        ball.setVy(vy);

        decreaseBallSpeed(ball);
    }

    public void resolveBallAndGoalPostEdgeCollision(Ball ball, float postX, float postY) {
        float ballSpeedAngle = calcAngle(ball.getVx(), ball.getVy());

        float xPositionsDiff = postX - ball.getX();
        float yPositionsDiff = postY - ball.getY();

        float directionDiffAngle = calcAngle(xPositionsDiff, yPositionsDiff);

        float ballAngleDifference = ballSpeedAngle - directionDiffAngle;

        float ballSpeed = calcVectorIntensity(0, 0, ball.getVx(), ball.getVy());

        float normal = (float) Math.abs(Math.cos(ballAngleDifference) * ballSpeed);

        float vx = (float) (-1 * Math.cos(directionDiffAngle) * normal +
                Math.cos(directionDiffAngle + Math.PI / 2));

        float vy = (float) (-1 * Math.sin(directionDiffAngle) * normal +
                Math.sin(directionDiffAngle + Math.PI / 2));

        ball.setVx(vx);
        ball.setVy(vy);

        decreaseBallSpeed(ball);
    }
}
