package com.example.proyectito;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Base extends SQLiteOpenHelper {
    public Base(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE runs(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "baraja TEXT," +
                "stake TEXT," +
                "seed TEXT," +
                "mano REAL," +
                "ante INTEGER," +
                "notas TEXT," +
                "fecha TEXT DEFAULT (datetime('now','localtime'))" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS runs");
        onCreate(db);
    }
}
