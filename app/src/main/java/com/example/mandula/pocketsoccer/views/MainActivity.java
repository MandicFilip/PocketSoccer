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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
        String readValue = sharedPreferences.getString("GAME_PARAMETERS", "NONE");


        if ("NONE".equals(readValue)) {
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
    }

    public void onStatisticsClick(View view) {
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
            gameParameters = (GameParameters) data.getSerializableExtra("NEXT_PARAMETERS");

            SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("GAME_PARAMETERS", gameParameters.toString());
            editor.apply();

            printOnToast(gameParameters);
        }
    }

    private void printOnToast(GameParameters gameParameters) {
        String str = gameParameters.toString();
        String[] arr = str.split(";");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            stringBuilder.append(arr[i]);
            stringBuilder.append("\n");
        }
        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_LONG).show();
    }
}
