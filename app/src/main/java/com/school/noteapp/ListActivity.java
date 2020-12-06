package com.school.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener {

    TextView textViewListTitle;
    FloatingActionButton floatingActionButtonCreateNewTask;
    RecyclerView recyclerViewTasks;
    TaskAdapter taskAdapter;

    // database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRefList = database.getReference("list");

    App app = App.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final List list = (List) getIntent().getSerializableExtra("list");

        // UI
        textViewListTitle = findViewById(R.id.textViewListTitle);
        floatingActionButtonCreateNewTask = findViewById(R.id.floatingActionButtonCreateNewTask);
        recyclerViewTasks = findViewById(R.id.recyclerViewList);
        textViewListTitle.setText(list.toString());

        // button for adding a task
        floatingActionButtonCreateNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, TaskActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
            }
        });

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(this, list, app.getTasks(list));
        taskAdapter.setItemClickListener(this);
        recyclerViewTasks.setAdapter(taskAdapter);
    }

    @Override
    public void onItemClick(View view, List list, int position) {
        /*Task task = app.getTasks().get(position);
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra("task", task);
        taskActivity(intent);*/
/*        String text = recyclerViewTasks.getChildAt(position).toString();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
        //Toast.makeText(this, "You clicked " + taskAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        Task task = taskAdapter.getItem(position);
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra("list", list);
        intent.putExtra("task", task);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListActivity.this, StartActivity.class);
        startActivity(intent);
    }
}