package com.example.agentzengyu.zy2048.entity;

import android.graphics.Color;

/**
 * Created by Agent ZengYu on 2017/6/9.
 */

public class Square {
    private int number = 2;
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
