package com.school.noteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class CreateSubtaskDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {

    CreateSubtaskDialogListener createSubtaskDialogListener;

    // UI
    ImageButton imageButtonSave;
    ImageButton imageButtonDelete;
    EditText editTextSubtaskName;
    CheckBox checkBoxCompleted;
    Spinner spinnerPriority;

    Task parentTask;

    public CreateSubtaskDialog(Task task) {
        this.parentTask = parentTask;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_create_subtask_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_subtask, null);
        builder.setView(view);

        // Get elements of UI
        imageButtonSave = view.findViewById(R.id.imageButtonSave);
        imageButtonDelete = view.findViewById(R.id.imageButtonDelete);
        editTextSubtaskName = view.findViewById(R.id.editTextSubtaskName);
        checkBoxCompleted = (CheckBox)view.findViewById(R.id.checkBoxCompleted);
        spinnerPriority = view.findViewById(R.id.spinnerPriority);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.priorityArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerPriority.setAdapter(adapter);

        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                parent.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        // implement buttons
        imageButtonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = editTextSubtaskName.getText().toString().trim();
                boolean completed = checkBoxCompleted.isChecked();
                String priorityColour = Priority.getLOW();
                int levelFontsize = Level.getSECOND();
                //String priorityColour = spinnerPriority.setOnItemSelectedListener(this);

                Task subTask = CreateSubtaskDialogListener.saveSubtask(name, completed, levelFontsize, priorityColour);
                parentTask.addSubtask(subTask);
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
            }
        });

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartActivity.class);
                startActivity(intent);
            }
        });

        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            createSubtaskDialogListener = (CreateSubtaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    public interface CreateSubtaskDialogListener {
        Task saveSubtask(String name, boolean completed, String priorityColour);
    }
}
