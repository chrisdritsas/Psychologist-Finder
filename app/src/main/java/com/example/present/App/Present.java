package com.example.present.App;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.present.Models.DataBaseHelper;
import com.example.present.Models.Entities.User;

public class Present extends Application {

    private static Present instance;
    private SQLiteDatabase database;
    private User userSession = new User(-1);

    public static Present getInstance() {
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        if (database == null || !database.isOpen()) {
            database = new DataBaseHelper(this).getWritableDatabase();
            Log.d("Application", "Creating new database connection.");
        }
        return database;
    }

    public User getUserSession() {
        return new User(userSession.getId(), userSession.getEmail(), userSession.getIsDoctor());
    }

    public void setUserSession(User userSession) {
        this.userSession = userSession;
    }

    public Present() {
        instance = this;
    }
}
