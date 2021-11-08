package com.cem.powerqualityanalyser.meterobject;

public class RightListViewItemObj {
    private String content;
    private int srcRes;
    private int textSize = -1;

    public RightListViewItemObj() {

    }

    public RightListViewItemObj(String content, int srcRes) {
        this.content = content;
        this.srcRes = srcRes;
    }

    public RightListViewItemObj(String content,int srcRes,int textSize){
        this.content = content;
        this.srcRes = srcRes;
        this.textSize = textSize;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSrcRes() {
        return srcRes;
    }

    public void setSrcRes(int srcRes) {
        this.srcRes = srcRes;
    }
}
