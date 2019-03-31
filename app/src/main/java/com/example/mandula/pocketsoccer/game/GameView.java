package com.example.mandula.pocketsoccer.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.example.mandula.pocketsoccer.game.gamedata.Disk;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;
import com.example.mandula.pocketsoccer.game.gamedata.GoalPost;

import java.util.ArrayList;

public class GameView extends android.support.v7.widget.AppCompatImageView {

    //TODO implement interfaces for touch recognition

    private GameState gameState;
    private Paint paint = new Paint();

    private int width = getWidth();
    private int height = getHeight();

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void repaintState() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //TODO paint background

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
            disk.drawCircle(canvas, paint, width, height);
        }

        disks = gameState.getAwayDisks();

        for(Disk disk: disks) {
            disk.drawCircle(canvas, paint, width, height);
        }

        gameState.getBall().drawCircle(canvas, paint, width, height);

        //TODO print result - text at the top
    }
}
