package com.school.noteapp;

import java.util.ArrayList;

public class List {

    String id;
    ArrayList<Task> tasks;

    public List(String id, ArrayList<Task> tasks) {
        this.id = id;
        this.tasks = tasks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
