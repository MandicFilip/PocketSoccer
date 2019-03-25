package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball extends CircleFigure {
    private float ballSize;

    @Override
    public void drawCircle(Canvas canvas, Paint paint, int width, int height) {
        paint.setColor(Color.YELLOW);

        canvas.drawCircle(x * width, y * height, ballSize * canvas.getWidth(), paint);
    }
}
