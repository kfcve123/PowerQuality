package com.cem.powerqualityanalyser.sqlBean;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class SqlBaseBean extends LitePalSupport {
    public int id;
    public Date time;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
