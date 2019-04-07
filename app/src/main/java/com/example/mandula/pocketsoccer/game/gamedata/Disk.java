package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Disk extends CircleFigure {
    //TODO change with images
    private int colorToUse;

    public Disk(int colorToUse, float x, float y) {
        super(x, y);
        this.colorToUse = colorToUse;
        this.r = 0.07f;
    }

    @Override
    public void drawCircle(Canvas canvas, Paint paint, int width, int height, boolean turn) {
        if (turn) paint.setColor(colorToUse);
        else paint.setColor(Color.BLACK);
        canvas.drawCircle(x * width, y * width, r * canvas.getWidth(), paint);
    }
}
