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

import java.time.LocalDateTime;
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


        // add testdata to db
        addTestdata();




    }

    // for testing
    private void addTestdata() {
        dataRef = database.getReference("list");
        // first list for test
        String id = dataRef.push().getKey();
        List list = new List(id, "List 1", "Test for list", "red");
        list.addTask(new Task(1, "Task 1", false, null, Priority.getHIGH(), Level.getFIRST()));
        list.addTask(new Task(1, "Task 2", false, null, Priority.getMEDIUM(), Level.getSECOND()));
        dataRef.child(id).setValue(list);
        // second list for test
        id = dataRef.push().getKey();
        Task task = new Task(1, "Task 1", false, null, Priority.getHIGH(), Level.getFIRST());
        Task subTask = new Task(1, "Task 2", false, null, Priority.getMEDIUM(), Level.getSECOND());
        task.getSubtasks().add(subTask);
        list = new List(id, "List 1", "Test for list", "blue");
        list.addTask(task);
        dataRef.child(id).setValue(list);

    }
}