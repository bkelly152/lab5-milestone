package com.cs407.lab5_milestone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void onClick(View view) {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);

        sharedPreferences.edit().putString("username", username).apply();

        Intent intent = new Intent(this, NotesPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = "username";

        SharedPreferences sharedPreferences =
                getSharedPreferences("com.cs407.lab5_milestone", MODE_PRIVATE);
        if (sharedPreferences.getString("username", "") != "") {
            Intent intent = new Intent(this, NotesPage.class);
            startActivity(intent);
        } else {
            getSupportActionBar().setTitle("Notes");
            setContentView(R.layout.activity_main);
        }
    }

}