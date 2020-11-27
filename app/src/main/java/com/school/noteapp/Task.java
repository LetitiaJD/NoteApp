package com.school.noteapp;

import java.time.LocalDateTime;

public class Task {

    private int id;
    private String name;
    private boolean completed;
    private LocalDateTime deadline;
    private Task parentTask;
    private Priority priority;
    private Level level;

    public Task(int id, String name, boolean completed, LocalDateTime deadline, Task parentTask, Priority priority, Level level) {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.deadline = deadline;
        this.parentTask = parentTask;
        this.priority = priority;
        this.level = level;
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

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}


