package com.school.noteapp;

import java.io.Serializable;

public abstract class Level implements Serializable {

    private static int FIRST = 20;
    private static int SECOND = 14;

    public static int getFIRST() {
        return FIRST;
    }

    public static void setFIRST(int FIRST) {
        Level.FIRST = FIRST;
    }

    public static int getSECOND() {
        return SECOND;
    }

    public static void setSECOND(int SECOND) {
        Level.SECOND = SECOND;
    }
}
