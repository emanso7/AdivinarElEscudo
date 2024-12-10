package org.esei.dm.adivinarelescudo.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GameDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    public static final String TABLE_ESCUDOS = "escudos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_IMAGEN = "imagen";

    // Sentencia SQL para crear la tabla
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_ESCUDOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT NOT NULL, " +
                    COLUMN_IMAGEN + " BLOB NOT NULL);";

    public GameDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla al inicializar la base de datos
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina la tabla anterior si existe y crea una nueva
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESCUDOS);
        onCreate(db);
    }

    // Método para insertar un escudo
    public void insertarEscudo(String nombre, byte[] imagen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_IMAGEN, imagen);

        long resultado = db.insert(TABLE_ESCUDOS, null, values);

        if (resultado == -1) {
            Log.e("GameDatabase", "Error al insertar escudo: " + nombre);
        } else {
            Log.d("GameDatabase", "Escudo insertado: " + nombre);
        }
        db.close();
    }

    public void reiniciarTablaEscudos() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Eliminar todos los registros de la tabla
        db.execSQL("DELETE FROM " + TABLE_ESCUDOS);

        // Reiniciar el contador de IDs
        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + TABLE_ESCUDOS + "'");

        Log.d("GameDatabase", "Tabla escudos reiniciada.");
    }

    public void imprimirTablaEscudos() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ESCUDOS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE));
                Log.d("GameDatabase", "ID: " + id + ", Nombre: " + nombre);
            } while (cursor.moveToNext());
        } else {
            Log.d("GameDatabase", "La tabla escudos está vacía.");
        }

        cursor.close();
    }


    public boolean isTablaEscudosVacia() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ESCUDOS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }


    // Método para obtener todos los escudos
    public Cursor obtenerEscudoPorID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ESCUDOS + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }



}
