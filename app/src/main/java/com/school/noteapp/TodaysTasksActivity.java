package com.school.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TodaysTasksActivity extends AppCompatActivity implements TodaysTasksAdapter.OnNoteListener{

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    // UI
    TextView textViewTodaysTasksTitle;
    RecyclerView recyclerViewTodaysTasksListsWithTasks;
	
    java.util.List<List> listsWithTodaysTasks;


    App app = App.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_tasks);

        // UI
        textViewTodaysTasksTitle = findViewById(R.id.textViewTodaysTasksTitle);
        recyclerViewTodaysTasksListsWithTasks = findViewById(R.id.recyclerViewTodaysTasksListsWithTasks);
		
		
	// get Lists with tasks for today
        try {
            listsWithTodaysTasks = app.getTodaysTasks();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            // need to implement toast

        }

        // Initialize view for list of todays tasks
        final TodaysTasksAdapter todaysTasksAdapter = new TodaysTasksAdapter(listsWithTodaysTasks, this);
        // add adapter to recyclerview
        recyclerViewTodaysTasksListsWithTasks.setAdapter(todaysTasksAdapter);
        // Set layout manager to position the items
        recyclerViewTodaysTasksListsWithTasks.setLayoutManager(new LinearLayoutManager(this));

        /*
        DatabaseReference dataRefList = database.getReference("list");
        dataRefList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List list = snapshot.getValue(List.class);
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    String today = format.format(new Date());
                    ArrayList<Task> tasksForToday = new ArrayList<>();
                    for (Task task : list.getTaskList()) {
                        if (task.getDeadline() != null) {
                            String deadline = format.format(task.getDeadline());
                            if (today.equals(deadline) && !tasksForToday.contains(task)) {
                                tasksForToday.add(task);
                            }
                        }

                        listsWithTodaysTasks.remove(list);
                        list.setTaskList(tasksForToday);
                    listsWithTodaysTasks.add(list);
                    todaysTasksAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                List list = snapshot.getValue(List.class);
                listsWithTodaysTasks.remove(list);
                todaysTasksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }
	
	
    @Override
    public void onNoteClick(int position) {
        List listWithTodaysTasks = listsWithTodaysTasks.get(position);
        // open listActivity
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("list", listWithTodaysTasks);
        startActivity(intent);
	    
    }
}
