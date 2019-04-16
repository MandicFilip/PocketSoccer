package com.example.mandula.pocketsoccer.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.mandula.pocketsoccer.R;
import com.example.mandula.pocketsoccer.common.GameParameters;
import com.example.mandula.pocketsoccer.game.BasicComputerPlayer;
import com.example.mandula.pocketsoccer.game.CollisionDetector;
import com.example.mandula.pocketsoccer.game.GameMoveResolver;
import com.example.mandula.pocketsoccer.game.GameThreadWorker;
import com.example.mandula.pocketsoccer.game.GameView;
import com.example.mandula.pocketsoccer.game.gamedata.GameState;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameActivity extends AppCompatActivity {

    private boolean isNewGame;
    private GameState gameState;
    private GameView gameView;
    private GameThreadWorker gameThreadWorker;
    private CollisionDetector collisionDetector;
    private GameMoveResolver gameMoveResolver;

    private static boolean isGameInterrupt = false;
    private static final String FILE_NAME = "pocket_soccer_tmp_game_file";

    private static final int RESUME_THREAD_SECONDS_TO_WAIT = 5;

    private GameParameters gameParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        gameParameters = (GameParameters) intent.getSerializableExtra("GAME_PARAMETERS");
        isNewGame = intent.getBooleanExtra("NEW_GAME_FLAG", true);

        gameView = findViewById(R.id.game_view_id);

        initializeGame();
    }

    private void initializeGame() {
        if (isNewGame) {
            isGameInterrupt = false;
            getFileStreamPath(FILE_NAME).delete();
        }

        if (isGameInterrupt) {
            try {
                FileInputStream fis = getApplicationContext().openFileInput(FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
                gameState = (GameState) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            gameView.setPrintResumeScreen(RESUME_THREAD_SECONDS_TO_WAIT);

        } else gameState = new GameState(gameParameters);

        collisionDetector = new CollisionDetector(gameState);
        gameThreadWorker = new GameThreadWorker(this, gameState, collisionDetector);

        BasicComputerPlayer basicComputerPlayer = new BasicComputerPlayer(gameState);
        gameMoveResolver = new GameMoveResolver(gameState, gameThreadWorker, basicComputerPlayer);

        gameThreadWorker.setGameMoveResolver(gameMoveResolver);
        gameThreadWorker.setGameView(gameView);

        gameThreadWorker.setActiveGame();

        //print status screen for same time before game continue
        if (isGameInterrupt) {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    int counter = RESUME_THREAD_SECONDS_TO_WAIT;
                    while (counter > 0) {
                        try {
                            gameView.setPrintResumeScreen(counter);
                            Thread.sleep(1000);
                            counter--;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    gameView.resetPrintResumeScreen();
                }
            })).start();
        }
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
                gameView = findViewById(R.id.game_view_id);
                gameView.setGameState(gameState, gameMoveResolver);
                Thread thread = new Thread(gameThreadWorker);
                thread.start();

                //get game state from file
                initializeGame();
            }
        })).start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        gameThreadWorker.stopGame();
        try {
            FileOutputStream stream = openFileOutput(FILE_NAME, Activity.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
            objectOutputStream.writeObject(gameState);
            stream.getFD().sync();
            isGameInterrupt = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finishGame() {
        gameThreadWorker.stopGame();
        Intent intent = getIntent();
        intent.putExtra("GAME_OUTCOME", gameState.getGameOutcome());
        setResult(RESULT_OK, intent);
        finish();
    }
}
