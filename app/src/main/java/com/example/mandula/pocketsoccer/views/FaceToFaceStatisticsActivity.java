package com.example.mandula.pocketsoccer.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.adapters.FaceToFaceStatisticsAdapter;
import com.example.mandula.pocketsoccer.database.entity.Game;

import java.util.ArrayList;

public class FaceToFaceStatisticsActivity extends AppCompatActivity {

    private TextView victoryNameResultTextView;
    private TextView victoryCountResultTextView;

    private String homeUser;
    private String awayUser;

    private ArrayList<Game> faceToFaceGames = new ArrayList<>();
    private FaceToFaceStatisticsAdapter adapter;

    //test data
    private ArrayList<Game> games = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_to_face_statistics_activity);

        victoryNameResultTextView = findViewById(R.id.face_to_face_users_name_text_view_id);
        victoryCountResultTextView = findViewById(R.id.face_to_face_users_result_text_view_id);

        Intent intent = getIntent();
        homeUser = intent.getStringExtra("HOME_USER");
        awayUser = intent.getStringExtra("AWAY_USER");

        fillList();

        calcVictoryCount();
    }

    private void extractGamesBetweenPlayers() {
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
        games = StatisticsTest.getGames();
        extractGamesBetweenPlayers();

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

        for (Game game: faceToFaceGames) {
            StatisticsTest.remove(game);
        }

        victoryCountResultTextView.setText(String.format("%s : %s", Integer.toString(0), Integer.toString(0)));
        adapter.emptyList();
    }
}
