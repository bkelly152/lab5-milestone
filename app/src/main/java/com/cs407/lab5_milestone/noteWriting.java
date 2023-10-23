package com.cs407.lab5_milestone;

import static com.cs407.lab5_milestone.DBHelper.sqLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class noteWriting extends AppCompatActivity {

    private int noteId = -1;


    public void delete(View view) {
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        String note = ((EditText) findViewById(R.id.note)).getText().toString();
        String title = "NOTES_"+(noteId+1);

        dbHelper.deleteNotes(note, title);

        Intent intent = new Intent(this, NotesPage.class);
        startActivity(intent);
    }

    private void goToNotes() {
        Intent intent = new Intent(this, NotesPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writing);


        EditText editText = (EditText) findViewById(R.id.note);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", 0);

        if (noteId != -1) {
            Notes notes = NotesPage.notes1.get(noteId);
            String noteContent = notes.getContent();
            editText.setText(noteContent);
        }
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
                DBHelper dbHelper = new DBHelper(sqLiteDatabase);
                SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String title;
                DateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY HH:mm:ss");
                String date = dateFormat.format(new Date());
                String note = ((EditText) findViewById(R.id.note)).getText().toString();
                Log.i("Info", "Printing noteid before using in condition"+noteId);
                if (noteId == -1) {
                    title = "NOTES_" + (NotesPage.notes1.size() + 1);
                    Log.i("info", "printing content to be saved" + note);
                    dbHelper.saveNotes(username, title, date, note);
                    goToNotes();
                } else {
                    Log.i("Info", "Printing notesid from update update condition " + noteId);
                    title = "NOTES_" + (noteId + 1);
                    dbHelper.updateNotes(note, date, title, username);
                    goToNotes();
                }
            }
        });

    }
}