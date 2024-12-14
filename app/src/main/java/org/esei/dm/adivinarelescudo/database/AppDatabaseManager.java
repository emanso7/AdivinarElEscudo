package org.esei.dm.adivinarelescudo.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.esei.dm.adivinarelescudo.SesionManager.SesionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppDatabaseManager {
    //private AppDatabase dbHelper;
    private DBManager dbHelper;
    private SQLiteDatabase database;

    /*public AppDatabaseManager(Context context) {
        dbHelper = new AppDatabase(context);
    }*/
    public AppDatabaseManager(Context context) {
        dbHelper = new DBManager(context);
    }


    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Métodos para Usuarios

    // Verificar si el nombre de usuario ya está en uso
    public boolean isUsernameInUse(String username) {
        Cursor cursor = database.query(
                DBManager.ADIVINAESCUDO_USR_TABLE_NAME, // Tabla de usuarios
                new String[]{"id"},         // Seleccionar solo el ID
                "nombre_usuario = ?",       // Condición WHERE
                new String[]{username},     // Argumento del WHERE
                null, null, null            // Sin agrupamientos ni ordenamientos
        );

        boolean exists = (cursor.getCount() > 0); // Si hay resultados, el usuario ya existe
        cursor.close(); // Cierra el cursor para liberar recursos
        return exists;
    }

    // Insertar un usuario
    public long insertUser(String nombre, String nombreUsuario, String contraseña, String email) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);                  // Nombre completo
        values.put("nombre_usuario", nombreUsuario);   // Nombre de usuario
        values.put("contraseña", contraseña);          // Contraseña (recomendado: encriptada)
        values.put("email", email);                    // Email
        return database.insert(DBManager.ADIVINAESCUDO_USR_TABLE_NAME, null, values);
    }

// Comprobar contraseña y nombre de usuario
    public boolean checkUser(String nombreUsuario, String contraseña) {
        Cursor cursor = database.query(
                DBManager.ADIVINAESCUDO_USR_TABLE_NAME,
                new String[]{"id"}, // Solo necesitamos verificar si existe el ID
                "nombre_usuario = ? AND contraseña = ?", // Condición WHERE
                new String[]{nombreUsuario, contraseña}, // Argumentos del WHERE
                null, null, null
        );

        boolean exists = (cursor.getCount() > 0); // Si hay resultados, el usuario existe
        cursor.close(); // Liberar recursos
        return exists;
    }





    // Actualizar puntaje de usuario
    public int updateScore(String nombreUsuario, int nuevoPuntaje) {
        ContentValues values = new ContentValues();
        values.put("puntaje", nuevoPuntaje);

        return database.update(
                DBManager.ADIVINAESCUDO_USR_TABLE_NAME,
                values,
                "nombre_usuario = ?", // Condición WHERE
                new String[]{nombreUsuario}
        );
    }

    public UserDetails getUserDetails(String nombreUsuario) {
        Cursor cursor = database.query(
                DBManager.ADIVINAESCUDO_USR_TABLE_NAME,
                new String[]{"nombre", "nombre_usuario", "email", "puntaje"}, // Columnas necesarias
                "nombre_usuario = ?", // Condición WHERE
                new String[]{nombreUsuario},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") int puntaje = cursor.getInt(cursor.getColumnIndex("puntaje"));

            cursor.close();
            return new UserDetails(nombre, nombreUsuario, email, puntaje);
        }

        if (cursor != null) {
            cursor.close();
        }
        return null; // Si no se encuentra el usuario
    }

    public int getUserPoints(String nombreUsuario) {
        Cursor cursor = database.query(
                DBManager.ADIVINAESCUDO_USR_TABLE_NAME,
                new String[]{"puntaje"}, // Solo la columna de puntaje
                "nombre_usuario = ?", // Condición WHERE
                new String[]{nombreUsuario},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int puntaje = cursor.getInt(cursor.getColumnIndex("puntaje"));
            cursor.close();
            return puntaje; // Devuelve directamente el puntaje
        }

        if (cursor != null) {
            cursor.close();
        }

        return 0; // Retorna 0 si no se encuentra el usuario
    }

    // Verificar si el correo está en uso
    public boolean isEmailInUse(String email) {
        Cursor cursor = database.query(
                DBManager.ADIVINAESCUDO_USR_TABLE_NAME,
                new String[]{"id"},
                "email = ?",
                new String[]{email},
                null, null, null
        );
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Actualizar nombre de usuario
    public int updateUsername(String oldUsername, String newUsername) {
        ContentValues values = new ContentValues();
        values.put("nombre_usuario", newUsername); // Nuevo valor para el nombre de usuario

        // Actualizar el registro correspondiente
        return database.update(
                DBManager.ADIVINAESCUDO_USR_TABLE_NAME,       // Tabla
                values,                           // Nuevos valores
                "nombre_usuario = ?",             // Condición WHERE
                new String[]{oldUsername}         // Argumentos del WHERE
        );
    }

    // Método para obtener los 5 usuarios con más puntaje
    public List<Usuario> obtenerTopUsuarios() {
        List<Usuario> topUsuarios = new ArrayList<>();

        // Consulta SQL para obtener los 5 usuarios con más puntaje
        String query = "SELECT nombre_usuario, puntaje FROM usuarios ORDER BY puntaje DESC LIMIT 10";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombreUsuario = cursor.getString(cursor.getColumnIndex("nombre_usuario"));
                @SuppressLint("Range") int puntaje = cursor.getInt(cursor.getColumnIndex("puntaje"));

                // Agregar el usuario a la lista
                topUsuarios.add(new Usuario(nombreUsuario, puntaje));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return topUsuarios;
    }

    public List<Usuario> obtenerTopUsuariosAscendente() {
        // Obtener la lista de usuarios en orden descendente
        List<Usuario> topUsuarios = obtenerTopUsuarios();

        // Invertir la lista para que quede en orden ascendente
        Collections.reverse(topUsuarios);

        return topUsuarios;
    }




}
