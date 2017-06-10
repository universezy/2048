package com.example.agentzengyu.zy2048.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.agentzengyu.zy2048.app.Config;
import com.example.agentzengyu.zy2048.app.ZY2048Application;
import com.example.agentzengyu.zy2048.entity.Record;
import com.example.agentzengyu.zy2048.entity.Score;
import com.example.agentzengyu.zy2048.entity.Square;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ZY2048Service extends Service {
    private ZY2048Application application = null;
    private ArrayList<Square> squares = new ArrayList<>();
    private ArrayList<Record> records = new ArrayList<>();
    private int BEST = 0;
    private int SCORE = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        application = (ZY2048Application) getApplication();
        application.setService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    public class ServiceBinder extends Binder {
    }

    /**
     * 向左
     */
    public void onLeft() {
        Log.e("onLeft", "      --");
        for (int y = 0; y < 4; y++) {
            changeData(y * 4, y * 4 + 1, y * 4 + 2, y * 4 + 3);
        }
        updateGame();
    }

    /**
     * 向右
     */
    public void onRight() {
        Log.e("onRight", "      --");
        for (int y = 0; y < 4; y++) {
            changeData(y * 4 + 3, y * 4 + 2, y * 4 + 1, y * 4);
        }
        updateGame();
    }

    /**
     * 向上
     */
    public void onTop() {
        Log.e("onTop", "      --");
        for (int x = 0; x < 4; x++) {
            changeData(x, 4 * 1 + x, 4 * 2 + x, 4 * 3 + x);
        }
        updateGame();
    }

    /**
     * 向下
     */
    public void onBottom() {
        Log.e("onBottom", "      --");
        for (int x = 0; x < 4; x++) {
            changeData(4 * 3 + x, 4 * 2 + x, 4 * 1 + x, x);
        }
        updateGame();
    }

    /**
     * index4到index1分别为手势滑动方向上按顺序的下标
     *
     * @param index1
     * @param index2
     * @param index3
     * @param index4
     */
    private void changeData(int index1, int index2, int index3, int index4) {
        Log.e("changeData", squares.size()+"");
        int[] indexs = new int[]{index1, index2, index3, index4};
        //清零
        for (int i = 0; i < 3; i++) {
            if (squares.get(indexs[i]).getNumber() == 0) {
                for (int j = i; j < 3; j++) {
                    squares.get(indexs[j]).setNumber(squares.get(indexs[j + 1]).getNumber());
                }
                squares.get(indexs[3]).setNumber(0);
            }
        }
        //叠加
//        for (int i = 0; i < 3; i++) {
//            int score = squares.get(indexs[i]).getNumber();
//            if (score == squares.get(indexs[i + 1]).getNumber()) {
//                squares.get(indexs[i]).setNumber(score * 2);
//                SCORE += score * 2;
//                for (int j = i + 1; j < 3; j++) {
//                    squares.get(indexs[j]).setNumber(squares.get(indexs[j + 1]).getNumber());
//                }
//                squares.get(indexs[3]).setNumber(0);
//            }
//        }
//        //清零
//        for (int i = 0; i < 3; i++) {
//            if (squares.get(indexs[i]).getNumber() == 0) {
//                for (int j = i; j < 3; j++) {
//                    squares.get(indexs[j]).setNumber(squares.get(indexs[j + 1]).getNumber());
//                }
//                squares.get(indexs[3]).setNumber(0);
//            }
//        }
    }

    /**
     * 新游戏
     */
    public void newGame() {
        int[] temps = new int[16];
        for (int i = 0; i < 16; i++) {
            temps[i] = 0;
        }
        for (int i = 0; i < 4; i++) {
            temps[i] = 2;
        }
        for (int i = 0; i < 16; i++) {
            int index = new Random().nextInt(16);
            int temp = temps[i];
            temps[i] = temps[index];
            temps[index] = temp;
        }
        for (int i = 0; i < 16; i++) {
            Square square = new Square();
            square.setNumber(temps[i]);
            squares.add(i, square);
        }
        startGame();
    }

    /**
     * 继续游戏
     */
    public void continueGame() {
        initGame();
        startGame();
    }

    /**
     * 初始化记录
     */
    private void initRecord() {
        JSONArray jsonArray = null;
        String jsonArrayString = "";
        records.clear();
        File fileRecord = new File(getFilesDir(), "record.txt");
        try {
            if (!fileRecord.exists()) {
                fileRecord.createNewFile();
                return;
            }
            FileInputStream inputStreamRecord = new FileInputStream(fileRecord);
            StringBuffer buffer = new StringBuffer();
            byte[] bytesRecord = new byte[1024];
            int read = -1;
            while ((read = inputStreamRecord.read(bytesRecord)) != -1) {
                buffer.append(new String(bytesRecord, 0, read));
            }
            jsonArrayString += buffer.toString();
            inputStreamRecord.close();
            jsonArray = new JSONArray(jsonArrayString);
            if (jsonArray != null) {
                if (jsonArray.length() == 0) {
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Record record = new Gson().fromJson(jsonArray.get(i).toString(), Record.class);
                        records.add(record);
                    }
                    BEST = records.get(0).getScore();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化游戏
     */
    private void initGame() {
        JSONArray jsonArray = null;
        String jsonArrayString = "";
        records.clear();
        File fileGame = new File(getFilesDir(), "game.txt");
        try {
            if (!fileGame.exists()) {
                fileGame.createNewFile();
                return;
            }
            FileInputStream inputStreamGame = new FileInputStream(fileGame);
            StringBuffer buffer = new StringBuffer();
            byte[] bytesGame = new byte[1024];
            int read = -1;
            while ((read = inputStreamGame.read(bytesGame)) != -1) {
                buffer.append(new String(bytesGame, 0, read));
            }
            jsonArrayString += buffer.toString();
            inputStreamGame.close();
            jsonArray = new JSONArray(jsonArrayString);
            if (jsonArray != null) {
                if (jsonArray.length() == 0) {
                    return;
                } else if (jsonArray.length() == 17) {
                    for (int i = 0; i < 16; i++) {
                        Square square = new Gson().fromJson(jsonArray.get(i).toString(), Square.class);
                        squares.add(square);
                    }
                    Score score = new Gson().fromJson(jsonArray.get(16).toString(), Score.class);
                    SCORE = score.getScore();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始游戏
     */
    private void startGame() {
        initRecord();
        Intent intent = new Intent(Config.GAME);
        intent.putExtra(Config.STATE, Config.INITIALIZE);
        intent.putExtra(Config.BEST, BEST);
        intent.putExtra(Config.SCORE, SCORE);
        intent.putExtra(Config.SQUARES, squares);
        sendBroadcast(intent);
    }

    /**
     * 更新游戏
     */
    private void updateGame() {
        Intent intent = new Intent(Config.GAME);
        intent.putExtra(Config.STATE, Config.UPDATE);
        intent.putExtra(Config.SCORE, SCORE);
        intent.putExtra(Config.SQUARES, squares);
        sendBroadcast(intent);
        checkEnd();
    }

    /**
     * 检查游戏是否结束
     */
    private void checkEnd() {

    }

    /**
     * 保存记录
     */
    public void saveRecord() {
        File fileRecord = new File(getFilesDir(), "record.txt");
        try {
            if (!fileRecord.exists())
                fileRecord.createNewFile();
            FileOutputStream outputStreamRecord = new FileOutputStream(fileRecord);
            JSONArray jsonArrayRecord = new JSONArray(records);
            byte[] bytesRecord = jsonArrayRecord.toString().getBytes();
            outputStreamRecord.write(bytesRecord);
            outputStreamRecord.flush();
            outputStreamRecord.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存游戏
     */
    public void saveGame() {
        File fileGame = new File(getFilesDir(), "game.txt");
        try {
            if (!fileGame.exists())
                fileGame.createNewFile();
            FileOutputStream outputStreamGame = new FileOutputStream(fileGame);
            JSONArray jsonArrayGame = new JSONArray(squares);
            Score score = new Score();
            score.setScore(SCORE);
            jsonArrayGame.put(jsonArrayGame.length(), score);
            byte[] bytesGame = jsonArrayGame.toString().getBytes();
            outputStreamGame.write(bytesGame);
            outputStreamGame.flush();
            outputStreamGame.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
