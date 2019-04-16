package com.example.mandula.pocketsoccer.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mandula.pocketsoccer.common.GameEndCondition;
import com.example.mandula.pocketsoccer.game.gamedata.Disk;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;
import com.example.mandula.pocketsoccer.game.gamedata.GoalPost;

import java.util.ArrayList;

public class GameView extends View {

    private static final float DISK_TOUCH_RADIUS_CONST = 1.2f;
    private static final float RESULT_TEXT_SIZE = 64;

    private boolean printStatusScreen = false;
    private boolean printResumeScreen = false;
    private int resumeGameCounter = 0;
    private GameState gameState;
    private Paint paint = new Paint();

    private int width;
    private int height;

    private GameMoveResolver gameMoveResolver;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPrintResumeScreen(int resumeGameCounter) {
        this.printResumeScreen = true;
        this.resumeGameCounter = resumeGameCounter;
    }

    public void resetPrintResumeScreen() {
        this.printResumeScreen = false;
    }

        public void setGameState(GameState gameState, GameMoveResolver gameMoveResolver) {
        this.gameState = gameState;
        this.gameMoveResolver = gameMoveResolver;

        if (getWidth() != 0) {
            width = getWidth();
            height = getHeight();
            gameState.setScreenHeightProportion(height / (width * 1f), height);
        }
    }

    public void repaintState() {
        post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paintBackground();

        if (gameState == null) return;

        if (printStatusScreen) {
            printGameStatusScreen(canvas);
        } else if (printResumeScreen) {
            printGameResumeScreen(canvas);
        } else printNormalState(canvas);
    }

    private void printGameResumeScreen(Canvas canvas) {
        int homeGoals = gameState.getGameOutcome().getHomePlayerGoals();
        int awayGoals = gameState.getGameOutcome().getAwayPlayerGoals();

        paint.setColor(Color.WHITE);
        paint.setTextSize(RESULT_TEXT_SIZE * 2);

        canvas.drawText(String.format("%s  :  %s",
                Integer.toString(homeGoals), Integer.toString(awayGoals)),
                width * 2 / 5.0f, height * 0.7f, paint);
        canvas.drawText(Integer.toString(resumeGameCounter), width * 0.5f, height * 0.3f, paint);
    }

    private void printNormalState(Canvas canvas) {
        ArrayList<GoalPost> posts = gameState.getLeftGoalPosts();

        for (GoalPost goalPost: posts) {
            goalPost.drawGoalPost(canvas, paint, width, height);
        }

        posts = gameState.getRightGoalPosts();

        for (GoalPost goalPost: posts) {
            goalPost.drawGoalPost(canvas, paint, width, height);
        }

        ArrayList<Disk> disks = gameState.getHomeDisks();

        for(Disk disk: disks) {
            disk.drawCircle(canvas, paint, width, height,
                    gameState.getTurn() == GameMoveResolver.HOME_TURN_FLAG);
        }

        disks = gameState.getAwayDisks();

        for(Disk disk: disks) {
            disk.drawCircle(canvas, paint, width, height,
                    gameState.getTurn() == GameMoveResolver.AWAY_TURN_FLAG);
        }

        gameState.getBall().drawCircle(canvas, paint, width, height, true);

        printResult(canvas);
    }

    private void printGameStatusScreen(Canvas canvas) {
        int homeGoals = gameState.getGameOutcome().getHomePlayerGoals();
        int awayGoals = gameState.getGameOutcome().getAwayPlayerGoals();

        paint.setColor(Color.WHITE);
        paint.setTextSize(RESULT_TEXT_SIZE * 2);

        canvas.drawText(String.format("%s  :  %s",
                Integer.toString(homeGoals), Integer.toString(awayGoals)),
                width * 2 / 5.0f, height * 0.5f, paint);
    }

    private void paintBackground() {
        setBackgroundColor(Color.GREEN);
    }

    private void printResult(Canvas canvas) {
        int seconds = gameState.getTimeLeft();
        int minutes = seconds / 60;
        seconds %= 60;

        int homeGoals = gameState.getGameOutcome().getHomePlayerGoals();
        int awayGoals = gameState.getGameOutcome().getAwayPlayerGoals();

        paint.setColor(Color.WHITE);
        paint.setTextSize(RESULT_TEXT_SIZE);

        canvas.drawText(String.format("%s : %s", Integer.toString(minutes), Integer.toString(seconds)),
                width / 2.0f, height * 0.1f, paint);
        canvas.drawText(Integer.toString(homeGoals), width * 1.0f / 4.0f, height * 0.1f, paint);
        canvas.drawText(Integer.toString(awayGoals), width * 3.0f / 4.0f, height * 0.1f, paint);
    }

    public void setPrintStatusScreen(boolean printEndScreen) {
        this.printStatusScreen = printEndScreen;
    }

    private float calcDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void isTouchOnDisksFromList(float x, float y, ArrayList<Disk> disks, int type) {
        for (Disk disk: disks) {
            float relativeX = x / width;
            float relativeY = y / width;

            float distance = calcDistance(relativeX, relativeY, disk.getX(), disk.getY());

            if (distance < disk.getRadius() * DISK_TOUCH_RADIUS_CONST) {
                gameMoveResolver.startMove(disk, relativeX, relativeY, type);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isTouchOnDisksFromList(event.getX(), event.getY(),
                    gameState.getHomeDisks(), GameMoveResolver.HOME_TURN_FLAG);
            isTouchOnDisksFromList(event.getX(), event.getY(),
                    gameState.getAwayDisks(), GameMoveResolver.AWAY_TURN_FLAG);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            float relativeX = event.getX() / width;
            float relativeY = event.getY() / width;
            gameMoveResolver.endMove(relativeX, relativeY);
        }

        return true;
    }
}
