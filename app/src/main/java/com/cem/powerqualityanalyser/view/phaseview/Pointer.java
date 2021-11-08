package com.cem.powerqualityanalyser.view.phaseview;

public class Pointer {
    private float degrees;
    private float percentage;
    private int color;
    public float getDegrees(){
        return  degrees;
    }
    public Pointer(float degrees,float percentage,int color){
        this.degrees=degrees;
        this.percentage=percentage;
        this.color=color;
    }
    public Pointer(float degrees,float percentage){
        this.degrees=degrees;
        this.percentage=percentage;
    }
    public float getPercentage() {
        return percentage;
    }

    public int getColor() {
        return color;
    }
}
