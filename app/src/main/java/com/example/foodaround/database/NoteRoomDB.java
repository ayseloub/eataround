package com.example.foodaround.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 2)
public abstract class NoteRoomDB extends RoomDatabase {
    public abstract NoteDao daoNote();

    private static volatile NoteRoomDB INSTANCE;

    public static NoteRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                NoteRoomDB.class, "note_db")
                        .fallbackToDestructiveMigration() // Mengizinkan migrasi destruktif
                        .build();
            }
        }
        return INSTANCE;
    }
}

