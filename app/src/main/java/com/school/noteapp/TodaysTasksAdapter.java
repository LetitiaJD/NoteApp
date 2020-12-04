package com.school.noteapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodaysTasksAdapter extends
            RecyclerView.Adapter<TodaysTasksAdapter.ViewHolder> {

    java.util.List<List> listsWithTodaysTasks;

    OnNoteListener onNoteListener;

    RecyclerView recyclerViewTodaysTasksListsWithTasks;


    public TodaysTasksAdapter(java.util.List<List> listsWithTodaysTasks, OnNoteListener onNoteListener) {
        this.listsWithTodaysTasks = listsWithTodaysTasks;
        this.onNoteListener = onNoteListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View listView = inflater.inflate(R.layout.item_todays_tasks, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(listView, onNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		// Get the data model based on position
        List listWithTodaysTasks = listsWithTodaysTasks.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.textViewItemTodaysTasksName;
		ListView listView = holder.listViewItemTaskItemTodaysTasksTasks;

		String list = listWithTodaysTasks.toString();
		for (int i = 0; i <listWithTodaysTasks.getTaskList().size(); i++) {
		    list += "\n";
		    list += (i+1);
		    list += ". ";
		    list += listWithTodaysTasks.getTaskList().get(i).getName();
        }
        textView.setText(list);
		
		// was muss ich hier tun? Erst im zweiten Schritt?



    }

    @Override
    public int getItemCount() {
        return this.listsWithTodaysTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView textViewItemTodaysTasksName;
        public ListView listViewItemTaskItemTodaysTasksTasks;
        OnNoteListener onNoteListener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, OnNoteListener onNoteListener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            textViewItemTodaysTasksName = (TextView) itemView.findViewById(R.id.textViewItemTodaysTasksName);
            listViewItemTaskItemTodaysTasksTasks = (ListView) itemView.findViewById(R.id.listViewItemTaskItemTodaysTasksTasks);

			this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
			onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
