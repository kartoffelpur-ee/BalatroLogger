package com.example.proyectito;

import android.content.Context;
import android.database.Cursor;
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

    public double getMejorMano() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(mano) FROM runs", null);

        double mejorMano = 0;
        if (cursor.moveToFirst()) {
            mejorMano = cursor.getDouble(0);
        }
        cursor.close();
        db.close();

        return mejorMano;
    }

    public String getBarajaFavorita() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT baraja, " +
                "COUNT(baraja) AS total " +
                "FROM runs " +
                "GROUP BY baraja " +
                "ORDER BY total DESC " +
                "LIMIT 1", null);

        String barajaFavorita = "Ninguna aun.";
        if (cursor.moveToFirst()) {
            barajaFavorita = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return barajaFavorita;
    }

    public int getPorcentajeVictorias() {
        SQLiteDatabase db = this.getReadableDatabase();

        // 1. Contamos el total
        Cursor cursorTotal = db.rawQuery("SELECT COUNT(*) FROM runs", null);
        int total = 0;
        if (cursorTotal.moveToFirst()) total = cursorTotal.getInt(0);
        cursorTotal.close();

        if (total == 0) return 0;

        // 2. Contamos las victorias (Ante >= 8)
        Cursor cursorWins = db.rawQuery("SELECT COUNT(*) FROM runs WHERE ante >= 8", null);
        int wins = 0;
        if (cursorWins.moveToFirst()) wins = cursorWins.getInt(0);
        cursorWins.close();

        // 3. Calculamos el porcentaje
        return (wins * 100) / total;
    }

    public int getMejorAnte() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(ante) FROM runs", null);
        int maxAnte = 0;
        if (cursor.moveToFirst()) {
            maxAnte = cursor.getInt(0);
        }
        cursor.close();
        return maxAnte;
    }

    public double getPromedioAnte() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(ante) FROM runs", null);
        double promedio = 0;
        if (cursor.moveToFirst()) {
            promedio = cursor.getInt(0);
        }
        cursor.close();
        return promedio;
    }
}
