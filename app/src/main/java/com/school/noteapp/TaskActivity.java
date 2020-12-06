package com.school.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class TaskActivity extends AppCompatActivity implements CreateSubtaskDialog.CreateSubtaskDialogListener, TaskAdapter.ItemClickListener {

    ImageButton imageButtonSave;
    ImageButton imageButtonDelete;
    FloatingActionButton floatingActionButtonAddSubtask;
    CheckBox checkBoxCompleted;
    EditText editTextTaskTitle;
    EditText editTextDeadline;
    TextView textViewSubtasks;
    Spinner spinnerPriority;
    RecyclerView recyclerViewSubtasks;
    TaskAdapter taskAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRefList;

    List selectedList;
    Task selectedTask;

    App app = App.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        dataRefList = database.getReference("list");

        final List list = (List) getIntent().getSerializableExtra("list");
        final Task task = (Task) getIntent().getSerializableExtra("task");

        selectedList = list;
        selectedTask = task;

        // Initialize UI
        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextDeadline = findViewById(R.id.editTextDeadline);
        textViewSubtasks = findViewById(R.id.textViewSubtasks);
        checkBoxCompleted = findViewById(R.id.checkBoxCompleted);
        floatingActionButtonAddSubtask = findViewById(R.id.floatingActionButtonAddSubtask);
        imageButtonSave = findViewById(R.id.imageButtonSave);
        imageButtonDelete = findViewById(R.id.imageButtonDelete);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        recyclerViewSubtasks = findViewById(R.id.recyclerViewSubtasks);

        final String[] priorities = new String[]{
                "Priorität wählen",
                Priority.getLOW(),
                Priority.getMEDIUM(),
                Priority.getHIGH()
        };

        final ArrayList<String> priorityList = new ArrayList<>(Arrays.asList(priorities));

        // Initialising the ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_priority, priorityList) {
            @Override
            public boolean isEnabled(int position) {
                // Disable first item in the spinner, as it's only a hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;

                if (position == 0) {
                    textView.setTextColor(Color.parseColor("#999999"));
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_priority);
        spinnerPriority.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();

        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    Toast.makeText(getApplicationContext(), "Selected: " + selectedItemText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Implement RecyclerView for Subtasks
        recyclerViewSubtasks.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(this, app.getSubtasks(task));
        taskAdapter.setItemClickListener(this);
        recyclerViewSubtasks.setAdapter(taskAdapter);

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

                    // Low is default, if user does not select a priority
                    String priorityColour = Priority.getLOW();
                    //String priorityColour = spinnerPriority.getSelectedItem().toString();
                    if (spinnerPriority.getSelectedItemPosition() > 0) {
                        priorityColour = spinnerPriority.getSelectedItem().toString();
                    }

                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    Date deadline;
                    try {
                        deadline = format.parse(editTextDeadline.getText().toString().trim());
                    } catch (ParseException e) {
                        // not yet implemented
                        e.printStackTrace();
                        deadline = null;
                    }

                    Task task = new Task(name, completed, deadline, priorityColour, Level.getFIRST());
                    list.addTask(task);
                    dataRefList.child(list.getId()).setValue(list);

                    Intent intent = new Intent(TaskActivity.this, TaskActivity.class);
                    intent.putExtra("list", list);
                    intent.putExtra("task", task);
                    startActivity(intent);
                }
            });
            // Stop creating a new task
            imageButtonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TaskActivity.this, ListActivity.class);
                    intent.putExtra("list", list);
                    startActivity(intent);
                }
            });
            Toast.makeText(TaskActivity.this, "NULL Task has been saved", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(TaskActivity.this, "Du musst die Aufgabe zuerst speichern, bevor du Unteraufgaben speichern kannst!", Toast.LENGTH_LONG).show();
                } else {
                    CreateSubtaskDialog dialog = new CreateSubtaskDialog(list, task, "new");
                    dialog.show(getSupportFragmentManager(), "createSubTask");
                }
            }
        });
    }

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
    public Task saveSubtask(String name, boolean completed, int levelFontsize, String priority) {
        Task subtask = new Task(name, completed, priority, levelFontsize);

        selectedList.deleteTask(selectedTask);

        selectedTask.addSubtask(subtask);
        selectedList.addTask(selectedTask);

        dataRefList.child(selectedList.getId()).setValue(selectedList);

        return subtask;
    }

    @Override
    public void onItemClick(View view, int position) {
        Task subtask = taskAdapter.getItem(position);
        Bundle args = new Bundle();
        args.putSerializable("subtask", subtask);

        CreateSubtaskDialog subtaskDialog = new CreateSubtaskDialog(selectedList, selectedTask, "edit");
        subtaskDialog.setArguments(args);
        subtaskDialog.show(getSupportFragmentManager(), "dialog");
    }
}