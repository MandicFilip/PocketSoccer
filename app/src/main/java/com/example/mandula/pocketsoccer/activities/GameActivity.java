package com.example.mandula.pocketsoccer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.common.GameOutcome;
import com.example.mandula.pocketsoccer.common.GameParameters;
import com.example.mandula.pocketsoccer.game.BasicComputerPlayer;
import com.example.mandula.pocketsoccer.game.CollisionDetector;
import com.example.mandula.pocketsoccer.game.GameMoveResolver;
import com.example.mandula.pocketsoccer.game.GameThreadWorker;
import com.example.mandula.pocketsoccer.game.GameView;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;

public class GameActivity extends AppCompatActivity {

    private GameState gameState;
    private GameView gameView;
    private GameThreadWorker gameThreadWorker;
    private CollisionDetector collisionDetector;
    private GameMoveResolver gameMoveResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        GameParameters gameParameters =
                (GameParameters) intent.getSerializableExtra("GAME_PARAMETERS");

        gameView = findViewById(R.id.game_view_id);
        gameState = new GameState(gameParameters);

        collisionDetector = new CollisionDetector(gameState);
        gameThreadWorker = new GameThreadWorker(this, gameState, collisionDetector);

        BasicComputerPlayer basicComputerPlayer = new BasicComputerPlayer(gameState);
        gameMoveResolver = new GameMoveResolver(gameState, gameThreadWorker, basicComputerPlayer);

        gameThreadWorker.setGameView(gameView);
        gameThreadWorker.setActiveGame(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameView.setGameState(gameState, gameMoveResolver);
                Thread thread = new Thread(gameThreadWorker);
                thread.start();
            }
        })).start();
        //TODO implement
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //TODO implement
    }

    public void finishGame() {
        gameThreadWorker.setActiveGame(false);
        Intent intent = getIntent();
        intent.putExtra("GAME_OUTCOME", gameState.getGameOutcome());
        setResult(RESULT_OK, intent);
        finish();
    }
}
