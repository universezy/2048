package com.example.agentzengyu.zy2048.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.agentzengyu.zy2048.app.Config;
import com.example.agentzengyu.zy2048.app.ZY2048Application;
import com.example.agentzengyu.zy2048.entity.Record;
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
    private String BEST = "";

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

    public void newGame() {
        int[] temps = new int[16];
        for(int i=0;i<16;i++){
            temps[i]=0;
        }
        for(int i=0;i<4;i++){
            temps[i]=2;
        }
        for(int i=0;i<16;i++){
            int index = new Random().nextInt(16);
            int temp = temps[i];
            temps[i] = temps[index];
            temps[index] = temp;
        }
        for(int i=0;i<16;i++){
            Square square = new Square();
            square.setNumber(temps[i]);
        }
        startGame();
    }

    public void continueGame() {
        initGame();
        startGame();
    }

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
                    BEST = "" + records.get(0).getScore();
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

    private void initGame(){
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
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Square square = new Gson().fromJson(jsonArray.get(i).toString(), Square.class);
                        squares.add(square);
                    }
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

    private void startGame() {
        initRecord();
        Intent intent = new Intent(Config.GAME);
        intent.putExtra(Config.STATE, Config.INITIALIZE);
        intent.putExtra(Config.BEST, BEST);
        intent.putExtra(Config.SQUARES, squares);
        sendBroadcast(intent);
    }

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

    public void saveGame() {
        File fileGame = new File(getFilesDir(), "game.txt");
        try {
            if (!fileGame.exists())
                fileGame.createNewFile();
            FileOutputStream outputStreamGame = new FileOutputStream(fileGame);
            JSONArray jsonArrayGame = new JSONArray(squares);
            byte[] bytesGame = jsonArrayGame.toString().getBytes();
            outputStreamGame.write(bytesGame);
            outputStreamGame.flush();
            outputStreamGame.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}