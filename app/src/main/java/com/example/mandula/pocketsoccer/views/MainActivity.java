package com.example.mandula.pocketsoccer.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.common.GameParameters;

public class MainActivity extends AppCompatActivity {

    private GameParameters gameParameters;

    private static final int PARAMETERS_ACTIVITY_CODE = 5;
    private static final int PRE_GAME_ACTIVITY_CODE = 2;
    private static final int GAME_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
        String readValue = sharedPreferences.getString("GAME_PARAMETERS", "NONE");

        if ("NONE".equals(readValue) || readValue == null) {
            gameParameters = new GameParameters();
            gameParameters.setDefaultParameters();
        } else {
            gameParameters = GameParameters.unpackFromString(readValue);
        }
        printOnToast(gameParameters);
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

            printOnToast(gameParameters);

        } else if (requestCode == PRE_GAME_ACTIVITY_CODE){
            boolean gameOn = data.getBooleanExtra("GAME_ON", false);

            if (gameOn) { //TODO: start game activity
                gameParameters = (GameParameters) data.getSerializableExtra("GAME_PARAMETERS");
                Toast.makeText(this, "Starting game!", Toast.LENGTH_SHORT).show();
            }
        }
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
