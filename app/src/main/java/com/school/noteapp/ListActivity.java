package com.school.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListActivity extends AppCompatActivity {

    TextView textViewListTitle;
    // database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRefList = database.getReference("list");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        List list = (List) getIntent().getSerializableExtra("list");

        // UI
        textViewListTitle = findViewById(R.id.textViewListTitle);

        textViewListTitle.setText(list.toString());

        // button for adding a task has to add th etask to the intent


    }

}