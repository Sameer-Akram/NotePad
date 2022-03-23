package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialButton addNoteBtn=findViewById(R.id.addnew_note);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(MainActivity.this,AddNoteActivity.class));
            }
        });
        Realm.init(getApplicationContext());
        Realm realm=Realm.getDefaultInstance();

        RealmResults<Note> notesList=realm.where(Note.class).findAll().sort("createdTime", Sort.DESCENDING);

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter=new Adapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(adapter);
       notesList.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {
           @SuppressLint("NotifyDataSetChanged")
           @Override
           public void onChange(@NonNull RealmResults<Note> notes) {
               adapter.notifyDataSetChanged();
           }
       });
    }
}