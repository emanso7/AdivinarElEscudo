package org.esei.dm.adivinarelescudo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDatabase {
    private Database dbHelper;
    private SQLiteDatabase database;

    public UserDatabase(Context context) {
        dbHelper = new Database(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Insertar un usuario
    public long insertUser(String name, String username, String password, String email) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_NAME, name);
        values.put(Database.COLUMN_USERNAME, username);
        values.put(Database.COLUMN_PASSWORD, password);
        values.put(Database.COLUMN_EMAIL, email);
        return database.insert(Database.TABLE_USERS, null, values);
    }

    // Verificar usuario
    public boolean checkUser(String username, String password) {
        Cursor cursor = database.query(Database.TABLE_USERS,
                new String[]{Database.COLUMN_ID},
                Database.COLUMN_USERNAME + "=? AND " + Database.COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Actualizar puntaje
    public int updateScore(String username, int newScore) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_SCORE, newScore);
        return database.update(Database.TABLE_USERS, values,
                Database.COLUMN_USERNAME + "=?",
                new String[]{username});
    }

    // Verificar si el correo ya está en uso
    public boolean isEmailInUse(String email) {
        Cursor cursor = database.query(Database.TABLE_USERS,
                new String[]{Database.COLUMN_ID},
                Database.COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Verificar si el nombre de usuario ya está en uso
    public boolean isUsernameInUse(String username) {
        Cursor cursor = database.query(Database.TABLE_USERS,
                new String[]{Database.COLUMN_ID},
                Database.COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}

