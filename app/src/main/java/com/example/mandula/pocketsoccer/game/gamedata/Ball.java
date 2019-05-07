package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball extends CircleFigure {

    public Ball(float x, float y) {
        super(x, y);
        this.r = 0.03f;
    }

    @Override
    public void drawCircle(Canvas canvas, Paint paint, int width, int height, boolean turn) {
        paint.setColor(Color.YELLOW);

        //TODO change with image
        canvas.drawCircle(x * width, y * width, r * canvas.getWidth(), paint);
    }
}
