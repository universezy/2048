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
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * 游戏后台服务,处理游戏数据
 */
public class ZY2048Service extends Service {
    private ZY2048Application application = null;
    private ArrayList<Square> squares = new ArrayList<>();
    private ArrayList<Record> records = new ArrayList<>();
    int[] indexs = new int[]{0, 1, 2, 3, 4, 7, 8, 11, 12, 13, 14, 15};
    private int BEST = 0, LOWEST = 0;
    private int SCORE = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        application = (ZY2048Application) getApplication();
        application.setService(this);
        initRecord();
        initGame();
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
     * 获取排行记录
     *
     * @return
     */
    public ArrayList<Record> getRecords() {
        return this.records;
    }

    /**
     * 获取最高记录
     *
     * @return
     */
    public int getBest() {
        return this.BEST;
    }

    /**
     * 向左
     */
    public void onLeft() {
        boolean equal = false;
        String oldString = "";
        for (int i = 0; i < 16; i++) {
            oldString += squares.get(i).getNumber();
        }
        for (int y = 0; y < 4; y++) {
            changeData(y * 4, y * 4 + 1, y * 4 + 2, y * 4 + 3, true, true);
        }
        String newString = "";
        for (int i = 0; i < 16; i++) {
            newString += squares.get(i).getNumber();
        }
        if (oldString.equals(newString))
            equal = true;
        checkEnd(equal);
    }

    /**
     * 向右
     */
    public void onRight() {
        boolean equal = false;
        String oldString = "";
        for (int i = 0; i < 16; i++) {
            oldString += squares.get(i).getNumber();
        }
        for (int y = 0; y < 4; y++) {
            changeData(y * 4 + 3, y * 4 + 2, y * 4 + 1, y * 4, true, true);
        }
        String newString = "";
        for (int i = 0; i < 16; i++) {
            newString += squares.get(i).getNumber();
        }
        if (oldString.equals(newString))
            equal = true;
        checkEnd(equal);
    }

    /**
     * 向上
     */
    public void onTop() {
        boolean equal = false;
        String oldString = "";
        for (int i = 0; i < 16; i++) {
            oldString += squares.get(i).getNumber();
        }
        for (int x = 0; x < 4; x++) {
            changeData(x, 4 + x, 4 * 2 + x, 4 * 3 + x, true, true);
        }
        String newString = "";
        for (int i = 0; i < 16; i++) {
            newString += squares.get(i).getNumber();
        }
        if (oldString.equals(newString))
            equal = true;
        checkEnd(equal);
    }

    /**
     * 向下
     */
    public void onBottom() {
        boolean equal = false;
        String oldString = "";
        for (int i = 0; i < 16; i++) {
            oldString += squares.get(i).getNumber();
        }
        for (int x = 0; x < 4; x++) {
            changeData(4 * 3 + x, 4 * 2 + x, 4 + x, x, true, true);
        }
        String newString = "";
        for (int i = 0; i < 16; i++) {
            newString += squares.get(i).getNumber();
        }
        if (oldString.equals(newString))
            equal = true;
        checkEnd(equal);
    }

    /**
     * 改变单行或单列的四个数据
     * index4到index1分别为手势滑动方向上按顺序的下标
     *
     * @param index1
     * @param index2
     * @param index3
     * @param index4
     * @param clearZero 清零
     * @param addNumber 叠加
     */
    private void changeData(int index1, int index2, int index3, int index4, boolean clearZero, boolean addNumber) {
        int[] indexs = new int[]{index1, index2, index3, index4};
        //清零,进行多次直到无清零操作
        if (clearZero) {
            clearZero = false;
            if (squares.get(indexs[0]).getNumber() == 0 && squares.get(indexs[1]).getNumber() == 0 && squares.get(indexs[2]).getNumber() == 0 && squares.get(indexs[3]).getNumber() == 0) {
            } else if (squares.get(indexs[0]).getNumber() != 0 && squares.get(indexs[1]).getNumber() == 0 && squares.get(indexs[2]).getNumber() == 0 && squares.get(indexs[3]).getNumber() == 0) {
            } else if (squares.get(indexs[0]).getNumber() != 0 && squares.get(indexs[1]).getNumber() != 0 && squares.get(indexs[2]).getNumber() == 0 && squares.get(indexs[3]).getNumber() == 0) {
            } else if (squares.get(indexs[0]).getNumber() != 0 && squares.get(indexs[1]).getNumber() != 0 && squares.get(indexs[2]).getNumber() != 0 && squares.get(indexs[3]).getNumber() == 0) {
            } else if (squares.get(indexs[0]).getNumber() != 0 && squares.get(indexs[1]).getNumber() != 0 && squares.get(indexs[2]).getNumber() != 0 && squares.get(indexs[3]).getNumber() != 0) {
            } else {
                for (int i = 0; i < 3; i++) {
                    if (squares.get(indexs[i]).getNumber() == 0) {
                        clearZero = true;
                        for (int j = i; j < 3; j++) {
                            squares.get(indexs[j]).setNumber(squares.get(indexs[j + 1]).getNumber());
                        }
                        squares.get(indexs[3]).setNumber(0);
                    }
                }
            }
        }
        //叠加,只进行一次
        if (addNumber) {
            for (int i = 0; i < 3; i++) {
                int score = squares.get(indexs[i]).getNumber();
                if (score == squares.get(indexs[i + 1]).getNumber()) {
                    squares.get(indexs[i]).setNumber(score * 2);
                    SCORE += score * 2;
                    for (int j = i + 1; j < 3; j++) {
                        squares.get(indexs[j]).setNumber(squares.get(indexs[j + 1]).getNumber());
                    }
                    squares.get(indexs[3]).setNumber(0);
                }
            }
        }
        if (!clearZero && !addNumber) return;
        changeData(index1, index2, index3, index4, clearZero, false);
    }

    /**
     * 新游戏
     */
    public void newGame() {
        squares.clear();
        SCORE = 0;
        int[] temps = new int[16];
        for (int i = 0; i < 16; i++) {
            temps[i] = 0;
        }
        for (int i = 0; i < 4; i++) {
            temps[indexs[i]] = 2;
        }
        for (int i = 0; i < 12; i++) {
            int index = new Random().nextInt(12);
            int temp = temps[indexs[i]];
            temps[indexs[i]] = temps[indexs[index]];
            temps[indexs[index]] = temp;
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
        Log.e("initRecord", "----------");
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
            if (jsonArrayString == null || "".equals(jsonArrayString) || "[null]".equals(jsonArrayString))
                return;
            inputStreamRecord.close();
            jsonArray = new JSONArray(jsonArrayString);
            if (jsonArray != null) {
                if (jsonArray.length() == 0) {
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Record record = new Gson().fromJson(jsonArray.get(i).toString(), Record.class);
                        records.add(i, record);
                    }
                    BEST = records.get(0).getScore();
                    LOWEST = records.get(records.size() - 1).getScore();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        for (Record record : records) {
            Log.e("getName", record.getName());
            Log.e("getTime", record.getTime());
            Log.e("getRank", "" + record.getRank());
            Log.e("getScore", "" + record.getScore());
        }
        Log.e("BEST", "" + BEST);
        Log.e("LOWEST", "" + LOWEST);
    }

    /**
     * 初始化游戏
     */
    private void initGame() {
        Log.e("initGame", "----------");
        JSONArray jsonArray = null;
        String jsonArrayString = "";
        squares.clear();
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
            if (jsonArray != null && jsonArray.length() == 17) {
                if (jsonArray.length() == 17) {
                    for (int i = 0; i < 16; i++) {
                        Square square = new Gson().fromJson(jsonArray.get(i).toString(), Square.class);
                        squares.add(i, square);
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
        for (Square square : squares) {
            Log.e("getNumber", "" + square.getNumber());
        }
        Log.e("SCORE", "" + SCORE);
    }

    /**
     * 开始游戏
     */
    private void startGame() {
//        initRecord();
        Log.e("startGame", "----------");
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
        Log.e("updateGame", "----------");
        Intent intent = new Intent(Config.GAME);
        intent.putExtra(Config.STATE, Config.UPDATE);
        intent.putExtra(Config.SCORE, SCORE);
        intent.putExtra(Config.SQUARES, squares);
        sendBroadcast(intent);
    }

    /**
     * 检查游戏是否结束
     */
    private void checkEnd(boolean equal) {
        if (equal) return;
        boolean end = true, sameNumber = false;
        for (int i = 0; i < 12; i++) {
            int index = new Random().nextInt(12);
            int temp = indexs[i];
            indexs[i] = indexs[index];
            indexs[index] = temp;
        }
        for (int index : indexs) {
            if (squares.get(index).getNumber() == 0) {
                squares.get(index).setNumber(2);
                break;
            }
        }
        updateGame();
        for (int y = 0; y < 4; y++) {
            if (!sameNumber) {
                for (int x = 0; x < 3; x++) {
                    if (squares.get(4 * y + x).getNumber() == squares.get(4 * y + x + 1).getNumber()) {
                        end = false;
                        sameNumber = true;
                        break;
                    }
                }
            } else
                break;
        }
        for (int x = 0; x < 4; x++) {
            if (!sameNumber) {
                for (int y = 0; y < 3; y++) {
                    if (squares.get(4 * y + x).getNumber() == squares.get(4 * y + 4 + x).getNumber()) {
                        end = false;
                        sameNumber = true;
                    }
                }
            } else
                break;
        }
        if (end) {
            Intent intent = new Intent(Config.GAME);
            if (SCORE > LOWEST || records.size() < 10) {
                intent.putExtra(Config.STATE, Config.NEWRECORD);
            } else {
                intent.putExtra(Config.STATE, Config.GAMEOVER);
            }
            sendBroadcast(intent);
        }
    }

    /**
     * 清除历史游戏记录
     */
    public void clearGame() {
        squares.clear();
        SCORE = 0;
        saveGame();
    }

    /**
     * 更新记录
     *
     * @param name 玩家名
     */
    public void updateRecord(String name) {
        Log.e("updateRecord", "----------");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = dateFormat.format(date);
        Record record = new Record();
        record.setName(name);
        record.setScore(SCORE);
        record.setTime(time);
        if (records.size() == 0) {
            record.setRank(1);
            records.add(0, record);
            saveRecord();
        } else {
            int index = 0;
            for (int i = 0; i < records.size(); i++) {
                if (SCORE > records.get(i).getScore()) {
                    index = i;
                    record.setRank(i + 1);
                    records.add(i, record);
                    break;
                }
            }
            for (int j = index; j < records.size(); j++) {
                records.get(j).setRank(j + 1);
            }
            if (records.size() > 10) {
                records.remove(10);
            }
            saveRecord();
        }
    }

    /**
     * 保存记录
     */
    public void saveRecord() {
        Log.e("saveRecord", "----------");
        File fileRecord = new File(getFilesDir(), "record.txt");
        try {
            if (!fileRecord.exists())
                fileRecord.createNewFile();
            FileOutputStream outputStreamRecord = new FileOutputStream(fileRecord);
            JSONArray jsonArrayRecord = new JSONArray();
            for (Record record : records) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", record.getName());
                jsonObject.put("score", record.getScore());
                jsonObject.put("time", record.getTime());
                jsonObject.put("rank", record.getRank());
                jsonArrayRecord.put(jsonObject);
            }
            byte[] bytesRecord = jsonArrayRecord.toString().getBytes();
            outputStreamRecord.write(bytesRecord);
            outputStreamRecord.flush();
            outputStreamRecord.close();
            Log.e("saveRecord", "successful");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存游戏
     */
    public void saveGame() {
        Log.e("saveGame", "----------");
        File fileGame = new File(getFilesDir(), "game.txt");
        try {
            if (!fileGame.exists())
                fileGame.createNewFile();
            FileOutputStream outputStreamGame = new FileOutputStream(fileGame);
            JSONArray jsonArrayGame = new JSONArray();
            for (Square square : squares) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("number", square.getNumber());
                jsonObject.put("textColor", square.getTextColor());
                jsonObject.put("backgroundColor", square.getBackgroundColor());
                jsonArrayGame.put(jsonArrayGame.length(), jsonObject);
            }
            Score score = new Score();
            score.setScore(SCORE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("score", score.getScore());
            jsonArrayGame.put(jsonArrayGame.length(), jsonObject);
            byte[] bytesGame = jsonArrayGame.toString().getBytes();
            outputStreamGame.write(bytesGame);
            outputStreamGame.flush();
            outputStreamGame.close();
            Log.e("saveGame", "successful");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
