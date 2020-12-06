package com.school.noteapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Task implements Serializable {

    private String name;
    private boolean completed;
    private Date deadline;
    private ArrayList<Task> subtaskList;
    private String priorityColour;
    private int levelFontsize;

    public Task() {

    }

    public Task(String name, boolean completed, String priorityColour, int levelFontsize) {
        this.name = name;
        this.completed = completed;
        this.subtaskList = new ArrayList<>();
        this.priorityColour = priorityColour;
        this.levelFontsize = levelFontsize;
    }

    public Task(String name, boolean completed, Date deadline, String priorityColour, int levelFontsize) {
        this.name = name;
        this.completed = completed;
        this.deadline = deadline;
        this.subtaskList = new ArrayList<>();
        this.priorityColour = priorityColour;
        this.levelFontsize = levelFontsize;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public ArrayList<Task> getSubtaskList() {
        return subtaskList;
    }

    public void setSubtaskList(ArrayList<Task> subtaskList) {
        this.subtaskList = subtaskList;
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

    public boolean addSubtask(Task subtask) {
        if (this.subtaskList == null) {
            this.subtaskList = new ArrayList<>();
        }
        this.subtaskList.add(subtask);
        return true;
    }

    public boolean deleteSubtask(Task subtask) {
        this.subtaskList.remove(subtask);
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass().equals(o.getClass())) {
            Task task = (Task) o;
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String deadlineT = "";
            if (this.getDeadline() != null) {
                deadlineT = format.format(this.getDeadline());
            }
            String deadlineO = "";
            if (task.getDeadline() != null) {
                deadlineO = format.format(task.getDeadline());
            }
            if (this.getName().equals(((Task) o).getName())
                    && this.getPriorityColour().equals(task.getPriorityColour())
                    && this.getLevelFontsize() == task.getLevelFontsize()
                    && (this.getSubtaskList() != null && task.getSubtaskList() != null && this.getSubtaskList().equals(task.getSubtaskList()) || this.getSubtaskList() == null && task.getSubtaskList() == null)
                    && deadlineT.equals(deadlineO)){
                return true;
            }
        }
        return false;
    }
}


