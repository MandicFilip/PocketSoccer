package com.example.mandula.pocketsoccer.game;

import com.example.mandula.pocketsoccer.game.gamedata.Ball;
import com.example.mandula.pocketsoccer.game.gamedata.CircleFigure;
import com.example.mandula.pocketsoccer.game.gamedata.Disk;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;
import com.example.mandula.pocketsoccer.game.gamedata.GoalPost;

import java.util.ArrayList;

public class CollisionDetector {

    private CollisionResolver collisionResolver;

    private GameState gameState;

    public CollisionDetector(GameState gameState) {
        collisionResolver = new CollisionResolver();
        this.gameState = gameState;
    }

    public void detectAndResolveCollisions() {
        detectBallCollisions();
        detectDisksCollisions();
    }

    private void detectBallCollisions() {
        ArrayList<Disk> homeDisks = gameState.getHomeDisks();
        ArrayList<Disk> awayDisks = gameState.getAwayDisks();

        Ball ball = gameState.getBall();

        for (int i = 0; i < homeDisks.size(); i++) {
            if (checkCirclesCollision(ball, homeDisks.get(i))) {
                collisionResolver.resolveDiskAndBallCollision(homeDisks.get(i), ball);
                //TODO play sound
            }
        }

        for (int i = 0; i < awayDisks.size(); i++) {
            if (checkCirclesCollision(ball, awayDisks.get(i))) {
                collisionResolver.resolveDiskAndBallCollision(awayDisks.get(i), ball);
                //TODO play sound
            }
        }

        //ball and border collisions
        if (checkCircleAndUpBorderCollision(ball))
            collisionResolver.resolveBallAndUpBorderCollision(ball);

        if (checkCircleAndDownBorderCollision(ball))
            collisionResolver.resolveBallAndDownBorderCollision(ball);

        if (checkCircleAndLeftBorderCollision(ball))
            collisionResolver.resolveBallAndLeftBorderCollision(ball);

        if (checkCircleAndRightBorderCollision(ball))
            collisionResolver.resolveBallAndRightBorderCollision(ball);

        //ball and goal posts collisions
        if (checkCircleAndGoalPostUpCollision(ball))
            collisionResolver.resolveBallAndGoalPostUpCollision(ball);

        if (checkCircleAndGoalPostDownCollision(ball))
            collisionResolver.resolveBallAndGoalPostDownCollision(ball);

        if (checkCircleAndLeftUpGoalPostEdgeCollision(ball))
            collisionResolver.resolveBallAndGoalPostEdgeCollision(ball,
                    gameState.getLeftGoalPosts().get(0).getLength(),
                    gameState.getLeftGoalPosts().get(0).getY());

        if (checkCircleAndLeftDownGoalPostEdgeCollision(ball))
            collisionResolver.resolveBallAndGoalPostEdgeCollision(ball,
                    gameState.getLeftGoalPosts().get(1).getLength(),
                    gameState.getLeftGoalPosts().get(1).getY());

        if (checkCircleAndRightUpGoalPostEdgeCollision(ball))
            collisionResolver.resolveBallAndGoalPostEdgeCollision(ball,
                    1 - gameState.getRightGoalPosts().get(0).getLength(),
                    gameState.getRightGoalPosts().get(0).getY());

        if (checkCircleAndRightDownGoalPostEdgeCollision(ball))
            collisionResolver.resolveBallAndGoalPostEdgeCollision(ball,
                    1 - gameState.getRightGoalPosts().get(1).getLength(),
                    gameState.getRightGoalPosts().get(1).getY());
    }

    private void detectDisksCollisions() {
        ArrayList<Disk> homeDisks = gameState.getHomeDisks();
        ArrayList<Disk> awayDisks = gameState.getAwayDisks();

        //check collisions with same team
        if (checkCirclesCollision(homeDisks.get(0), homeDisks.get(1))) {
            collisionResolver.resolveDisksCollision(homeDisks.get(0), homeDisks.get(1));
        }

        if (checkCirclesCollision(homeDisks.get(0), homeDisks.get(2))) {
            collisionResolver.resolveDisksCollision(homeDisks.get(0), homeDisks.get(2));
        }

        if (checkCirclesCollision(homeDisks.get(1), homeDisks.get(2))) {
            collisionResolver.resolveDisksCollision(homeDisks.get(1), homeDisks.get(2));
        }

        if (checkCirclesCollision(awayDisks.get(0), awayDisks.get(1))) {
            collisionResolver.resolveDisksCollision(awayDisks.get(0), awayDisks.get(1));
        }

        if (checkCirclesCollision(awayDisks.get(0), awayDisks.get(2))) {
            collisionResolver.resolveDisksCollision(awayDisks.get(0), awayDisks.get(2));
        }

        if (checkCirclesCollision(awayDisks.get(1), awayDisks.get(2))) {
            collisionResolver.resolveDisksCollision(awayDisks.get(1), awayDisks.get(2));
        }

        //check collisions with opposite team
        for (int i = 0; i < homeDisks.size(); i++) {
            for (int j = 0; j < awayDisks.size(); j++) {
                if (checkCirclesCollision(homeDisks.get(i), awayDisks.get(j))) {
                    collisionResolver.resolveDisksCollision(homeDisks.get(i), awayDisks.get(j));
                }
            }
        }

        //check collisions with borders
        for (int i = 0; i < homeDisks.size(); i++) {
            if (checkCircleAndUpBorderCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndUpBorderCollision(homeDisks.get(i));

            if (checkCircleAndDownBorderCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndDownBorderCollision(homeDisks.get(i));

            if (checkCircleAndLeftBorderCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndLeftBorderCollision(homeDisks.get(i));

            if (checkCircleAndRightBorderCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndRightBorderCollision(homeDisks.get(i));
        }

        for (int i = 0; i < awayDisks.size(); i++) {
            if (checkCircleAndUpBorderCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndUpBorderCollision(awayDisks.get(i));

            if (checkCircleAndDownBorderCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndDownBorderCollision(awayDisks.get(i));

            if (checkCircleAndLeftBorderCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndLeftBorderCollision(awayDisks.get(i));

            if (checkCircleAndRightBorderCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndRightBorderCollision(awayDisks.get(i));
        }

        //check collisions with goals

        for (int i = 0; i < homeDisks.size(); i++) {
            if (checkCircleAndGoalPostUpCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostUpSizeCollision(homeDisks.get(i));

            if (checkCircleAndGoalPostDownCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostDownSizeCollision(homeDisks.get(i));

            if (checkCircleAndLeftUpGoalPostEdgeCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostEdgeCollision(homeDisks.get(i),
                        gameState.getLeftGoalPosts().get(0).getLength(),
                        gameState.getLeftGoalPosts().get(0).getY());

            if (checkCircleAndLeftDownGoalPostEdgeCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostEdgeCollision(homeDisks.get(i),
                        gameState.getLeftGoalPosts().get(1).getLength(),
                        gameState.getLeftGoalPosts().get(1).getY());

            if (checkCircleAndRightUpGoalPostEdgeCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostEdgeCollision(homeDisks.get(i),
                        1 - gameState.getRightGoalPosts().get(0).getLength(),
                        gameState.getRightGoalPosts().get(0).getY());

            if (checkCircleAndRightDownGoalPostEdgeCollision(homeDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostEdgeCollision(homeDisks.get(i),
                        1 - gameState.getRightGoalPosts().get(1).getLength(),
                        gameState.getRightGoalPosts().get(1).getY());
        }

        for (int i = 0; i < awayDisks.size(); i++) {
            if (checkCircleAndGoalPostUpCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostUpSizeCollision(awayDisks.get(i));

            if (checkCircleAndGoalPostDownCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostDownSizeCollision(awayDisks.get(i));

            if (checkCircleAndLeftUpGoalPostEdgeCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostEdgeCollision(awayDisks.get(i),
                        gameState.getLeftGoalPosts().get(0).getLength(),
                        gameState.getLeftGoalPosts().get(0).getY());

            if (checkCircleAndLeftDownGoalPostEdgeCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostEdgeCollision(awayDisks.get(i),
                        gameState.getLeftGoalPosts().get(1).getLength(),
                        gameState.getLeftGoalPosts().get(1).getY());

            if (checkCircleAndRightUpGoalPostEdgeCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostEdgeCollision(awayDisks.get(i),
                        1 - gameState.getRightGoalPosts().get(0).getLength(),
                        gameState.getRightGoalPosts().get(0).getY());

            if (checkCircleAndRightDownGoalPostEdgeCollision(awayDisks.get(i)))
                collisionResolver.resolveDiskAndGoalPostEdgeCollision(awayDisks.get(i),
                        1 - gameState.getRightGoalPosts().get(1).getLength(),
                        gameState.getRightGoalPosts().get(1).getY());
        }
    }

    private boolean checkCirclesCollision(CircleFigure circleFigure1, CircleFigure circleFigure2) {
        float distance = calcTwoDotsDistance(circleFigure1.getX(), circleFigure1.getY(),
                circleFigure2.getX(), circleFigure2.getY());
        return distance < (circleFigure1.getRadius() + circleFigure2.getRadius());
    }

    private boolean checkCircleAndUpBorderCollision(CircleFigure circleFigure) {
        return circleFigure.getY() - circleFigure.getRadius() < 0;
    }

    private boolean checkCircleAndDownBorderCollision(CircleFigure circleFigure) {
        return circleFigure.getY() + circleFigure.getRadius() > gameState.screenHeight;
    }

    private boolean checkCircleAndLeftBorderCollision(CircleFigure circleFigure){
        return circleFigure.getX() - circleFigure.getRadius() < 0;
    }

    private boolean checkCircleAndRightBorderCollision(CircleFigure circleFigure) {
        return circleFigure.getX() + circleFigure.getRadius() > gameState.screenWidth;
    }

    private boolean checkCircleAndGoalPostUpCollision(CircleFigure circleFigure) {
        float leftGoalPostRightEnd = gameState.getLeftGoalPosts().get(0).getLength();
        float rightGoalPostLeftEnd = 1 - gameState.getRightGoalPosts().get(0).getLength();

        //left goal posts
        if (circleFigure.getX() < leftGoalPostRightEnd) {
            ArrayList<GoalPost> leftGoalPosts = gameState.getLeftGoalPosts();

            for (int i = 0; i < leftGoalPosts.size(); i++) {
                if (circleFigure.getY() < leftGoalPosts.get(i).getY() &&
                        circleFigure.getY() + circleFigure.getRadius() >
                                leftGoalPosts.get(i).getY() - gameState.goalPostHeight / 2) {
                    return true;
                }
            }
        }

        //right goal posts
        if (circleFigure.getX() > rightGoalPostLeftEnd) {
            ArrayList<GoalPost> rightGoalPosts = gameState.getRightGoalPosts();

            for (int i = 0; i < rightGoalPosts.size(); i++) {
                if (circleFigure.getY() < rightGoalPosts.get(i).getY() &&
                        circleFigure.getY() + circleFigure.getRadius() >
                                rightGoalPosts.get(i).getY() - gameState.goalPostHeight / 2) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkCircleAndGoalPostDownCollision(CircleFigure circleFigure) {
        float leftGoalPostRightEnd = gameState.getLeftGoalPosts().get(0).getLength();
        float rightGoalPostLeftEnd = 1 - gameState.getRightGoalPosts().get(0).getLength();

        if (circleFigure.getX() < leftGoalPostRightEnd) {
            ArrayList<GoalPost> leftGoalPosts = gameState.getLeftGoalPosts();

            for (int i = 0; i < leftGoalPosts.size(); i++) {
                if (circleFigure.getY() > leftGoalPosts.get(i).getY() &&
                        circleFigure.getY() - circleFigure.getRadius() <
                                leftGoalPosts.get(i).getY() + gameState.goalPostHeight / 2) {
                    return true;
                }
            }
        }

        if (circleFigure.getX() > rightGoalPostLeftEnd) {
            ArrayList<GoalPost> rightGoalPosts = gameState.getRightGoalPosts();

            for (int i = 0; i < rightGoalPosts.size(); i++) {
                if (circleFigure.getY() > rightGoalPosts.get(i).getY() &&
                        circleFigure.getY() - circleFigure.getRadius() <
                                rightGoalPosts.get(i).getY() + gameState.goalPostHeight / 2) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkCircleAndLeftUpGoalPostEdgeCollision(CircleFigure circleFigure) {
        float leftGoalPostRightEnd = gameState.getLeftGoalPosts().get(0).getLength();
        ArrayList<GoalPost> leftGoalPosts = gameState.getLeftGoalPosts();

        float distance = calcTwoDotsDistance(leftGoalPostRightEnd, leftGoalPosts.get(0).getY(),
                circleFigure.getX(), circleFigure.getY());
        return leftGoalPostRightEnd < circleFigure.getX() && distance < circleFigure.getRadius();
    }

    private boolean checkCircleAndLeftDownGoalPostEdgeCollision(CircleFigure circleFigure) {
        float leftGoalPostRightEnd = gameState.getLeftGoalPosts().get(1).getLength();
        ArrayList<GoalPost> leftGoalPosts = gameState.getLeftGoalPosts();

        float distance = calcTwoDotsDistance(leftGoalPostRightEnd, leftGoalPosts.get(1).getY(),
                circleFigure.getX(), circleFigure.getY());
        return leftGoalPostRightEnd < circleFigure.getX() && distance < circleFigure.getRadius();

    }

    private boolean checkCircleAndRightUpGoalPostEdgeCollision(CircleFigure circleFigure) {
        float rightGoalPostLeftEnd = 1 - gameState.getRightGoalPosts().get(0).getLength();
        ArrayList<GoalPost> rightGoalPosts = gameState.getRightGoalPosts();

        float distance = calcTwoDotsDistance(rightGoalPostLeftEnd, rightGoalPosts.get(0).getY(),
                circleFigure.getX(), circleFigure.getY());
        return rightGoalPostLeftEnd > circleFigure.getX() && distance < circleFigure.getRadius();

    }

    private boolean checkCircleAndRightDownGoalPostEdgeCollision(CircleFigure circleFigure) {
        float rightGoalPostLeftEnd = 1 - gameState.getRightGoalPosts().get(1).getLength();
        ArrayList<GoalPost> rightGoalPosts = gameState.getRightGoalPosts();

        float distance = calcTwoDotsDistance(rightGoalPostLeftEnd, rightGoalPosts.get(1).getY(),
                circleFigure.getX(), circleFigure.getY());
        return rightGoalPostLeftEnd > circleFigure.getX() && distance < circleFigure.getRadius();

    }

    private float calcTwoDotsDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
