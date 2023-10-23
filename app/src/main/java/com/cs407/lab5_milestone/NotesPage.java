package com.cs407.lab5_milestone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesPage extends AppCompatActivity {

    public static ArrayList<Notes> notes1 = new ArrayList<>();
    ArrayList<String> displayNotes = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notespage);
        getSupportActionBar().setTitle("Notes");
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        TextView textView = (TextView) findViewById(R.id.message);
        textView.setText("Welcome " + username + " to notes app!");

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        notes1 = dbHelper.readNotes(username);

        for (Notes notes: notes1) {
            displayNotes.add(String.format("Title:%s\nDate:%s\n", notes.getTitle(), notes.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView notesListView = (ListView) findViewById(R.id.listView);
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), noteWriting.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
            String userName = sharedPreferences.getString("username", "");
            sharedPreferences.edit().clear().apply();
            goToMainActivity();
            return true;
        }
        if (item.getItemId() == R.id.addNote) {
            Intent intent = new Intent(this, noteWriting.class);
            intent.putExtra("noteId", -1);
            startActivity(intent);

            return true;
        }
        return false;
    }


}