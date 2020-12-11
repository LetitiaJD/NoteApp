package com.school.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    Task selectedSubtask;

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

        // initialising the ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_priority, priorityList) {
            @Override
            public boolean isEnabled(int position) {
                // disable first item in the spinner, as it's only a hint
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


        int priority = 0;

        // fill fields if an existing task is selected
        if (selectedTask != null) {
            editTextTaskTitle.setText(selectedTask.getName());

            if (selectedTask.getDeadline() != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                editTextDeadline.setText(format.format(selectedTask.getDeadline()));
            }

            if (selectedTask.isCompleted()) {
                checkBoxCompleted.setChecked(true);
            }

            if (selectedTask.getPriorityColour().equals(Priority.getLOW())) {
                priority = 1;
            } else if (selectedTask.getPriorityColour().equals(Priority.getMEDIUM())) {
                priority = 2;
            } else if (selectedTask.getPriorityColour().equals(Priority.getHIGH())) {
                priority = 3;
            }

        }

        spinnerPriority.setSelection(priority);
        spinnerArrayAdapter.notifyDataSetChanged();


        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    //Toast.makeText(getApplicationContext(), "Selected: " + selectedItemText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // implement RecyclerView for Subtasks
        recyclerViewSubtasks.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(this, list, app.getSubtasks(task));
        taskAdapter.setItemClickListener(this);
        recyclerViewSubtasks.setAdapter(taskAdapter);

        // save Task
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
                    if (selectedTask != null) {
                        task.setSubtaskList(selectedTask.getSubtaskList());
                        list.deleteTask(selectedTask);
                    }
                    list.addTask(task);
                    dataRefList.child(list.getId()).setValue(list);
                    dataRefList.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            List list = snapshot.getValue(List.class);
                            app.getLists().remove(list);
                            app.getLists().add(list);
                            taskAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            List list = snapshot.getValue(List.class);
                            app.getLists().remove(list);
                            app.getLists().add(list);
                            taskAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                            List list = snapshot.getValue(List.class);
                            app.getLists().remove(list);
                            taskAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Intent intent = new Intent(TaskActivity.this, TaskActivity.class);
                    intent.putExtra("list", list);
                    intent.putExtra("task", task);
                    Toast.makeText(getApplicationContext(), "Aufgabe wurde gespeichert", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });


            imageButtonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (selectedTask != null) {
                        deleteSelectedTask(list, task);
                    }
                    // go back to ListActivity
                    Intent intent = new Intent(TaskActivity.this, ListActivity.class);
                    intent.putExtra("list", list);
                    Toast.makeText(getApplicationContext(), "Aufgabe wurde gelöscht", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });

        floatingActionButtonAddSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTask == null) {
                    Toast.makeText(TaskActivity.this, "Du musst die Aufgabe zuerst speichern, bevor du Unteraufgaben speichern kannst!", Toast.LENGTH_LONG).show();
                } else {
                    CreateSubtaskDialog dialog = new CreateSubtaskDialog(list, task, selectedSubtask,"new");
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
    public Task saveSubtaskInDatabase(Task subtaskOld, String name, boolean completed, int levelFontsize, String priority) {
        Task subtask = new Task(name, completed, priority, levelFontsize);

        selectedList.deleteTask(selectedTask);

        if (subtaskOld != null) {
            selectedTask.deleteSubtask(subtaskOld);
        }
        selectedTask.addSubtask(subtask);

        selectedList.addTask(selectedTask);

        dataRefList.child(selectedList.getId()).setValue(selectedList);

        dataRefList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List list = snapshot.getValue(List.class);
                selectedList = list;
                    taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List list = snapshot.getValue(List.class);
                selectedList = list;
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return subtask;
    }

    @Override
    public void deleteSubtaskInDatabase(Task subtask) {

        selectedList.deleteTask(selectedTask);
        selectedTask.deleteSubtask(subtask);
        selectedList.addTask(selectedTask);

        dataRefList.child(selectedList.getId()).setValue(selectedList);

        dataRefList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List list = snapshot.getValue(List.class);
                selectedList = list;
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List list = snapshot.getValue(List.class);
                selectedList = list;
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(View view, List list, int position) {
        Task subtask = taskAdapter.getItem(position);
        selectedSubtask = subtask;
        Bundle args = new Bundle();
        args.putSerializable("subtask", subtask);

        CreateSubtaskDialog subtaskDialog = new CreateSubtaskDialog(selectedList, selectedTask, selectedSubtask, "edit");
        subtaskDialog.setArguments(args);
        subtaskDialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TaskActivity.this, ListActivity.class);
        intent.putExtra("list", selectedList);
        startActivity(intent);
    }
}