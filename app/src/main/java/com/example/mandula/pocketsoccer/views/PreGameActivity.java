package com.example.mandula.pocketsoccer.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.adapters.LeftPreGameAdapter;
import com.example.mandula.pocketsoccer.adapters.RightPreGameAdapter;
import com.example.mandula.pocketsoccer.common.GameParameters;

import java.util.ArrayList;

public class PreGameActivity extends AppCompatActivity {

    //TODO test, insert countries from resources, add images

    private LeftPreGameAdapter leftPreGameAdapter;
    private RightPreGameAdapter rightPreGameAdapter;

    private GameParameters gameParameters;
    private ArrayList<String> countries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pre_game);

        Intent intent = getIntent();
        gameParameters = (GameParameters) intent.getSerializableExtra("GAME_PARAMETERS");

        initCountriesLists();

        fillList();
    }

    private void fillList() {
        countries.add("Serbia");
        countries.add("England");
        countries.add("France");
        countries.add("Italy");
        countries.add("Germany");
        countries.add("Poland");
        countries.add("Russia");

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
            Toast.makeText(this, "Same country for both players", Toast.LENGTH_SHORT).show();
            return;
        }

        gameParameters.setFirstPlayerName(countries.get(left));
        gameParameters.setSecondPlayerName(countries.get(right));

        Intent intent = getIntent();
        intent.putExtra("GAME_ON", true);
        setResult(RESULT_OK, intent);
    }

    public void onCancelGameClick(View view) {
        Intent intent = getIntent();
        intent.putExtra("GAME_ON", false);
        setResult(RESULT_OK, intent);
    }
}
