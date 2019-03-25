package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class CircleFigure {
    //position
    protected float x;
    protected float y;

    //speed
    protected float vx;
    protected float vy;

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

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public abstract void drawCircle(Canvas canvas, Paint paint, int width, int height);
}
