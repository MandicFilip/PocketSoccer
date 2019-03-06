package com.example.mandula.pocketsoccer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mandula.pocketsoccer.database.dao.GameDAO;
import com.example.mandula.pocketsoccer.database.entity.Game;


@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase{
    private static RoomDB instance;

    public abstract GameDAO gameDAO();

    public static RoomDB getDB(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomDB.class,
                    "pocket_soccer_db").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public RoomDB() {
    }

}
