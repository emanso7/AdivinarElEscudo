package org.esei.dm.adivinarelescudo.GameActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Database.Escudo;
import org.esei.dm.adivinarelescudo.Database.UserDetails;
import org.esei.dm.adivinarelescudo.Questions.Pregunta;
import org.esei.dm.adivinarelescudo.Questions.Preguntas_medium;

import java.util.List;

public class Actividad_medium extends AppCompatActivity {
    private String nombreUsuarioActivo; // Usuario activo recibido
    private List<Pregunta> preguntas; // Lista de preguntas
    private int preguntaActual = 0;  // Índice de la pregunta actual
    private int puntajeActual; // Puntaje actual del usuario
    private ImageView imageViewEscudo;
    private Button option1, option2, option3, option4;

    private AppDatabaseManager userDatabase; // Instancia de UserDatabase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium);

        // Inicializar las vistas
        imageViewEscudo = findViewById(R.id.imageViewEscudo);
        option1 = findViewById(R.id.button_option1);
        option2 = findViewById(R.id.button_option2);
        option3 = findViewById(R.id.button_option3);
        option4 = findViewById(R.id.button_option4);

        // Inicializar la base de datos
        userDatabase = new AppDatabaseManager(this);
        userDatabase.open();

        // Recuperar el nombre del usuario activo desde el Intent
        nombreUsuarioActivo = getIntent().getStringExtra("nombre_usuario_activo");

        // Cargar el puntaje actual del usuario
        UserDetails userDetails = userDatabase.getUserDetails(nombreUsuarioActivo);
        puntajeActual = (userDetails != null) ? userDetails.getPoints() : 0;

        // Cargar las preguntas
        Preguntas_medium preguntasDificultad = new Preguntas_medium(this);
        preguntas = preguntasDificultad.getPreguntas();

        // Mostrar la primera pregunta
        mostrarPregunta();
    }

    private void mostrarPregunta() {
        if (preguntaActual >= preguntas.size()) {
            // Juego terminado
            mostrarResumenFinal(); // Mostrar resumen del puntaje
            return;
        }

        Pregunta pregunta = preguntas.get(preguntaActual);

        // Mostrar la imagen del escudo
        cargarImagenEscudo(pregunta.getEscudoId());

        // Configurar las opciones
        List<String> opciones = pregunta.getOpciones();
        option1.setText(opciones.get(0));
        option2.setText(opciones.get(1));
        option3.setText(opciones.get(2));
        option4.setText(opciones.get(3));

        // Configurar los listeners para los botones
        option1.setOnClickListener(v -> validarRespuesta(opciones.get(0), pregunta.getRespuestaCorrecta()));
        option2.setOnClickListener(v -> validarRespuesta(opciones.get(1), pregunta.getRespuestaCorrecta()));
        option3.setOnClickListener(v -> validarRespuesta(opciones.get(2), pregunta.getRespuestaCorrecta()));
        option4.setOnClickListener(v -> validarRespuesta(opciones.get(3), pregunta.getRespuestaCorrecta()));
    }

    private void cargarImagenEscudo(int escudoId) {
        // Usar AppDatabaseManager para obtener el escudo
        Escudo escudo = userDatabase.getEscudoById(escudoId);

        if (escudo != null && escudo.getImagen() != null) {
            // Convertir el byte array a Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(escudo.getImagen(), 0, escudo.getImagen().length);
            // Configurar la imagen en el ImageView
            imageViewEscudo.setImageBitmap(bitmap);
        } else {
            // Si no se encuentra el escudo o no tiene imagen
            Toast.makeText(this, getString(R.string.embleme_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void validarRespuesta(String seleccion, String respuestaCorrecta) {
        if (seleccion.equals(respuestaCorrecta)) {
            Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show();

            // Incrementar el puntaje y actualizarlo en la base de datos
            puntajeActual++;
            userDatabase.updateScore(nombreUsuarioActivo, puntajeActual);
        } else {
            Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show();
        }

        // Pasar a la siguiente pregunta
        preguntaActual++;
        mostrarPregunta();
    }

    private void mostrarResumenFinal() {
        // Crear un Intent para ir a la actividad final
        Intent intentFinal = new Intent(Actividad_medium.this, Actividad_final.class);
        intentFinal.putExtra("nombre_usuario_activo", nombreUsuarioActivo); // Pasar usuario activo
        intentFinal.putExtra("puntaje", puntajeActual); // Pasar el puntaje actual
        startActivity(intentFinal);

        // Finalizar la actividad actual
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos al destruir la actividad
        if (userDatabase != null) {
            userDatabase.close();
        }
    }
}
