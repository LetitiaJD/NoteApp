package com.school.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class StartActivity extends AppCompatActivity implements CreateListDialog.CreateListDialogListener, ListAdapter.OnNoteListener {

    // database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRefList;

    // UI
    TextView textViewStartTitle;
    FloatingActionButton floatingActionButtonCreateNewList;
    RecyclerView recyclerViewStartLists;
    Button buttonStartTodaysTasks;

    App app = App.getInstance();

    private View.OnClickListener listOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // initialize UI
        textViewStartTitle = findViewById(R.id.textViewStartTitle);
        floatingActionButtonCreateNewList = findViewById(R.id.floatingActionButtonCreateNewList);
        recyclerViewStartLists = findViewById(R.id.recyclerViewStartLists);
        buttonStartTodaysTasks = findViewById(R.id.buttonStartTodaysTasks);

        // database
        dataRefList = database.getReference("list");

        // add testdata to db
        addTestdata();

        // implement button for adding list
        floatingActionButtonCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog for creating a list
                CreateListDialog dialog = new CreateListDialog();
                dialog.show(getSupportFragmentManager(), "createList");
            }
        });

        // Initialize view for lists
        ListAdapter listAdapter = new ListAdapter(app.getLists(), this);
        // add adapter to recyclerview
        recyclerViewStartLists.setAdapter(listAdapter);
        // Set layout manager to position the items
        recyclerViewStartLists.setLayoutManager(new LinearLayoutManager(this));
		
		// implement button for open todays tasks
		buttonStartTodaysTasks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open today's tasks
                    Intent intent = new Intent(StartActivity.this, TodaysTasksActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        buttonStartTodaysTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, TodaysTasksActivity.class);
                startActivity(intent);
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
        Task task = new Task("Task 1", false, new Date(), Priority.getHIGH(), Level.getFIRST());
        Task subTask = new Task( "Task 2", false, null, Priority.getMEDIUM(), Level.getSECOND());
        task.getSubtaskList().add(subTask);
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

    @Override
    public void onNoteClick(int position) {
        List list = app.getLists().get(position);
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("list", list);
        startActivity(intent);
    }
}
