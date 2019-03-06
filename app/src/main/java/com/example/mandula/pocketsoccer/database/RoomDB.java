package com.example.mandula.pocketsoccer.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.mandula.pocketsoccer.database.dao.GameDAO;
import com.example.mandula.pocketsoccer.database.entity.Game;

import java.util.List;


@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase{
    private static RoomDB instance;

    public abstract GameDAO gameDAO();

    public RoomDB() { }

    public static RoomDB getDB(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomDB.class,
                    "pocket_soccer_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            new InsertInitData(instance).execute();
                        }
                    })
                    .build();
        }
        return instance;
    }

    private static class InsertInitData extends AsyncTask<Void, Void, Void> {

        private final GameDAO dao;

        InsertInitData(RoomDB db) {
            dao = db.gameDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            List<Game> games = dao.getAllGames().getValue();
            if ((games == null) || (games.size() < 1)) {
                dao.insert(new Game(1, System.currentTimeMillis(), "Petar", "Filip", 10, 7));
                dao.insert(new Game(2, System.currentTimeMillis(), "Filip", "Emilija", 5, 4));
                dao.insert(new Game(3, System.currentTimeMillis(), "Emilija", "Ana", 3, 10));
                dao.insert(new Game(4, System.currentTimeMillis(), "Filip", "Emilija", 2, 10));
                dao.insert(new Game(5, System.currentTimeMillis(), "Petar", "Marko", 10, 8));
                dao.insert(new Game(6, System.currentTimeMillis(), "Marko", "Filip", 10, 6));
                dao.insert(new Game(7, System.currentTimeMillis(), "Ana", "Filip", 6, 10));
                dao.insert(new Game(8, System.currentTimeMillis(), "Filip", "Ana", 5, 2));
                dao.insert(new Game(9, System.currentTimeMillis(), "Emilija", "Filip", 4, 10));
                dao.insert(new Game(10, System.currentTimeMillis(), "Ana", "Emilija", 3, 10));
            }
            return null;
        }
    }

}
