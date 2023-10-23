package com.cs407.lab5_milestone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Notes {
    private String date;
    private String username;
    private String title;
    private String content;

    public Notes(String date, String username, String title, String content) {
        this.date = date;
        this.username = username;
        this.title = title;
        this.content = content;
    }

    public String getDate() {
        return date;
    }
    public String getUsername() {
        return username;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
}
