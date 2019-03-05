package com.example.mandula.pocketsoccer.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.adapters.GlobalStatisticsAdapter;
import com.example.mandula.pocketsoccer.database.entity.Game;

import java.sql.Date;
import java.util.ArrayList;

public class GlobalStatisticsActivity extends AppCompatActivity {

    private GlobalStatisticsAdapter adapter;

    private ArrayList<Game> games = new ArrayList<>();

    private void fillTestData() {
        games.add(new Game(1, new Date(System.currentTimeMillis()), "Petar", "Filip", 10, 7));
        games.add(new Game(2, new Date(System.currentTimeMillis()), "Filip", "Emilija", 5, 4));
        games.add(new Game(3, new Date(System.currentTimeMillis()), "Emilija", "Ana", 3, 10));
        games.add(new Game(4, new Date(System.currentTimeMillis()), "Filip", "Emilija", 2, 10));
        games.add(new Game(5, new Date(System.currentTimeMillis()), "Petar", "Marko", 10, 8));
        games.add(new Game(6, new Date(System.currentTimeMillis()), "Marko", "Filip", 10, 6));
        games.add(new Game(7, new Date(System.currentTimeMillis()), "Ana", "Filip", 6, 10));
        games.add(new Game(8, new Date(System.currentTimeMillis()), "Filip", "Ana", 5, 2));
        games.add(new Game(9, new Date(System.currentTimeMillis()), "Filip", "Emilija", 4, 10));
        games.add(new Game(10, new Date(System.currentTimeMillis()), "Ana", "Emilija", 3, 10));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_statistics);

        //TODO Extract data from database

        adapter = new GlobalStatisticsAdapter(this, games);

        RecyclerView recyclerView = findViewById(R.id.global_statistics_list_id);
        recyclerView.setAdapter(adapter);

        fillTestData();
        adapter.notifyDataSetChanged();
    }

    public void onGlobalStatsBackButtonClick(View view) {
        setResult(RESULT_OK, getIntent());
        finish();
    }

    public void onGlobalStatsResetButtonClick(View view) {
        //TODO: Erase from database
        adapter.emptyList();
    }
}
