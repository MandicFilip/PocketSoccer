package com.example.mandula.pocketsoccer.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.mandula.pocketsoccer.database.entity.Game;

import java.util.List;

@Dao
public interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Game game);

    @Query("DELETE FROM game WHERE ((homeUser = :homeUser) AND (awayUser = :awayUser)) OR ((homeUser = :awayUser) AND (awayUser = :homeUser))")
    void deleteTwoPlayersGames(String homeUser, String awayUser);

    @Query("DELETE FROM game")
    void deleteAll();

    @Query("SELECT * FROM game")
    LiveData<List<Game>> getAllGames();
}
