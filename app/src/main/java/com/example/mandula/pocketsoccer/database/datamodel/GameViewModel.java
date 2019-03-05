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

    public void deleteGamesBetweenTwoPlayers(final Game Game) {
        gameRepository.deleteGamesBetweenTwoPlayers(Game);
    }

    public void deleteAllGames() {
        gameRepository.deleteAllGames();
    }

    public LiveData<List<Game>> getAllReminders() {
        return games;
    }

    public void readFromBundle(Bundle bundle) {

    }

    public void writeToBundle(Bundle bundle) {

    }
}
