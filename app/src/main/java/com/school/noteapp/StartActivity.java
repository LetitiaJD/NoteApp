package com.school.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity implements CreateListDialog.CreateListDialogListener {

    // database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRefList;

    // UI
    TextView textViewStartTitle;
    FloatingActionButton floatingActionButtonCreateNewList;
    RecyclerView recyclerViewStartLists;
    Button buttonStartTodaysTasks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // initialize UI
        textViewStartTitle = findViewById(R.id.textViewStartTitle);
        floatingActionButtonCreateNewList = findViewById(R.id.floatingActionButtonCreateNewTask);
        recyclerViewStartLists = findViewById(R.id.recyclerViewStartLists);
        buttonStartTodaysTasks = findViewById(R.id.buttonStartTodaysTasks);

        // database
        dataRefList = database.getReference("list");

        // add testdata to db
       // addTestdata();

        // get elements of UI
        floatingActionButtonCreateNewList = findViewById(R.id.floatingActionButtonCreateNewTask);

        // implement button for adding list
        floatingActionButtonCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog for creating a list
                CreateListDialog dialog = new CreateListDialog();
                dialog.show(getSupportFragmentManager(), "createList");
            }
        });




    }

    // for testing
    private void addTestdata() {

        // first list for test
        String id = dataRefList.push().getKey();
        List list = new List(id, "List 1", "Test for list", "red");
        list.addTask(new Task("Task 1", false , Priority.getHIGH(), Level.getFIRST()));
        list.addTask(new Task("Task 2", false, Priority.getMEDIUM(), Level.getSECOND()));
        dataRefList.child(id).setValue(list);
        // second list for test
        id = dataRefList.push().getKey();
        Task task = new Task("Task 1", false, null, Priority.getHIGH(), Level.getFIRST());
        Task subTask = new Task( "Task 2", false, null, Priority.getMEDIUM(), Level.getSECOND());
        task.getSubtasks().add(subTask);
        list = new List(id, "List 1", "Test for list", "blue");
        list.addTask(task);
        dataRefList.child(id).setValue(list);

    }

    @Override
    public List saveList(String name, String description, String colour) {

        String id = dataRefList.push().getKey();
        List list = new List(id, name, description, colour);
        dataRefList.child(id).setValue(list);

        return list;
    }
}