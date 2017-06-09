package com.example.agentzengyu.zy2048.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.app.Config;
import com.example.agentzengyu.zy2048.app.ZY2048Application;
import com.example.agentzengyu.zy2048.entity.Square;
import com.example.agentzengyu.zy2048.view.SquareView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private ZY2048Application application = null;
    private GameReceiver receiver;
    private TextView mtvScore, mtvBest;
    private SquareView msvGame;
    private int mode = 0;
    private ArrayList<Square> squares = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        application = (ZY2048Application) getApplication();
        mode = getIntent().getIntExtra(Config.MODE, Config.NEW);
        initView();
        receiver = new GameReceiver();
        IntentFilter filter = new IntentFilter(Config.GAME);
        registerReceiver(receiver, filter);
        switch (mode) {
            case Config.CONTINUE:
                continueGame();
                break;
            case Config.NEW:
            default:
                newGame();
                break;
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void initView() {
        mtvScore = (TextView) findViewById(R.id.tvScore);
        mtvBest = (TextView) findViewById(R.id.tvBest);
        msvGame = (SquareView) findViewById(R.id.svGame);
    }

    private void newGame() {
        application.getService().newGame();
    }

    private void continueGame() {
        application.getService().continueGame();
    }

    public class GameReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra(Config.STATE);
            Log.e("state", state);
            switch (state) {
                case Config.INITIALIZE:
                    String BEST = intent.getStringExtra(Config.BEST);
                    squares.clear();
                    squares.addAll((ArrayList<Square>) intent.getSerializableExtra(Config.SQUARES));
                    if ("".equals(BEST)){
                        mtvBest.setText("0");
                    }else{
                        mtvBest.setText(BEST);
                    }
                    mtvScore.setText("0");
                    msvGame.setSquares(squares);
                    msvGame.invalidate();
                    break;
                case Config.UPDATE:
                    String SCORE = intent.getStringExtra(Config.SCORE);
                    squares.addAll((ArrayList<Square>) intent.getSerializableExtra(Config.SQUARES));
                    mtvScore.setText("" + SCORE);
                    msvGame.setSquares(squares);
                    msvGame.invalidate();
                    break;
                case Config.END:

                    break;
                default:
                    break;
            }
        }
    }
}
