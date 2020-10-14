package com.example.phms;

import java.util.Date;

public class Doctor {

    private String name, date, checkupInfo;

    public Doctor() {}

    public Doctor(String name, String date, String password, String checkInfo) {
        this.name = name;
        this.date = date;
        this.checkupInfo = checkInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckInfo() {
        return checkupInfo;
    }

    public void setCheckInfo(String CheckupInfo) {
        this.checkupInfo = CheckupInfo;
    }
}
