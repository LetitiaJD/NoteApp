package com.school.noteapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateListDialog extends AppCompatDialogFragment {

    CreateListDialogListener createListDialogListener;

    // UI
    ImageButton imageButtonSave;
    ImageButton imageButtonDelete;
    EditText editTextListName;
    EditText editTextListDescription;
    EditText editTextListColour;
    Button buttonRed;
    Button buttonGreen;
    Button buttonBlue;
    Button buttonPink;
    Button buttonYellow;
    Button buttonOrange;

    java.util.List<Button> buttons;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_create_list_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_list, null);
        builder.setView(view);

        // get elements of UI
        imageButtonSave = view.findViewById(R.id.imageButtonSave);
        imageButtonDelete = view.findViewById(R.id.imageButtonDelete);
        editTextListName = view.findViewById(R.id.editTextListName);
        editTextListDescription = view.findViewById(R.id.editTextListDescription);
        editTextListColour = view.findViewById(R.id.editTextListColour);
        buttonRed = view.findViewById(R.id.buttonRed);
        buttonGreen = view.findViewById(R.id.buttonGreen);
        buttonBlue = view.findViewById(R.id.buttonBlue);
        buttonPink = view.findViewById(R.id.buttonPink);
        buttonYellow = view.findViewById(R.id.buttonYellow);
        buttonOrange = view.findViewById(R.id.buttonOrange);

        buttons = new ArrayList<>();
        buttons.add(buttonBlue);
        buttons.add(buttonOrange);
        buttons.add(buttonGreen);
        buttons.add(buttonPink);
        buttons.add(buttonRed);
        buttons.add(buttonYellow);

        // implement buttons
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(buttonBlue);
            }
        });
        buttonOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(buttonOrange);
            }
        });
        buttonPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(buttonPink);
            }
        });
        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(buttonRed);
            }
        });
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(buttonGreen);
            }
        });
        buttonYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(buttonYellow);
            }
        });

        imageButtonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = editTextListName.getText().toString().trim();
                String description = editTextListDescription.getText().toString().trim();
                String colour = editTextListColour.getText().toString().trim();
                List list = createListDialogListener.saveList(name, description, colour);
                Intent intent = new Intent(getActivity(), ListActivity.class);
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            createListDialogListener = (CreateListDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }

    }

    public interface CreateListDialogListener {
        List saveList(String name, String description, String colour);
    }

    private void selectButton(Button buttonToSelect) {
        // select the selected button
        if (buttonToSelect == buttonBlue) {
                    editTextListColour.setText("#" + Integer.toHexString(getResources().getColor(R.color.dialog_create_list_button_blue)));
                    buttonBlue.setBackground(getResources().getDrawable(R.drawable.button_blue_selected));
                } else if (buttonToSelect == buttonGreen) {
                    editTextListColour.setText("#" + Integer.toHexString(getResources().getColor(R.color.dialog_create_list_button_green)));
                    buttonToSelect.setBackground(getResources().getDrawable(R.drawable.button_green_selected));
                } else if (buttonToSelect == buttonOrange) {
                    editTextListColour.setText("#" + Integer.toHexString(getResources().getColor(R.color.dialog_create_list_button_orange)));
                    buttonToSelect.setBackground(getResources().getDrawable(R.drawable.button_orange_selected));
                } else if (buttonToSelect == buttonPink) {
                    editTextListColour.setText("#" + Integer.toHexString(getResources().getColor(R.color.dialog_create_list_button_pink)));
                    buttonToSelect.setBackground(getResources().getDrawable(R.drawable.button_pink_selected));
                } else if (buttonToSelect == buttonRed) {
                    editTextListColour.setText("#" + Integer.toHexString(getResources().getColor(R.color.dialog_create_list_button_red)));
                    buttonToSelect.setBackground(getResources().getDrawable(R.drawable.button_red_selected));
                } else if (buttonToSelect == buttonYellow) {
                    editTextListColour.setText("#" + Integer.toHexString(getResources().getColor(R.color.dialog_create_list_button_yellow)));
                    buttonToSelect.setBackground(getResources().getDrawable(R.drawable.button_yellow_selected));
                }

        // deselect the others
            for (Button button : buttons) {
            if (button != buttonToSelect) {
                button.setSelected(false);
                if (button == buttonBlue) {
                    button.setBackgroundColor(getResources().getColor(R.color.dialog_create_list_button_blue));
                } else if (button == buttonGreen) {
                    button.setBackgroundColor(getResources().getColor(R.color.dialog_create_list_button_green));
                } else if (button == buttonOrange) {
                    button.setBackgroundColor(getResources().getColor(R.color.dialog_create_list_button_orange));
                } else if (button == buttonPink) {
                    button.setBackgroundColor(getResources().getColor(R.color.dialog_create_list_button_pink));
                } else if (button == buttonRed) {
                    button.setBackgroundColor(getResources().getColor(R.color.dialog_create_list_button_red));
                } else if (button == buttonYellow) {
                    button.setBackgroundColor(getResources().getColor(R.color.dialog_create_list_button_yellow));
            }
        }

    }
}}
