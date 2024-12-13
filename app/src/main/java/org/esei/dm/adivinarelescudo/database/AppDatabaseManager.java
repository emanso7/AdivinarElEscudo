package org.esei.dm.adivinarelescudo.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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
                AppDatabase.TABLE_USUARIOS, // Tabla de usuarios
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
        return database.insert(AppDatabase.TABLE_USUARIOS, null, values);
    }

// Comprobar contraseña y nombre de usuario
    public boolean checkUser(String nombreUsuario, String contraseña) {
        Cursor cursor = database.query(
                AppDatabase.TABLE_USUARIOS,
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
                AppDatabase.TABLE_USUARIOS,
                values,
                "nombre_usuario = ?", // Condición WHERE
                new String[]{nombreUsuario}
        );
    }

    public UserDetails getUserDetails(String nombreUsuario) {
        Cursor cursor = database.query(
                AppDatabase.TABLE_USUARIOS,
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


    // Verificar si el correo está en uso
    public boolean isEmailInUse(String email) {
        Cursor cursor = database.query(
                AppDatabase.TABLE_USUARIOS,
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
                AppDatabase.TABLE_USUARIOS,       // Tabla
                values,                           // Nuevos valores
                "nombre_usuario = ?",             // Condición WHERE
                new String[]{oldUsername}         // Argumentos del WHERE
        );
    }


    // Métodos para Escudos

    // Insertar un escudo
    public long insertEscudo(String nombre, byte[] imagen) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("imagen", imagen);
        return database.insert(AppDatabase.TABLE_ESCUDOS, null, values);
    }

    // Obtener un escudo por ID
    public Escudo getEscudoById(int id) {
        Cursor cursor = database.query(
                AppDatabase.TABLE_ESCUDOS,
                new String[]{"id", "nombre", "imagen"}, // Asegúrate de incluir las columnas correctas
                "id = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int escudoId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            @SuppressLint("Range") byte[] imagen = cursor.getBlob(cursor.getColumnIndex("imagen"));

            cursor.close();
            return new Escudo(escudoId, nombre, imagen);
        }

        if (cursor != null) {
            cursor.close();
        }
        return null; // Si no se encuentra el escudo
    }


    public Cursor obtenerEscudosPorDificultad(SQLiteDatabase db, String dificultad) {
        return db.rawQuery(
                "SELECT * FROM " + AppDatabase.TABLE_ESCUDOS + " WHERE dificultad = ?",
                new String[]{dificultad}
        );
    }

    // Obtener todos los escudos
    public List<Escudo> getAllEscudos() {
        List<Escudo> escudos = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + AppDatabase.TABLE_ESCUDOS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                @SuppressLint("Range") byte[] imagen = cursor.getBlob(cursor.getColumnIndex("imagen"));
                escudos.add(new Escudo(id, nombre, imagen));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return escudos;
    }

    public int getEscudoIdByName(String nombre) {
        Cursor cursor = database.query(
                AppDatabase.TABLE_ESCUDOS,
                new String[]{"id"}, // Solo necesitamos el ID
                "nombre = ?", // Condición WHERE
                new String[]{nombre},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
            return id; // Retorna el ID si se encuentra el escudo
        }

        if (cursor != null) {
            cursor.close();
        }
        return -1; // Si no se encuentra, retorna un valor no válido
    }

    // Método para obtener los 5 usuarios con más puntaje
    public List<Usuario> obtenerTopUsuarios() {
        List<Usuario> topUsuarios = new ArrayList<>();

        // Consulta SQL para obtener los 5 usuarios con más puntaje
        String query = "SELECT nombre, puntaje FROM usuarios ORDER BY puntaje DESC LIMIT 5";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                @SuppressLint("Range") int puntaje = cursor.getInt(cursor.getColumnIndex("puntaje"));

                // Agregar el usuario a la lista
                topUsuarios.add(new Usuario(nombre, puntaje));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return topUsuarios;
    }



}
