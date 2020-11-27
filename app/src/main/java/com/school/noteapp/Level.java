package com.school.noteapp;

public class Level {

    private int id;
    private int fontSize;

    public Level(int id, int fontSize) {
        this.id = id;
        this.fontSize = fontSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
