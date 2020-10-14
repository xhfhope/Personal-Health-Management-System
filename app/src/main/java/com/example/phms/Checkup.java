package com.example.phms;

public class Checkup {

    private String doctor, date, contents;

    public Checkup() {
    }

    public Checkup(String doctor, String date, String contents) {
        this.doctor = doctor;
        this.date = date;
        this.contents = contents;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}

