package org.esei.dm.adivinarelescudo.GameActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.HomeActivities.Actividad_home;
import org.esei.dm.adivinarelescudo.SesionManager.SesionManager;

public class Actividad_final extends AppCompatActivity {

    private String nombreUsuarioActivo; // Nombre del usuario activo recibido
    private int puntajeFinal; // Puntaje final recibido

    AppDatabaseManager database;
    SesionManager sesionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        database = new AppDatabaseManager(this);
        sesionManager = new SesionManager(this);


       String nombreUsuario = sesionManager.getNombreUsuario();


        // Referencias a las vistas
        TextView juegoTerminadoTextView = findViewById(R.id.juegoTerminado_textView);
        TextView puntosTextView = findViewById(R.id.puntos_textView);
        Button buttonVolver = findViewById(R.id.button_volver);

        int puntos = database.getUserDetails(nombreUsuario).getPoints();
        puntajeFinal = getIntent().getIntExtra("puntajeFinal", 0);

        float puntosFinales = puntos + puntajeFinal;
        // Mostrar la puntuación en el TextView
        puntosTextView.setText(String.valueOf(puntosFinales));

        // Configurar el botón para volver al inicio
        buttonVolver.setOnClickListener(v -> {
            Intent intent = new Intent(Actividad_final.this, Actividad_home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
