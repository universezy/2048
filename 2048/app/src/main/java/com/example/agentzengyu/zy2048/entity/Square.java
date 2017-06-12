package com.example.agentzengyu.zy2048.entity;

import android.graphics.Color;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Agent ZengYu on 2017/6/9.
 */

/**
 * 方块实体类
 */
public class Square extends JSONObject implements Serializable {
    private int number = 0;
    private int textColor = Color.parseColor("#000000");
    private int backgroundColor = Color.parseColor("#ffffff");

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getNumber() {
        return number;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
