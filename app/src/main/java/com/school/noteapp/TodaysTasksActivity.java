package com.school.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class TodaysTasksActivity extends AppCompatActivity implements TodaysTasksAdapter.OnNoteListener{


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
        TodaysTasksAdapter todaysTasksAdapter = new TodaysTasksAdapter(listsWithTodaysTasks, this);
        // add adapter to recyclerview
        recyclerViewTodaysTasksListsWithTasks.setAdapter(todaysTasksAdapter);
        // Set layout manager to position the items
        recyclerViewTodaysTasksListsWithTasks.setLayoutManager(new LinearLayoutManager(this));
    }
	
	
    @Override
    public void onNoteClick(int position) {
        List listWithTodaysTasks = listsWithTodaysTasks.get(position);
        // make recyclerview with tasks visible
	    
	    
    }
}
