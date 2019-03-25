package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Disk extends CircleFigure {
    private float diskSize;

    //TODO change with images
    private int colorToUse;


    @Override
    public void drawCircle(Canvas canvas, Paint paint, int width, int height) {
        paint.setColor(colorToUse);

        canvas.drawCircle(x * width, y * height, diskSize * canvas.getWidth(), paint);
    }
}
