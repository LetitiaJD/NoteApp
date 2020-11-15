package com.school.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StartActivity extends AppCompatActivity {


    // UI
    TextView textViewStartTitle;
    FloatingActionButton floatingActionButtonStartAddList;
    RecyclerView recyclerViewStartLists;
    Button buttonStartTodaysTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // initialize UI
        textViewStartTitle = findViewById(R.id.textViewStartTitle);
        floatingActionButtonStartAddList = findViewById(R.id.floatingActionButtonStartAddList);
        recyclerViewStartLists = findViewById(R.id.recyclerViewStartLists);
        buttonStartTodaysTasks = findViewById(R.id.buttonStartTodaysTasks);


    }
}