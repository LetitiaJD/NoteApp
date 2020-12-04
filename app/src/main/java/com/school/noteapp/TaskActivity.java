package com.school.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskActivity extends AppCompatActivity implements CreateSubtaskDialog.CreateSubtaskDialogListener/*, AdapterView.OnItemSelectedListener*/ {

    ImageButton imageButtonSave;
    ImageButton imageButtonDelete;
    FloatingActionButton floatingActionButtonAddSubtask;
    CheckBox checkBoxCompleted;
    Spinner spinnerPriority;
    EditText editTextTaskTitle;
    EditText editTextDeadline;
    TextView textViewSubtasks;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRefList;

    List selectedList;
    Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        dataRefList = database.getReference("list");

        final List list = (List) getIntent().getSerializableExtra("list");
        final Task task = (Task) getIntent().getSerializableExtra("task");

        selectedList = list;
        selectedTask = task;

        // initialize UI
        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextDeadline = findViewById(R.id.editTextDeadline);
        textViewSubtasks = findViewById(R.id.textViewSubtasks);
        checkBoxCompleted = findViewById(R.id.checkBoxCompleted);
        floatingActionButtonAddSubtask = findViewById(R.id.floatingActionButtonAddSubtask);
        imageButtonSave = findViewById(R.id.imageButtonSave);
        imageButtonDelete = findViewById(R.id.imageButtonDelete);

        /*
        spinnerPriority = (Spinner) findViewById(R.id.spinnerPriority);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.priorityArray, android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerPriority.setAdapter(adapter);

        spinnerPriority.setOnItemSelectedListener(this);

*/


        // Implement buttons
        imageButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TaskActivity.this, "NOT NULL Task has been saved", Toast.LENGTH_LONG).show();
                Toast.makeText(TaskActivity.this, editTextTaskTitle.getText().toString(), Toast.LENGTH_SHORT).show();
                task.setName(editTextTaskTitle.getText().toString());
            }
        });


        // save new Task
        if (selectedTask == null) {
            imageButtonSave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String name = editTextTaskTitle.getText().toString().trim();
                    boolean completed = checkBoxCompleted.isChecked();

                    // need to implement spinner first
                    String priorityColour = Priority.getLOW();
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    Date deadline;
                    try {
                        deadline = format.parse(editTextDeadline.getText().toString().trim());
                    } catch (ParseException e) {
                        // not yet implemented
                        e.printStackTrace();
                        deadline = null;
                    }

                    //Task task = CreateSubtaskDialog.CreateSubtaskDialogListener.saveSubtask(name, completed, priorityColour);
                    Task task = new Task(name, completed, deadline, priorityColour, Level.getFIRST());
                    list.addTask(task);
                    dataRefList.child(list.getId()).setValue(list);

                    Intent intent = new Intent(TaskActivity.this, TaskActivity.class);
                    intent.putExtra("list", list);
                    intent.putExtra("task", task);
                    startActivity(intent);
                }
            });
            // stop creating a new task
            imageButtonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TaskActivity.this, ListActivity.class);
                    intent.putExtra("list", list);
                    startActivity(intent);
                }
            });
            Toast.makeText(TaskActivity.this, "NULL Task has been saved", Toast.LENGTH_LONG);
        } else {

            editTextTaskTitle.setText(task.getName());

            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            editTextDeadline.setText(format.format(task.getDeadline()));

            // task already exists and can be deleted
            imageButtonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteSelectedTask(list, task);
                    // go back to listactivity
                    Intent intent = new Intent(TaskActivity.this, ListActivity.class);
                    intent.putExtra("list", list);
                    startActivity(intent);
                }
            });



        }



        floatingActionButtonAddSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTask == null) {
                    Toast.makeText(TaskActivity.this, "Du musst die Aufgabe zuerst speichern, bevor du Unteraufgaben speichern kannst!", Toast.LENGTH_LONG);
                } else {
                    CreateSubtaskDialog dialog = new CreateSubtaskDialog(list, task);
                    dialog.show(getSupportFragmentManager(), "createSubTask");
                }
            }
        });
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //
    }*/

    // deletes selected task
    public void deleteSelectedTask(List list, Task task) {
        for (Task t : list.getTaskList()) {
            if (t.equals(task)) {
                list.getTaskList().remove(t);
                // update list in firebase
                dataRefList.child(list.getId()).setValue(list);
                break;
            }
        }
    }

    @Override
    public Task saveSubtask(String name, boolean completed, int levelFontsize, String priorityColour) {
        Task subtask = new Task(name, completed, priorityColour, levelFontsize);

        selectedList.deleteTask(selectedTask);

        selectedTask.addSubtask(subtask);
        selectedList.addTask(selectedTask);

        dataRefList.child(selectedList.getId()).setValue(selectedList);

        return subtask;
    }
}