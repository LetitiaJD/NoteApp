package com.school.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListActivity extends AppCompatActivity {

    TextView textViewListTitle;
    FloatingActionButton floatingActionButtonCreateNewTask;
    // database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRefList = database.getReference("list");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final List list = (List) getIntent().getSerializableExtra("list");

        // UI
        textViewListTitle = findViewById(R.id.textViewListTitle);
        floatingActionButtonCreateNewTask = findViewById(R.id.floatingActionButtonCreateNewTask);

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


    }

}