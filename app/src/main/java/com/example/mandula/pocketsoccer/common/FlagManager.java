package com.example.mandula.pocketsoccer.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.mandula.pocketsoccer.R;

import java.util.HashMap;

public class FlagManager {
    private static HashMap<String, Integer> flagsMap = new HashMap<>();
    private static HashMap<String, Integer> greyFlagsMap = new HashMap<>();

    private static Drawable homeNormalFlag;
    private static Drawable homeGreyFlag;
    private static Drawable awayNormalFlag;
    private static Drawable awayGreyFlag;

    private static Drawable ballImage;

    static {
        flagsMap.put("Argentina", R.drawable.argentina);
        greyFlagsMap.put("Argentina", R.drawable.argentina_grey);

        flagsMap.put("Brazil", R.drawable.brazil);
        greyFlagsMap.put("Brazil", R.drawable.brazil_grey);

        flagsMap.put("England", R.drawable.england);
        greyFlagsMap.put("England", R.drawable.england_grey);

        flagsMap.put("France", R.drawable.france);
        greyFlagsMap.put("France", R.drawable.france_grey);

        flagsMap.put("Germany", R.drawable.germany);
        greyFlagsMap.put("Germany", R.drawable.germany_grey);

        flagsMap.put("Italy", R.drawable.italy);
        greyFlagsMap.put("Italy", R.drawable.italy_grey);

        flagsMap.put("Poland", R.drawable.poland);
        greyFlagsMap.put("Poland", R.drawable.poland_grey);

        flagsMap.put("Russia", R.drawable.russia);
        greyFlagsMap.put("Russia", R.drawable.russia_grey);

        flagsMap.put("Serbia", R.drawable.serbia);
        greyFlagsMap.put("Serbia", R.drawable.serbia_grey);

        flagsMap.put("Spain", R.drawable.spain);
        greyFlagsMap.put("Spain", R.drawable.spain_grey);
    }

    public static Integer getCountryFlag(String country) {
        return flagsMap.get(country);
    }

    public static Integer getCountryGreyFlag(String country) {
        return greyFlagsMap.get(country);
    }

    public static void prepareFlagsAndBallForGame(Context context, String home, String away) {
        homeNormalFlag = context.getResources().getDrawable(getCountryFlag(home), null);
        homeGreyFlag = context.getResources().getDrawable(getCountryGreyFlag(home), null);
        awayNormalFlag = context.getResources().getDrawable(getCountryFlag(away), null);
        awayGreyFlag = context.getResources().getDrawable(getCountryGreyFlag(away), null);

        ballImage = context.getResources().getDrawable(R.drawable.ball, null);
    }

    public static Drawable getHomeNormalFlag() {
        return homeNormalFlag;
    }

    public static Drawable getHomeGreyFlag() {
        return homeGreyFlag;
    }

    public static Drawable getAwayNormalFlag() {
        return awayNormalFlag;
    }

    public static Drawable getAwayGreyFlag() {
        return awayGreyFlag;
    }

    public static Drawable getBallImage() {
        return ballImage;
    }
}
