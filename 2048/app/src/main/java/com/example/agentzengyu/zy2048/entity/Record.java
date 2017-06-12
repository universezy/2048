package com.example.agentzengyu.zy2048.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Agent ZengYu on 2017/6/9.
 */

/**
 * 排名记录实体类
 */
public class Record extends JSONObject implements Serializable {
    private String name = "";
    private int score = 0;
    private String time = "";
    private int rank = 0;

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getRank() {
        return rank;
    }
}
