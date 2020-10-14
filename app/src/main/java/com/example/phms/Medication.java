package com.example.phms;

public class Medication {

    private String dosage, frequency, name;

    public Medication() {
    }

    public Medication(String dosage, String frequency, String name) {
        this.dosage = dosage;
        this.frequency = frequency;
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

