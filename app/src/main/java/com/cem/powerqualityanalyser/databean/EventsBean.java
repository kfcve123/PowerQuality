package com.cem.powerqualityanalyser.databean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventsBean {
    Date startTime;
    Date endTime;
    String type;
    String line;
    private SimpleDateFormat simpleDateFormat;
    public EventsBean(Date startTime, Date endTime, String type, String line) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.line = line;
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:ss:mm");
    }

    public EventsBean(){}

    public int getDurataion(){
       return  (int) ((endTime.getTime() - startTime.getTime()) / 1000);
    }

    public String getStartTimeStr(){
       return simpleDateFormat.format(startTime);
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }
    public EventsBean copySelf(){
        return new EventsBean(this.startTime,this.endTime,this.type,this.line);
    }

    public EventsBean clearself(){
        this.startTime = null;
        this.endTime = null;
        this.line = null;
        this.type = null;
        return this;
    }

}
