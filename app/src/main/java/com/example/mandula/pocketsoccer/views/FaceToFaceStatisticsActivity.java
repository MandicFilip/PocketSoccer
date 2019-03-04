package com.example.mandula.pocketsoccer.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.adapters.FaceToFaceStatisticsAdapter;
import com.example.mandula.pocketsoccer.database.entities.Game;

import java.sql.Date;
import java.util.ArrayList;

public class FaceToFaceStatisticsActivity extends AppCompatActivity {

    private TextView victoryNameResultTextView;
    private TextView victoryCountResultTextView;
    private RecyclerView recyclerView;

    private String homeUser;
    private String awayUser;

    private ArrayList<Game> faceToFaceGames = new ArrayList<>();
    private FaceToFaceStatisticsAdapter adapter;

    //test data
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
        setContentView(R.layout.face_to_face_statistics_activity);

        victoryNameResultTextView = findViewById(R.id.face_to_face_users_name_text_view_id);
        victoryCountResultTextView = findViewById(R.id.face_to_face_users_result_text_view_id);
        recyclerView = findViewById(R.id.face_to_face_statistics_list_id);

        Intent intent = getIntent();
        homeUser = intent.getStringExtra("HOME_USER");
        awayUser = intent.getStringExtra("AWAY_USER");

        extractGamesBetweenPlayers();

        fillList();

        calcVictoryCount();
    }

    private void extractGamesBetweenPlayers() {
        for (Game game: games) {
            String tmpHome = game.getHomeUser();
            String tmpAway = game.getAwayUser();
            if ((tmpHome.equals(homeUser) || (tmpHome.equals(awayUser))) &&
                    (tmpAway.equals(homeUser) || (tmpAway.equals(awayUser)))) {
                faceToFaceGames.add(game);
            }
        }
    }

    private void fillList() {
        adapter = new FaceToFaceStatisticsAdapter(this, games);
        recyclerView.setAdapter(adapter);
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
        victoryNameResultTextView.setText(homeUser + " vs " + awayUser + " wins:");
        victoryCountResultTextView.setText(Integer.toString(homeWins) + " : " + Integer.toString(awayWins));
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
        //TODO: Erase from database
        adapter.emptyList();
    }
}
