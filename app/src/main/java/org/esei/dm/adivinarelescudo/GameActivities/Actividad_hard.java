package org.esei.dm.adivinarelescudo.GameActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Database.Escudo;
import org.esei.dm.adivinarelescudo.Database.UserDetails;
import org.esei.dm.adivinarelescudo.Questions.Pregunta;
import org.esei.dm.adivinarelescudo.Questions.Preguntas_hard;
import org.esei.dm.adivinarelescudo.Questions.Preguntas_medium;

import java.util.List;

public class Actividad_hard extends AppCompatActivity {
    private List<Pregunta> preguntas; // Lista de preguntas
    private int preguntaActual = 0;  // Índice de la pregunta actual
    private int puntajeActual; // Puntaje actual del usuario
    private ImageView imageViewEscudo;
    private Button option1, option2, option3, option4;

    private TextView puntosText;
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


        puntosText= findViewById(R.id.puntos_juego);

        puntosText.setText(String.valueOf(puntajeActual)); // Muestra el puntaje inicial

        // Cargar las preguntas
        Preguntas_hard preguntasDificultad = new Preguntas_hard(this);
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


        // Restaurar colores de los botones
        restaurarColoresBotones();

        // Configurar los listeners para los botones
        option1.setOnClickListener(v -> validarRespuesta(option1, opciones.get(0), pregunta.getRespuestaCorrecta()));
        option2.setOnClickListener(v -> validarRespuesta(option2, opciones.get(1), pregunta.getRespuestaCorrecta()));
        option3.setOnClickListener(v -> validarRespuesta(option3, opciones.get(2), pregunta.getRespuestaCorrecta()));
        option4.setOnClickListener(v -> validarRespuesta(option4, opciones.get(3), pregunta.getRespuestaCorrecta()));
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



    private void validarRespuesta(Button botonSeleccionado, String seleccion, String respuestaCorrecta) {
        int correcto = ContextCompat.getColor(this, R.color.correct_option);
        int incorrecto = ContextCompat.getColor(this, R.color.incorrect_option);


        if (seleccion.equals(respuestaCorrecta)) {

            // Incrementar el puntaje y actualizarlo
            puntajeActual=puntajeActual+5;
            puntosText.setText(String.valueOf(puntajeActual)); // Muestra el puntaje actualizado
            botonSeleccionado.setBackgroundColor(correcto); // Pintar de verde
        } else {
            puntajeActual=puntajeActual-5;
            puntosText.setText(String.valueOf(puntajeActual)); // Muestra el puntaje actualizado
            botonSeleccionado.setBackgroundColor(incorrecto);
        }



        // Retrasar el paso a la siguiente pregunta para mostrar el color del botón
        botonSeleccionado.postDelayed(() -> {
            preguntaActual++;
            mostrarPregunta();
        }, 1000); // Retraso
    }

    private void mostrarResumenFinal() {
        // Crear un Intent para ir a la actividad final
        Intent intentFinal = new Intent(Actividad_hard.this, Actividad_final.class);
        startActivity(intentFinal);

        // Finalizar la actividad actual
        finish();
    }

    private void restaurarColoresBotones() {
        // Restaurar el color de fondo predeterminado de los botones usando ContextCompat
        int defaultColor = ContextCompat.getColor(this, R.color.default_option);
        option1.setBackgroundColor(defaultColor);
        option2.setBackgroundColor(defaultColor);
        option3.setBackgroundColor(defaultColor);
        option4.setBackgroundColor(defaultColor);
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
