package com.school.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.school.noteapp.R;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    // database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRef;

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


        dataRef = database.getReference("list");
        String id = dataRef.push().getKey();
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "Task 1"));
        tasks.add(new Task(2, "Task 2"));
        List list = new List(id, tasks);
        dataRef.setValue(list);

    }
}