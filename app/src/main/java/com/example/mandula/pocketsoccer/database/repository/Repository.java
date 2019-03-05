package com.example.mandula.pocketsoccer.database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.mandula.pocketsoccer.database.RoomDB;
import com.example.mandula.pocketsoccer.database.dao.GameDAO;
import com.example.mandula.pocketsoccer.database.entity.Game;

import java.util.List;

public class Repository {
    private GameDAO gameDAO;
    private LiveData<List<Game>> games;

    public Repository(Application application) {
        RoomDB roomDB = RoomDB.getDB(application);
        gameDAO = roomDB.gameDAO();
        games = gameDAO.getAllGames();
    }

    public void insert(final Game game) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                gameDAO.insert(game);
            }
        }).start();
    }

    public void deleteGamesBetweenTwoPlayers(final Game game) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                gameDAO.deleteTwoPlayersGames(game.getHomeUser(), game.getAwayUser());
            }
        }).start();
    }

    public void deleteAllGames() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                gameDAO.deleteAll();
            }
        }).start();
    }

    public LiveData<List<Game>> getAllGames() {
        return games;
    }

}
