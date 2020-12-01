package com.school.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class App {

    private static App instance;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static ArrayList<List> lists;

    private App () {}

    public static synchronized App getInstance () {
        if (App.instance == null) {
            App.instance = new App ();
            // load lists into App
            DatabaseReference dataRefList = database.getReference("list");
            lists = new ArrayList<>();
            dataRefList.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    List list = snapshot.getValue(List.class);
                    lists.add(list);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        return App.instance;
    }


    public ArrayList<List> getLists() {
        return lists;
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
}
