package com.school.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class TodaysTasksActivity extends AppCompatActivity {


    // UI
    TextView textViewTodaysTaskTitle;
    RecyclerView recyclerViewTodaysTasksListsWithTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_tasks);

        // UI
        textViewTodaysTaskTitle = findViewById(R.id.textViewTodaysTasksTitle);
        recyclerViewTodaysTasksListsWithTasks = findViewById(R.id.recyclerViewTodaysTasksListsWithTasks);
    }
}