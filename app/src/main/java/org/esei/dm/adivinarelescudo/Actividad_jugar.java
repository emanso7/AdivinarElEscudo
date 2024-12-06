package org.esei.dm.adivinarelescudo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Actividad_jugar extends AppCompatActivity {

    private GameDatabase gameDatabase;
    private ImageView imageViewEscudo;

    private int preguntaActual = 1; // Inicia con el ID 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Inicializar la base de datos y la vista
        gameDatabase = new GameDatabase(this);
        imageViewEscudo = findViewById(R.id.image_escudo);

        // Mostrar el primer escudo
        mostrarEscudoPorID(preguntaActual);
    }

    private void mostrarEscudoPorID(int id) {
        Cursor cursor = gameDatabase.obtenerEscudoPorID(id);

        if (cursor != null && cursor.moveToFirst()) {
            // Recuperar los datos del escudo
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(GameDatabase.COLUMN_NOMBRE));
            @SuppressLint("Range") byte[] imagen = cursor.getBlob(cursor.getColumnIndex(GameDatabase.COLUMN_IMAGEN));

            // Convertir la imagen a Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);

            // Mostrar la imagen en el ImageView
            imageViewEscudo.setImageBitmap(bitmap);

            // Guardar el nombre del escudo correcto (opcional para validar la respuesta)
            String respuestaCorrecta = nombre;

            // Opcional: Mostrar el nombre del equipo como Toast
            Toast.makeText(this, "Pregunta " + preguntaActual + ": " + nombre, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se encontró el escudo con ID: " + id, Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    // Método para avanzar a la siguiente pregunta
    private void siguientePregunta() {
        preguntaActual++;

        if (preguntaActual <= 10) {
            mostrarEscudoPorID(preguntaActual);
        } else {
            // Terminar el juego después de la décima pregunta
            Toast.makeText(this, "¡Juego terminado!", Toast.LENGTH_SHORT).show();
            // Aquí puedes navegar a otra actividad o mostrar un resumen del puntaje
        }
    }
}
