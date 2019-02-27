package com.example.mandula.pocketsoccer.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.adapters.LeftPreGameAdapter;
import com.example.mandula.pocketsoccer.adapters.RightPreGameAdapter;
import com.example.mandula.pocketsoccer.common.GameParameters;

import java.util.ArrayList;
import java.util.Arrays;

public class PreGameActivity extends AppCompatActivity {

    //TODO add images

    private LeftPreGameAdapter leftPreGameAdapter;
    private RightPreGameAdapter rightPreGameAdapter;

    private GameParameters gameParameters;
    private ArrayList<String> countries = new ArrayList<>();

    private EditText homeUserEditText;
    private EditText awayUserEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pre_game);

        Intent intent = getIntent();
        gameParameters = (GameParameters) intent.getSerializableExtra("GAME_PARAMETERS");

        homeUserEditText = findViewById(R.id.left_edit_text_id);
        awayUserEditText = findViewById(R.id.right_edit_text_id);

        homeUserEditText.setText(gameParameters.getFirstPlayerName());
        awayUserEditText.setText(gameParameters.getSecondPlayerName());

        initCountriesLists();

        fillList();
    }

    private void fillList() {
        String[] countiesArray = getResources().getStringArray(R.array.countries);
        countries.addAll(Arrays.asList(countiesArray));

        leftPreGameAdapter.notifyDataSetChanged();
        rightPreGameAdapter.notifyDataSetChanged();
    }

    private void initCountriesLists() {
        leftPreGameAdapter = new LeftPreGameAdapter(this, countries);
        rightPreGameAdapter = new RightPreGameAdapter(this, countries);

        RecyclerView recyclerViewLeft = findViewById(R.id.left_list_view_id);
        RecyclerView recyclerViewRight = findViewById(R.id.right_list_view_id);

        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLeft.setAdapter(leftPreGameAdapter);

        recyclerViewRight.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRight.setAdapter(rightPreGameAdapter);
    }

    public void onStartGameClick(View view) {

        int left = leftPreGameAdapter.getSelected();
        int right = rightPreGameAdapter.getSelected();

        if (left == right) {
            Toast.makeText(this, "Error! Same country for both players", Toast.LENGTH_SHORT).show();
            return;
        }

        gameParameters.setFirstPlayerFlag(left);
        gameParameters.setSecondPlayerFlag(right);

        String homeUser = homeUserEditText.getText().toString();
        String awayUser = awayUserEditText.getText().toString();

        if (homeUser.equals("")) {
            homeUser = "Home";
        }

        if (awayUser.equals("")) {
            awayUser = "Away";
        }

        if (homeUser.equals(awayUser)) {
            Toast.makeText(this, "Error! Both players has same username", Toast.LENGTH_SHORT).show();
            return;
        }

        gameParameters.setFirstPlayerName(homeUser);
        gameParameters.setSecondPlayerName(awayUser);

        Intent intent = getIntent();
        intent.putExtra("GAME_PARAMETERS", gameParameters);
        intent.putExtra("GAME_ON", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancelGameClick(View view) {
        Intent intent = getIntent();
        intent.putExtra("GAME_PARAMETERS", gameParameters);
        intent.putExtra("GAME_ON", false);
        setResult(RESULT_OK, intent);
        finish();
    }
}
