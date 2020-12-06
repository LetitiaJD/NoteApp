package com.school.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
        final ListAdapter listAdapter = new ListAdapter(app.getLists(), this);
        // add adapter to recyclerview
        recyclerViewStartLists.setAdapter(listAdapter);

        // load lists into App
        DatabaseReference dataRefList = database.getReference("list");
        dataRefList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List list = snapshot.getValue(List.class);
                if (!app.getLists().contains(list)) {
                    app.getLists().add(list);
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                List list = snapshot.getValue(List.class);
                app.getLists().remove(list);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        list.addTask(new Task("Task 1", false , new Date(), Priority.getHIGH(), Level.getFIRST()));
        list.addTask(new Task("Task 2", false, new Date(), Priority.getMEDIUM(), Level.getSECOND()));
        dataRefList.child(id).setValue(list);

        // second list for test
        id = dataRefList.push().getKey();
        Task task = new Task("Task 1", false, new Date(), Priority.getHIGH(), Level.getFIRST());
        Task subTask1 = new Task( "Subtask 1", true, null, Priority.getMEDIUM(), Level.getSECOND());
        Task subTask2 = new Task( "Subtask 2", false, null, Priority.getHIGH(), Level.getSECOND());
        task.getSubtaskList().add(subTask1);
        task.getSubtaskList().add(subTask2);
        list = new List(id, "List 2", "Test for list", "blue");
        list.addTask(task);
        dataRefList.child(id).setValue(list);
    }

    @Override
    public List saveList(String name, String description, String colour) {

        String id = dataRefList.push().getKey();
        List list = new List(id, name, description, colour);
        dataRefList.child(id).setValue(list);

        dataRefList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List list = snapshot.getValue(List.class);
                app.getLists().add(list);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                List list = snapshot.getValue(List.class);
                app.getLists().remove(list);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
