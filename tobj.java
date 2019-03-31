package com.example.mustafa.multitimer;

import android.os.CountDownTimer;

/**
 * Created by mustafa on 12/17/2018.
 */

public class tobj {


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public String getTkey() {
        return tkey;
    }

    public void setTkey(String tkey) {
        this.tkey = tkey;
    }

    public CountDownTimer getTime() {
        return time;
    }

    public void setTime(CountDownTimer time) {
        this.time = time;
    }

    public CountDownTimer time;
    public int start;

    public tobj(String tkey,int start, long left) {
        this.start = start;
        this.left = left;
        this.tkey = tkey;
    }
    public tobj(){}
    public long left;
    public String tkey;

}
