package com.example.agentzengyu.zy2048.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.app.Config;
import com.example.agentzengyu.zy2048.app.ZY2048Application;
import com.example.agentzengyu.zy2048.entity.Square;
import com.example.agentzengyu.zy2048.view.SquareView;

import java.util.ArrayList;

/**
 * 游戏界面
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private ZY2048Application application = null;
    private GameReceiver receiver;
    private TextView mtvScore, mtvBest;
    private TextView mtvPopupwindowScore;
    private EditText metPopupwindowName;
    private SquareView msvGame;
    private PopupWindow popupWindow;
    private int mode = 0;
    private int SCORE = 0, BEST = 0;
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
        startGame();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mtvScore = (TextView) findViewById(R.id.tvScore);
        mtvBest = (TextView) findViewById(R.id.tvBest);
        msvGame = (SquareView) findViewById(R.id.svGame);
    }

    /**
     * 开始游戏
     */
    private void startGame() {
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

    /**
     * 新游戏入口
     */
    private void newGame() {
        application.getService().newGame();
    }

    /**
     * 继续游戏入口
     */
    private void continueGame() {
        application.getService().continueGame();
    }

    private void showNoArchive() {
        View view = getLayoutInflater().inflate(R.layout.popupwindow_no_archive, null);
        view.findViewById(R.id.btnReturn).setOnClickListener(this);
        view.findViewById(R.id.btnNew).setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(msvGame, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.3f;
        getWindow().setAttributes(layoutParams);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 1f;
                getWindow().setAttributes(layoutParams);
            }
        });
    }

    private void showGameOver() {
        View view = getLayoutInflater().inflate(R.layout.popupwindow_game_over, null);
        view.findViewById(R.id.btnReturn).setOnClickListener(this);
        view.findViewById(R.id.btnNew).setOnClickListener(this);
        mtvPopupwindowScore = (TextView) view.findViewById(R.id.tvScore);
        mtvPopupwindowScore.setText(SCORE);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        popupWindow.showAtLocation(msvGame, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.3f;
        getWindow().setAttributes(layoutParams);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 1f;
                getWindow().setAttributes(layoutParams);
            }
        });
    }

    private void showNewRecord() {
        View view = getLayoutInflater().inflate(R.layout.popupwindow_new_record, null);
        view.findViewById(R.id.btnReturn).setOnClickListener(this);
        view.findViewById(R.id.btnNew).setOnClickListener(this);
        mtvPopupwindowScore = (TextView) view.findViewById(R.id.tvScore);
        mtvPopupwindowScore.setText(SCORE);
        metPopupwindowName = (EditText)view.findViewById(R.id.etName);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        popupWindow.showAtLocation(msvGame, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.3f;
        getWindow().setAttributes(layoutParams);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 1f;
                getWindow().setAttributes(layoutParams);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReturn:
                finish();
                break;
            case R.id.btnNew:
                popupWindow.dismiss();
                mode = 0;
                startGame();
                break;
            default:
                break;
        }
    }

    /**
     * 游戏广播接收器
     */
    public class GameReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra(Config.STATE);
            Log.e("state", state);
            switch (state) {
                case Config.INITIALIZE:
                    BEST = intent.getIntExtra(Config.BEST, 0);
                    SCORE = intent.getIntExtra(Config.SCORE, 0);
                    squares.clear();
                    squares.addAll((ArrayList<Square>) intent.getSerializableExtra(Config.SQUARES));
                    mtvBest.setText(String.valueOf(BEST));
                    mtvScore.setText(String.valueOf(SCORE));
                    msvGame.setSquares(squares);
                    msvGame.invalidate();
                    if (squares.size() != 16) {
                        showNoArchive();
                    }
                    break;
                case Config.UPDATE:
                    SCORE = intent.getIntExtra(Config.SCORE, 0);
                    squares.clear();
                    squares.addAll((ArrayList<Square>) intent.getSerializableExtra(Config.SQUARES));
                    mtvScore.setText(String.valueOf(SCORE));
                    msvGame.setSquares(squares);
                    msvGame.invalidate();
                    break;
                case Config.GAMEOVER:
                    showGameOver();
                    break;
                case Config.NEWRECORD:
                    showNewRecord();
                    break;
                default:
                    break;
            }
        }
    }
}
