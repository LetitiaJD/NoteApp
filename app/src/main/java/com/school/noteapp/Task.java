package com.school.noteapp;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task {

    private int id;
    private String name;
    private boolean completed;
    private LocalDateTime deadline;
    private ArrayList<Task> subtasks;
    private String priorityColour;
    private int levelFontsize;

    public Task(int id, String name, boolean completed, LocalDateTime deadline, String priorityColour, int levelFontsize) {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.deadline = deadline;
        this.subtasks = new ArrayList<>();
        this.priorityColour = priorityColour;
        this.levelFontsize = levelFontsize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public ArrayList<Task> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Task> subtasks) {
        this.subtasks = subtasks;
    }

    public String getPriorityColour() {
        return priorityColour;
    }

    public void setPriorityColour(String priorityColour) {
        this.priorityColour = priorityColour;
    }

    public int getLevelFontsize() {
        return levelFontsize;
    }

    public void setLevelFontsize(int levelFontsize) {
        this.levelFontsize = levelFontsize;
    }
}


