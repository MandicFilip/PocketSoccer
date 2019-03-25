package com.example.mandula.pocketsoccer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.adapters.GlobalStatisticsAdapter;
import com.example.mandula.pocketsoccer.database.datamodel.GameViewModel;
import com.example.mandula.pocketsoccer.database.entity.Game;

import java.util.ArrayList;
import java.util.List;

public class GlobalStatisticsActivity extends AppCompatActivity {

    private GlobalStatisticsAdapter adapter;

    public static final int FACE_TO_FACE_STATS_CALL_CODE = 3;

    private ArrayList<Game> games = new ArrayList<>();
    private GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_global_statistics);

        ViewModelProvider provider = ViewModelProviders.of(this);

        viewModel = provider.get(GameViewModel.class);
        viewModel.getAllGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> games) {
                adapter.setGames(games);
            }
        });

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
        viewModel.deleteAllGames();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == FACE_TO_FACE_STATS_CALL_CODE) {
            adapter.notifyDataSetChanged();
        }
    }
}
