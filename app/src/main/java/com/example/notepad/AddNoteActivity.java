package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        EditText titleInput=findViewById(R.id.titleinput);
        EditText descriptionInput=findViewById(R.id.discription);
        MaterialButton savebtn=findViewById(R.id.save_btn);
        Realm.init(getApplicationContext());
        Realm realm=Realm.getDefaultInstance();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=titleInput.getText().toString();
                String des=descriptionInput.getText().toString();
                long createdTime=System.currentTimeMillis();
                realm.beginTransaction();
                Note note=realm.createObject(Note.class);
                note.setTitle(title);
                note.setDescription(des);
                note.setCreatedTime(createdTime);
                realm.commitTransaction();
                Toast.makeText(AddNoteActivity.this, "Note Saved", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}