package com.school.noteapp;

import java.io.Serializable;

public abstract class Priority implements Serializable {

    private static String LOW = "green";
    private static String MEDIUM = "orange";
    private static String HIGH = "red";

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