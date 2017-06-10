package com.example.agentzengyu.zy2048.entity;

import java.io.Serializable;

/**
 * Created by Agent ZengYu on 2017/6/10.
 */

public class Score implements Serializable {
    private int score = 0;

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
