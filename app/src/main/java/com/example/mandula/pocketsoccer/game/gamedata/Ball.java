package com.example.mandula.pocketsoccer.game.gamedata;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.example.mandula.pocketsoccer.common.FlagManager;

public class Ball extends CircleFigure {

    private transient Drawable ballImage;

    public Ball(float x, float y) {
        super(x, y);
        this.r = 0.03f;
    }

    @Override
    public void drawCircle(Canvas canvas, Paint paint, int width, int height, boolean turn) {
        if (ballImage == null) {
            ballImage = FlagManager.getBallImage();
            if (ballImage == null) return;
        }

        ballImage.setBounds((int)((x - r) * width), (int)((y - r) * width),
                (int)((x + r) * width), (int)((y + r) * width));
        ballImage.draw(canvas);
    }
}
