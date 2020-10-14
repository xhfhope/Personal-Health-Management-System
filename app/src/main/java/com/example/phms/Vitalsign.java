package com.example.phms;

public class Vitalsign {
    private String dateRecorded, type, value;


    public Vitalsign() {}

    public Vitalsign(String dateRecorded, String type, String value) {
        this.dateRecorded = dateRecorded;
        this.type = type;
        this.value = value;
    }

    public String getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(String dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
