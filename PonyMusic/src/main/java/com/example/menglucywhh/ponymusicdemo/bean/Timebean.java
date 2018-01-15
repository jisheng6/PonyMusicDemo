package com.example.menglucywhh.ponymusicdemo.bean;

/**
 * Created by 0.0 on 2018/1/2.
 */

public class Timebean {

    public int currentPosition;
    public int duration;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Timebean(int currentPosition, int duration) {
        this.currentPosition = currentPosition;
        this.duration = duration;
    }
}
