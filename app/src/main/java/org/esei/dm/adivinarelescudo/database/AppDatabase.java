package org.esei.dm.adivinarelescudo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    // Tablas
    public static final String TABLE_ESCUDOS = "escudos";
    public static final String TABLE_USUARIOS = "usuarios";

    // Sentencia SQL para crear la tabla escudos
    private static final String CREATE_TABLE_ESCUDOS =
            "CREATE TABLE " + TABLE_ESCUDOS + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "imagen BLOB NOT NULL, " +
                    "dificultad TEXT NOT NULL CHECK (dificultad IN ('fácil', 'medio', 'difícil'))" +
                    ");";
    // Sentencia SQL para crear la tabla usuarios
    private static final String CREATE_TABLE_USUARIOS =
            "CREATE TABLE " + TABLE_USUARIOS + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +              // Nombre completo
                    "nombre_usuario TEXT NOT NULL UNIQUE, " + // Nombre de usuario único
                    "contraseña TEXT NOT NULL, " +          // Contraseña
                    "email TEXT NOT NULL UNIQUE, " +       // Email único
                    "puntaje INTEGER DEFAULT 0);";         // Puntaje inicial


    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear las tablas
        db.execSQL(CREATE_TABLE_ESCUDOS);
        db.execSQL(CREATE_TABLE_USUARIOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina las tablas anteriores si hay cambios en la versión
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESCUDOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    public boolean isTablaEscudosVacia() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ESCUDOS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }

}
