package com.school.noteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateSubtaskDialog extends AppCompatDialogFragment /*implements AdapterView.OnItemSelectedListener*/ {

    CreateSubtaskDialogListener createSubtaskDialogListener;

    // UI
    ImageButton imageButtonSave;
    ImageButton imageButtonDelete;
    EditText editTextSubtaskName;
    CheckBox checkBoxCompleted;
    Spinner spinnerPriority;

    List parentList;
    Task parentTask;

    public CreateSubtaskDialog(List parentList, Task parentTask) {
        this.parentList = parentList;
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

        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.priorityArray, android.R.layout.simple_spinner_item);
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
        });*/

        spinnerPriority = (Spinner) view.findViewById(R.id.spinnerPriority);

        String[] priorities = new String[]{
                "Priorität wählen",
                Priority.getLOW(),
                Priority.getMEDIUM(),
                Priority.getHIGH()
        };

        final ArrayList<String> priorityList = new ArrayList<>(Arrays.asList(priorities));

        // Initialising the ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_priority, priorityList) {
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
                    Toast.makeText(getContext(), "Selected: " + selectedItemText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // implement buttons
        imageButtonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = editTextSubtaskName.getText().toString().trim();
                boolean completed = checkBoxCompleted.isChecked();
                int levelFontsize = Level.getSECOND();
                String priority = Priority.getLOW();

                if (spinnerPriority.getSelectedItemPosition() > 0) {
                    priority = spinnerPriority.getSelectedItem().toString();
                }

                Task subTask = createSubtaskDialogListener.saveSubtask(name, completed, levelFontsize, priority);
                parentTask.addSubtask(subTask);
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                intent.putExtra("list", parentList);
                intent.putExtra("task", parentTask);
                Toast.makeText(getContext(), "Unteraufgabe wurde gespeichert", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartActivity.class);
                Toast.makeText(getContext(), "Unteraufgabe wurde gelöscht", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        return builder.create();
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
        Task saveSubtask(String name, boolean completed, int levelFontsize, String priority);
    }
}
