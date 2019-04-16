package com.example.mandula.pocketsoccer.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.common.GameOutcome;
import com.example.mandula.pocketsoccer.common.GameParameters;
import com.example.mandula.pocketsoccer.database.datamodel.GameViewModel;
import com.example.mandula.pocketsoccer.database.entity.Game;

public class MainActivity extends AppCompatActivity {

    private GameParameters gameParameters;
    private static boolean hasActiveGame = false;

    private static final int PARAMETERS_ACTIVITY_CODE = 5;
    private static final int PRE_GAME_ACTIVITY_CODE = 2;
    private static final int GAME_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
        String readValue = sharedPreferences.getString("GAME_PARAMETERS", "NONE");
//        hasActiveGame = sharedPreferences.getBoolean("HAS_ACTIVE_GAME", false);

        if ("NONE".equals(readValue) || readValue == null) {
            gameParameters = new GameParameters();
            gameParameters.setDefaultParameters();
        } else {
            gameParameters = GameParameters.unpackFromString(readValue);
        }
        //printOnToast(gameParameters);
    }

    @Override
    protected void onResume() {
        Button resumeButton = findViewById(R.id.button_resume_game_id);
        super.onResume();
        if (!hasActiveGame) resumeButton.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("GAME_PARAMETERS", gameParameters.toString());
        editor.apply();
    }

    public void onResumeGameClick(View view) {
        if (hasActiveGame) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("GAME_PARAMETERS", gameParameters);
            intent.putExtra("NEW_GAME_FLAG", false);
            startActivityForResult(intent, GAME_ACTIVITY_CODE);
        }
    }

    public void onNewGameClick(View view) {
        Intent intent = new Intent(this, PreGameActivity.class);
        intent.putExtra("GAME_PARAMETERS", gameParameters);
        startActivityForResult(intent, PRE_GAME_ACTIVITY_CODE);
    }

    public void onStatisticsClick(View view) {
        Intent intent = new Intent(this, GlobalStatisticsActivity.class);
        startActivity(intent);
    }

    public void onSettingsClick(View view) {
        Intent intent = new Intent(this, ParametersSetupActivity.class);
        intent.putExtra("CURRENT_PARAMETERS", gameParameters);
        startActivityForResult(intent, PARAMETERS_ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == PARAMETERS_ACTIVITY_CODE) {
            if (data != null) {
                gameParameters = (GameParameters) data.getSerializableExtra("NEXT_PARAMETERS");
            }

            SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("GAME_PARAMETERS", gameParameters.toString());
            editor.apply();

            //printOnToast(gameParameters);

        } else if (requestCode == PRE_GAME_ACTIVITY_CODE){
            boolean gameOn = data.getBooleanExtra("GAME_ON", false);

            if (gameOn) {
                gameParameters = (GameParameters) data.getSerializableExtra("GAME_PARAMETERS");
                hasActiveGame = true;
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("GAME_PARAMETERS", gameParameters);
                intent.putExtra("NEW_GAME_FLAG", true);
                startActivityForResult(intent, GAME_ACTIVITY_CODE);
            }

        } else if (requestCode == GAME_ACTIVITY_CODE) {
            hasActiveGame = false;
            GameOutcome gameOutcome = (GameOutcome) data.getSerializableExtra("GAME_OUTCOME");
            insertPlayedGameInDatabase(gameOutcome);

            Intent intent = new Intent(this, FaceToFaceStatisticsActivity.class);
            intent.putExtra("HOME_USER", gameOutcome.getHomePlayerName());
            intent.putExtra("AWAY_USER", gameOutcome.getAwayPlayerName());

            startActivity(intent);
        }
    }

    private void insertPlayedGameInDatabase(GameOutcome gameOutcome) {
        Game game = new Game();
        game.setHomeUser(gameOutcome.getHomePlayerName());
        game.setAwayUser(gameOutcome.getAwayPlayerName());

        game.setHomeGoals(gameOutcome.getHomePlayerGoals());
        game.setAwayGoals(gameOutcome.getAwayPlayerGoals());

        game.setDateTime(System.currentTimeMillis());

        ViewModelProvider provider = ViewModelProviders.of(this);
        GameViewModel viewModel = provider.get(GameViewModel.class);
        viewModel.insert(game);
    }

    private void printOnToast(GameParameters gameParameters) {
        String str = gameParameters.toString();
        String[] arr = str.split(";");
        StringBuilder stringBuilder = new StringBuilder();
        for (String anArr : arr) {
            stringBuilder.append(anArr);
            stringBuilder.append("\n");
        }
        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_LONG).show();
    }
}
