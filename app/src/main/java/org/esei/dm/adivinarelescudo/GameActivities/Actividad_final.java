package org.esei.dm.adivinarelescudo.GameActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.HomeActivities.Actividad_home;
import org.esei.dm.adivinarelescudo.SesionManager.SesionManager;

public class Actividad_final extends AppCompatActivity {

    private int puntajeFinal; // Puntaje final recibido
    AppDatabaseManager database;
    SesionManager sesionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // Inicialización de base de datos y sesión

        database = new AppDatabaseManager(this);
        database.open();


        sesionManager = new SesionManager(this);

        Button buttonVolver = findViewById(R.id.button_volver);

        // Obtener el nombre del usuario activo
        String nombreUsuario = sesionManager.getNombreUsuario();

        // Referencias a las vistas
            TextView juegoTerminadoTextView = findViewById(R.id.juegoTerminado_textView);
            TextView puntosTextView = findViewById(R.id.puntos_textView);

        int puntosPrevios = database.getUserPoints(nombreUsuario);

        puntajeFinal = getIntent().getIntExtra("puntajeFinal", 0);

        int puntosTotales = puntosPrevios + puntajeFinal;

        puntosTextView.setText(String.valueOf(puntosTotales));
        
        database.updateScore(nombreUsuario, puntosTotales);


        // Configurar el botón para volver al inicio
        buttonVolver.setOnClickListener(v -> {
            Intent intent = new Intent(Actividad_final.this, Actividad_home.class);
            startActivity(intent);
            finish();
        });
    }
}
