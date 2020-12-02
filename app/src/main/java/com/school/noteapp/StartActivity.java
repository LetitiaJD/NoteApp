package com.school.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        // Initialize view for lists
        ListAdapter listAdapter = new ListAdapter(app.getLists(), this);
        // add adapter to recyclerview
        recyclerViewStartLists.setAdapter(listAdapter);
        // Set layout manager to position the items
        recyclerViewStartLists.setLayoutManager(new LinearLayoutManager(this));

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

    @Override
    public void onNoteClick(int position) {
        List list = app.getLists().get(position);
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("list", list);
        startActivity(intent);
    }
}
