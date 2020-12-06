package com.school.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class App {

    private static App instance;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static ArrayList<List> lists;

    private App () {}

    public static synchronized App getInstance () {
       if (App.instance == null) {
            App.instance = new App();
            lists = new ArrayList<>();
       }
        return App.instance;
    }


    public ArrayList<List> getLists() {
        return lists;
    }

    public ArrayList<Task> getTasks(List list) {
        return list.getTaskList();
    }

    public ArrayList<Task> getSubtasks(Task task) {
        if (task == null ) {
            return null;
        }
        return task.getSubtaskList();
    }

    public void setLists(ArrayList<List> lists) {
        this.lists = lists;
    }

    public boolean createList(List list) {
        this.lists.add(list);
        return true;
    }

    public boolean deleteList(List list) {
        this.lists.remove(list);
        return true;
    }
	
	public ArrayList<List> getTodaysTasks() throws CloneNotSupportedException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String today = format.format(new Date());
		ArrayList<List> listsWithTodaysTasks = new ArrayList<>();
		for (List list : this.lists) {
			// check if tasks have today as deadline
			List listWithTodaysTasks = (List) list.clone();
			/*listWithTodaysTasks.setId(list.getId());
			listWithTodaysTasks.setName(list.getName());
            listWithTodaysTasks.setDescription(list.getDescription());
            listWithTodaysTasks.setColour(list.getColour());*/
			ArrayList<Task> tasksForToday = new ArrayList<>();
			if (list.getTaskList() != null) {
                for (Task task : list.getTaskList()) {
                    if (task.getDeadline() != null) {
                        String deadline = format.format(task.getDeadline());
                        if (today.equals(deadline)) {
                            tasksForToday.add(task);
                            // whats about the subtasks? Do they have the same deadline like the parent task? Maybe the parent task can have
                            // automatically the lowest deadline of the subtasks and then we check which subtasks have this deadline and
                            // add them to subtasks


                        }
                    }
                }
                if (tasksForToday.size() > 0) {
                    // add tasks that are for today to list
                    listWithTodaysTasks.setTaskList(tasksForToday);
                    // add list with tasks for today to listsWithTodaysTasks
                    listsWithTodaysTasks.add(listWithTodaysTasks);
                }
            }
		}
		return listsWithTodaysTasks;
		
	}
}
