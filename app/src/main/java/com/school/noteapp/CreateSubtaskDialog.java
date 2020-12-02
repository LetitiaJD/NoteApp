package com.school.noteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class CreateSubtaskDialog extends AppCompatDialogFragment {

    CreateSubtaskDialogListener createSubtaskDialogListener;

    // UI
    ImageButton imageButtonSave;
    ImageButton imageButtonDelete;
    EditText editTextSubtaskName;
    CheckBox checkBoxCompleted;
    TextView textViewPriority;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_create_subtask_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_subtask, null);
        builder.setView(view);

        // get elements of UI
        imageButtonSave = view.findViewById(R.id.imageButtonSave);
        imageButtonDelete = view.findViewById(R.id.imageButtonDelete);
        editTextSubtaskName = view.findViewById(R.id.editTextSubtaskName);
        checkBoxCompleted = (CheckBox)view.findViewById(R.id.checkBoxCompleted);
        textViewPriority = view.findViewById(R.id.textViewPriority);

        // implement buttons
        imageButtonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = editTextSubtaskName.getText().toString().trim();
                boolean completed = checkBoxCompleted.isChecked();
                String priorityColour = textViewPriority.getText().toString().trim();

                Task task = CreateSubtaskDialogListener.saveSubtask(name, completed, priorityColour);
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                intent.putExtra("task", task);
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

    public interface CreateSubtaskDialogListener {
        Task saveSubtask(String name, boolean completed, String priorityColour);
    }
}
