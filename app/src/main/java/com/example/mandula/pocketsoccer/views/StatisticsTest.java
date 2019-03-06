package com.example.mandula.pocketsoccer.views;

import com.example.mandula.pocketsoccer.database.entity.Game;

import java.util.ArrayList;

public class StatisticsTest {
    private static ArrayList<Game> games = new ArrayList<>();

    static {
        games.add(new Game(1, System.currentTimeMillis(), "Petar", "Filip", 10, 7));
        games.add(new Game(2, System.currentTimeMillis(), "Filip", "Emilija", 5, 4));
        games.add(new Game(3, System.currentTimeMillis(), "Emilija", "Ana", 3, 10));
        games.add(new Game(4, System.currentTimeMillis(), "Filip", "Emilija", 2, 10));
        games.add(new Game(5, System.currentTimeMillis(), "Petar", "Marko", 10, 8));
        games.add(new Game(6, System.currentTimeMillis(), "Marko", "Filip", 10, 6));
        games.add(new Game(7, System.currentTimeMillis(), "Ana", "Filip", 6, 10));
        games.add(new Game(8, System.currentTimeMillis(), "Filip", "Ana", 5, 2));
        games.add(new Game(9, System.currentTimeMillis(), "Emilija", "Filip", 4, 10));
        games.add(new Game(10, System.currentTimeMillis(), "Ana", "Emilija", 3, 10));
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void remove(Game game) {
        for (Game tmpGame : games) {
            if (tmpGame.getGameID() == game.getGameID()) {
                games.remove(game);
                return;
            }
        }
    }
}
