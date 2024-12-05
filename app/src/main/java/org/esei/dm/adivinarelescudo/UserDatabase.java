package org.esei.dm.adivinarelescudo;

import static org.esei.dm.adivinarelescudo.Database.COLUMN_EMAIL;
import static org.esei.dm.adivinarelescudo.Database.COLUMN_NAME;
import static org.esei.dm.adivinarelescudo.Database.COLUMN_SCORE;
import static org.esei.dm.adivinarelescudo.Database.COLUMN_USERNAME;
import static org.esei.dm.adivinarelescudo.Database.TABLE_USERS;

import android.annotation.SuppressLint;
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
        values.put(COLUMN_USERNAME, username);
        values.put(Database.COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        return database.insert(TABLE_USERS, null, values);
    }

    // Verificar usuario
    public boolean checkUser(String username, String password) {
        Cursor cursor = database.query(TABLE_USERS,
                new String[]{Database.COLUMN_ID},
                COLUMN_USERNAME + "=? AND " + Database.COLUMN_PASSWORD + "=?",
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
        return database.update(TABLE_USERS, values,
                COLUMN_USERNAME + "=?",
                new String[]{username});
    }

    // Verificar si el correo ya está en uso
    public boolean isEmailInUse(String email) {
        Cursor cursor = database.query(TABLE_USERS,
                new String[]{Database.COLUMN_ID},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Verificar si el nombre de usuario ya está en uso
    public boolean isUsernameInUse(String username) {
        Cursor cursor = database.query(TABLE_USERS,
                new String[]{Database.COLUMN_ID},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void updateUsername(String oldUsername, String newUsername) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);

        database.update(TABLE_USERS, values, COLUMN_USERNAME + "=?", new String[]{oldUsername});
    }


    public UserDetails getUserDetails(String username) {
        Cursor cursor = database.query(
                TABLE_USERS,
                new String[]{COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_SCORE, COLUMN_NAME},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            @SuppressLint("Range") int points = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
            @SuppressLint("Range") String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));

            cursor.close();

            // Devuelve un objeto con los detalles del usuario
            return new UserDetails(username, email, points, fullName);
        }

        if (cursor != null) {
            cursor.close();
        }
        return null; // Si no se encuentra el usuario
    }
}

