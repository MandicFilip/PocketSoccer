package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;

public class GoalPost implements Serializable {
    private int color = Color.BLACK;

    private float length;

    private float x; //left end position
    private float y; //y coordinate

    public static final int STROKE_SIZE = 24;

    public GoalPost(float length, float x, float y) {
        this.length = length;
        this.x = x;
        this.y = y;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void drawGoalPost(Canvas canvas, Paint paint, int width, int height) {
        paint.setColor(color);
        paint.setStrokeWidth(STROKE_SIZE);
        canvas.drawLine(x * width, y * width,
                (x + length) * width + length, y * width, paint);
    }
}
