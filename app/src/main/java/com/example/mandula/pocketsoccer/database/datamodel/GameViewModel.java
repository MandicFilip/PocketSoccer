package com.example.mandula.pocketsoccer.database.datamodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.mandula.pocketsoccer.database.entity.Game;
import com.example.mandula.pocketsoccer.database.repository.Repository;

import java.util.List;

public class GameViewModel extends AndroidViewModel {

    private Repository gameRepository;
    private LiveData<List<Game>> games;

    public GameViewModel(@NonNull Application application) {
        super(application);
        this.gameRepository = new Repository(application);
        games = gameRepository.getAllGames();
    }

    public void insert(final Game Game) {
        gameRepository.insert(Game);
    }

    public void deleteGamesBetweenTwoPlayers(final String homeUser, final String awayUser) {
        gameRepository.deleteGamesBetweenTwoPlayers(homeUser, awayUser);
    }

    public void deleteAllGames() {
        gameRepository.deleteAllGames();
    }

    public LiveData<List<Game>> getAllGames() {
        return games;
    }
}
