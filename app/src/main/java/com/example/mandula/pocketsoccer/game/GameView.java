package com.example.mandula.pocketsoccer.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class GameView extends android.support.v7.widget.AppCompatImageView {

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void repaintState() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas); //TODO implement
    }
}
