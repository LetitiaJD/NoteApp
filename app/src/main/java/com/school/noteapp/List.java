package com.school.noteapp;

import java.io.Serializable;
import java.util.ArrayList;

public class List implements Serializable {

    private String id;
    private String name;
    private String description;
    private String colour;
    private ArrayList<Task> taskList;

    public List(String id, String name, String description, String colour) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.colour = colour;
        this.taskList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public boolean addTask(Task task) {
        this.taskList.add(task);
        return true;
    }

    public boolean deleteTask(Task task) {
        this.taskList.remove(task);
        return true;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
