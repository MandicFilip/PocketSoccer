package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GoalPost {
    private int color = Color.BLACK;

    private float length;

    private float x; //left end position
    private float y; //y coordinate

    private int width;

    public GoalPost(float length, float x, float y, int width) {
        this.length = length;
        this.x = x;
        this.y = y;
        this.width = width;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void drawGoalPost(Canvas canvas, Paint paint, int width, int height) {
        paint.setColor(color);
        paint.setStrokeWidth(width);
        canvas.drawLine(x * height, y * width, x * height + length, y * width, paint);
    }
}
