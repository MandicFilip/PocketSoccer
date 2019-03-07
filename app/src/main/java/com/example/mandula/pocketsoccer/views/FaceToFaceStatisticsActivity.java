package com.example.mandula.pocketsoccer.views;

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
import android.widget.TextView;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.adapters.FaceToFaceStatisticsAdapter;
import com.example.mandula.pocketsoccer.database.datamodel.GameViewModel;
import com.example.mandula.pocketsoccer.database.entity.Game;

import java.util.ArrayList;
import java.util.List;

public class FaceToFaceStatisticsActivity extends AppCompatActivity {

    private TextView victoryNameResultTextView;
    private TextView victoryCountResultTextView;

    private String homeUser;
    private String awayUser;

    private ArrayList<Game> faceToFaceGames = new ArrayList<>();
    private FaceToFaceStatisticsAdapter adapter;

    private GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setContentView(R.layout.face_to_face_statistics_activity);

        victoryNameResultTextView = findViewById(R.id.face_to_face_users_name_text_view_id);
        victoryCountResultTextView = findViewById(R.id.face_to_face_users_result_text_view_id);

        Intent intent = getIntent();
        homeUser = intent.getStringExtra("HOME_USER");
        awayUser = intent.getStringExtra("AWAY_USER");

        fillList();
    }

    private void extractGamesBetweenPlayers(List<Game> games) {
        faceToFaceGames.clear();
        for (Game game: games) {
            String tmpHome = game.getHomeUser();
            String tmpAway = game.getAwayUser();
            if ((tmpHome.equals(homeUser) && (tmpAway.equals(awayUser))) ||
                    (tmpAway.equals(homeUser) && (tmpHome.equals(awayUser)))) {
                faceToFaceGames.add(game);
            }
        }
    }

    private void fillList() {

        //TODO: Extract form DB
        ViewModelProvider provider = ViewModelProviders.of(this);

        viewModel = provider.get(GameViewModel.class);
        viewModel.getAllGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> games) {
                if (games == null) return;

                extractGamesBetweenPlayers(games);
                adapter.setGames(faceToFaceGames);
                calcVictoryCount();
            }
        });

        adapter = new FaceToFaceStatisticsAdapter(this, faceToFaceGames);

        RecyclerView recyclerView = findViewById(R.id.face_to_face_statistics_list_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void calcVictoryCount() {
        int homeWins = 0;
        int awayWins = 0;

        for (Game game: faceToFaceGames) {
            if (homeUser.equals(game.getHomeUser())) {
                homeWins += checkIfHomePlayerWon(game);
                awayWins += checkIfAwayPlayerWon(game);
            } else {
                homeWins += checkIfAwayPlayerWon(game);
                awayWins += checkIfHomePlayerWon(game);
            }
        }

        //print in bottom
        victoryNameResultTextView.setText(String.format("%s vs %s wins:", homeUser, awayUser));
        victoryCountResultTextView.setText(String.format("%s : %s", Integer.toString(homeWins), Integer.toString(awayWins)));
    }

    private int checkIfHomePlayerWon(Game game) {
        return (game.getHomeGoals() > game.getAwayGoals())? 1 : 0;
    }

    private int checkIfAwayPlayerWon(Game game) {
        return (game.getAwayGoals() > game.getHomeGoals())? 1 : 0;
    }

    public void onFaceToFaceBackClicked(View view) {
        setResult(RESULT_OK, getIntent());
        finish();
    }

    public void onFaceToFaceResetClick(View view) {
        viewModel.deleteGamesBetweenTwoPlayers(homeUser, awayUser);
        victoryCountResultTextView.setText(String.format("%s : %s", Integer.toString(0), Integer.toString(0)));
    }
}
