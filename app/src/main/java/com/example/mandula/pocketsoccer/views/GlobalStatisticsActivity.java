package com.example.mandula.pocketsoccer.views;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.adapters.GlobalStatisticsAdapter;
import com.example.mandula.pocketsoccer.database.entity.Game;

import java.util.ArrayList;

public class GlobalStatisticsActivity extends AppCompatActivity {

    private GlobalStatisticsAdapter adapter;

    public static final int FACE_TO_FACE_STATS_CALL_CODE = 1;

    private ArrayList<Game> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_statistics);

        //TODO Extract data from database

        games = StatisticsTest.getGames();

        adapter = new GlobalStatisticsAdapter(this, games);

        RecyclerView recyclerView = findViewById(R.id.global_statistics_list_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public void onGlobalStatsBackButtonClick(View view) {
        setResult(RESULT_OK, getIntent());
        finish();
    }

    public void onGlobalStatsResetButtonClick(View view) {
        games.clear();
        adapter.emptyList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == FACE_TO_FACE_STATS_CALL_CODE) {
            adapter.notifyDataSetChanged();
        }
    }
}
