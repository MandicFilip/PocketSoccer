package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball extends CircleFigure {

    public Ball(float x, float y) {
        super(x, y);
        this.r = 0.01f;
    }

    @Override
    public void drawCircle(Canvas canvas, Paint paint, int width, int height) {
        paint.setColor(Color.YELLOW);

        canvas.drawCircle(x * width, y * height, r * canvas.getWidth(), paint);
    }
}
