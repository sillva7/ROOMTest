package com.example.roomtest.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactDB extends RoomDatabase {

    private static ContactDB database;
    private static final String DB_name = "contacts.db";
    private static final Object LOCK = new Object();

    public static ContactDB getInstance(Context context){
        synchronized (LOCK){
            if (database == null) {
                database = Room.databaseBuilder(context, ContactDB.class, DB_name).build();
            }
            return database;
        }

    }
    public  abstract ContactDAO contactDAO();


}
