package com.example.mandula.pocketsoccer.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.common.Background;
import com.example.mandula.pocketsoccer.common.GameEndCondition;
import com.example.mandula.pocketsoccer.common.GameParameters;
import com.example.mandula.pocketsoccer.common.Speed;

public class ParametersSetupActivity extends AppCompatActivity {

    private RadioButton grassRadio;
    private RadioButton hardRadio;
    private RadioButton indoorRadio;

    private RadioButton timeRadio;
    private RadioButton goalsRadio;

    private RadioButton ballSlowRadio;
    private RadioButton ballMediumRadio;
    private RadioButton ballFastRadio;
    private RadioButton disksSlowRadio;
    private RadioButton disksMediumRadio;
    private RadioButton disksFastRadio;

    private EditText editText;

    private GameParameters passedParameters;

    private GameParameters changedParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters_setup);

        Intent intent = getIntent();

        GameParameters gameParameters =
                (GameParameters) intent.getSerializableExtra("CURRENT_PARAMETERS");

        connectWithGUI();

        setupGUI(gameParameters);

        passedParameters = gameParameters;
        try {
            changedParameters = (GameParameters) passedParameters.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void connectWithGUI() {
        hardRadio = findViewById(R.id.hard_radio_id);
        grassRadio = findViewById(R.id.grass_radio_id);
        indoorRadio = findViewById(R.id.indoor_radio_id);

        timeRadio = findViewById(R.id.time_radio_id);
        goalsRadio = findViewById(R.id.goals_radio_id);

        ballSlowRadio = findViewById(R.id.ball_slow_radio_id);
        ballMediumRadio = findViewById(R.id.ball_medium__radio_id);
        ballFastRadio = findViewById(R.id.ball_fast__radio_id);
        disksSlowRadio = findViewById(R.id.disk_slow__radio_id);
        disksMediumRadio = findViewById(R.id.disk_medium_radio_id);
        disksFastRadio = findViewById(R.id.disk_fast_radio_id);

        editText = findViewById(R.id.edit_text_parameters);
    }

    private void setupGUI(GameParameters gameParameters) {
        Background background = gameParameters.getBackground();

        switch (background) {
            case GRASS:
                grassRadio.setFocusedByDefault(true);
                hardRadio.setFocusedByDefault(false);
                indoorRadio.setFocusedByDefault(false);
                break;
            case HARD:
                grassRadio.setFocusedByDefault(false);
                hardRadio.setFocusedByDefault(true);
                indoorRadio.setFocusedByDefault(false);
                break;
            case INDOOR:
                grassRadio.setFocusedByDefault(false);
                hardRadio.setFocusedByDefault(false);
                indoorRadio.setFocusedByDefault(true);
                break;
        }

        GameEndCondition gameEndCondition = gameParameters.getGameEndCondition();

        switch (gameEndCondition) {
            case TIME:
                int minutes = gameParameters.getMinutesToPlay();

                timeRadio.setFocusedByDefault(true);
                goalsRadio.setFocusedByDefault(false);

                editText.setText(Integer.toString(minutes));
                break;
            case GOALS:
                int goals = gameParameters.getGoalsLimit();

                timeRadio.setFocusedByDefault(false);
                goalsRadio.setFocusedByDefault(true);

                editText.setText(Integer.toString(goals));
                break;
        }

        Speed ballSpeed = gameParameters.getBallSpeed();

        switch (ballSpeed) {
            case SLOW:
                ballSlowRadio.setFocusedByDefault(true);
                ballMediumRadio.setFocusedByDefault(false);
                ballFastRadio.setFocusedByDefault(false);
                break;
            case MEDIUM:
                ballSlowRadio.setFocusedByDefault(false);
                ballMediumRadio.setFocusedByDefault(true);
                ballFastRadio.setFocusedByDefault(false);
                break;
            case FAST:
                ballSlowRadio.setFocusedByDefault(false);
                ballMediumRadio.setFocusedByDefault(false);
                ballFastRadio.setFocusedByDefault(true);
                break;
        }

        Speed disksSpeed = gameParameters.getDisksSpeed();

        switch (disksSpeed) {
            case SLOW:
                disksSlowRadio.setFocusedByDefault(true);
                disksMediumRadio.setFocusedByDefault(false);
                disksFastRadio.setFocusedByDefault(false);
                break;
            case MEDIUM:
                disksSlowRadio.setFocusedByDefault(false);
                disksMediumRadio.setFocusedByDefault(true);
                disksFastRadio.setFocusedByDefault(false);
                break;
            case FAST:
                disksSlowRadio.setFocusedByDefault(false);
                disksMediumRadio.setFocusedByDefault(false);
                disksFastRadio.setFocusedByDefault(true);
                break;
        }
    }

    public void onGrassClick(View view) {
        changedParameters.setBackground(Background.GRASS);
    }

    public void onHardClick(View view) {
        changedParameters.setBackground(Background.HARD);
    }

    public void onIndoorClick(View view) {
        changedParameters.setBackground(Background.INDOOR);
    }

    public void onGoalsClick(View view) {
        changedParameters.setGameEndCondition(GameEndCondition.GOALS);
    }

    public void onTimeClick(View view) {
        changedParameters.setGameEndCondition(GameEndCondition.TIME);
    }

    public void onSlowBallClick(View view) {
        changedParameters.setBallSpeed(Speed.SLOW);
    }

    public void onMediumSpeedBallClick(View view) {
        changedParameters.setBallSpeed(Speed.MEDIUM);
    }

    public void onFastBallClick(View view) {
        changedParameters.setBallSpeed(Speed.FAST);
    }

    public void onSlowDisksClick(View view) {
        changedParameters.setDisksSpeed(Speed.SLOW);
    }

    public void onMediumSpeedDisksClick(View view) {
        changedParameters.setDisksSpeed(Speed.MEDIUM);
    }

    public void onFastDisksClick(View view) {
        changedParameters.setDisksSpeed(Speed.FAST);
    }

    public void onChangeClick(View view) {
        String text = editText.getText().toString();

        try {
            int num = Integer.parseInt(text);

            if (changedParameters.getGameEndCondition() == GameEndCondition.GOALS) {
                changedParameters.setGoalsLimit(num);
            } else {
                changedParameters.setMinutesToPlay(num);
            }

            finishMyActivity(changedParameters);
        } catch (NumberFormatException e) {
            //print Toast
        }

        }

    public void onDiscardClick(View view) {
        finishMyActivity(passedParameters);
    }

    public void onDefaultClick(View view) {
        changedParameters.setDefaultParameters();
        setupGUI(changedParameters);
    }

    private void finishMyActivity(GameParameters gameParameters) {
        Intent intent = getIntent();
        intent.putExtra("NEXT_PARAMETERS", gameParameters);

        setResult(RESULT_OK, intent);
        finish();
    }

}
