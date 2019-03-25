package com.example.mandula.pocketsoccer.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.common.GameOutcome;
import com.example.mandula.pocketsoccer.common.GameParameters;

public class GameActivity extends AppCompatActivity {

    private Button button;
    private GameOutcome gameOutcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        button = findViewById(R.id.temp_game_button_id);

        Intent myIntent = getIntent();
        final GameParameters gameParameters = (GameParameters) myIntent.getSerializableExtra("GAME_PARAMETERS");

        int homeGoals = 7;
        int awayGoals = 10;

        int rand = (int) (Math.random() * 10);
        if (rand < 5) {
            homeGoals = homeGoals ^ awayGoals;
            awayGoals = homeGoals ^ awayGoals;
            homeGoals = homeGoals ^ awayGoals;
        }

        gameOutcome = new GameOutcome(homeGoals, awayGoals, gameParameters.getFirstPlayerName(), gameParameters.getSecondPlayerName());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("GAME_OUTCOME", gameOutcome);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
