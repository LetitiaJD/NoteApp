package com.school.noteapp;

import java.io.Serializable;

public abstract class Priority implements Serializable {

    private static String LOW = "unwichtig";
    private static String MEDIUM = "wichtig";
    private static String HIGH = "dringend";

    public static String getLOW() {
        return LOW;
    }

    public static void setLOW(String LOW) {
        Priority.LOW = LOW;
    }

    public static String getMEDIUM() {
        return MEDIUM;
    }

    public static void setMEDIUM(String MEDIUM) {
        Priority.MEDIUM = MEDIUM;
    }

    public static String getHIGH() {
        return HIGH;
    }

    public static void setHIGH(String HIGH) {
        Priority.HIGH = HIGH;
    }
}