package com.example.agentzengyu.zy2048.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.app.Config;
import com.example.agentzengyu.zy2048.entity.Square;
import com.example.agentzengyu.zy2048.view.SquareView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private int mode;
    private TextView mtvScore, mtvBest;
    private SquareView msvGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mode = getIntent().getIntExtra(Config.MODE, Config.NEW);
        initView();
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

    private void initView() {
        mtvScore = (TextView) findViewById(R.id.tvScore);
        mtvBest = (TextView) findViewById(R.id.tvBest);
        msvGame = (SquareView) findViewById(R.id.svGame);
    }

    private void newGame() {

    }

    private void continueGame() {

    }

    private void setSquare(ArrayList<Square> squares) {
        msvGame.setSquares(squares);
        msvGame.invalidate();
    }
}
