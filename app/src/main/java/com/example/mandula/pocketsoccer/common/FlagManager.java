package com.example.mandula.pocketsoccer.common;

import com.example.mandula.pocketsoccer.R;

import java.util.HashMap;

public class FlagManager {

    //TODO add gray images

    private static HashMap<String, Integer> flagsMap = new HashMap<>();

    private static int count = 0;

    static {
        flagsMap.put("Argentina", R.drawable.argentina);

        flagsMap.put("Brazil", R.drawable.brazil);

        flagsMap.put("England", R.drawable.england);

        flagsMap.put("France", R.drawable.france);

        flagsMap.put("Germany", R.drawable.germany);

        flagsMap.put("Italy", R.drawable.italy);

        flagsMap.put("Poland", R.drawable.poland);

        flagsMap.put("Russia", R.drawable.russia);

        flagsMap.put("Serbia", R.drawable.serbia);

        flagsMap.put("Spain", R.drawable.spain);
    }

    public static Integer getCountryFlag(String country) {
        return flagsMap.get(country);
    }
}
