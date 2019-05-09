package com.example.mandula.pocketsoccer.game.gamedata;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.mandula.pocketsoccer.common.FlagManager;

public class Disk extends CircleFigure {
    //TODO change with images
    private Drawable colorFlag;
    private Drawable greyFlag;

    private boolean home;

    public Disk(float x, float y, boolean home) {
        super(x, y);
        this.r = 0.05f;

        this.home = home;
    }

    @Override
    public void drawCircle(Canvas canvas, Paint paint, int width, int height, boolean turn) {
        if (colorFlag == null || greyFlag == null) {

            if (home) {
                colorFlag = FlagManager.getHomeNormalFlag();
                greyFlag = FlagManager.getHomeGreyFlag();
            } else {
                colorFlag = FlagManager.getAwayNormalFlag();
                greyFlag = FlagManager.getAwayGreyFlag();
            }

            if (colorFlag == null || greyFlag == null)
                return;
        }

        if (turn) {
            colorFlag.setBounds((int)((x - r) * width), (int)((y - r) * width),
                    (int)((x + r) * width), (int)((y + r) * width));
            colorFlag.draw(canvas);
        } else {
            greyFlag.setBounds((int)((x - r) * width), (int)((y - r) * width),
                    (int)((x + r) * width), (int)((y + r) * width));
            greyFlag.draw(canvas);
        }
    }
}
