package com.cem.powerqualityanalyser.databean;

import java.util.Date;

public class FileBean implements Comparable<FileBean>{
    public static final String TYPE_CSV = "csv";
    public static final String TYPE_PIC = "pic";

    private Date startTime;
    private Date endTime;
    private String fileName;
    private String type;
    private Boolean isChecked = false;
    private String imagePath;
    public FileBean(Date startTime, Date endTime, String fileName, Boolean isChecked) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.fileName = fileName;
        this.isChecked = isChecked;
    }

    public FileBean() {
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int compareTo(FileBean o) {
        int i = (int) (o.getStartTime().getTime() - this.startTime.getTime());//最新的时间放最前面
        return i;
    }
}
