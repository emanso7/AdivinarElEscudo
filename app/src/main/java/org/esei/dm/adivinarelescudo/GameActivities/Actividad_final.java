package org.esei.dm.adivinarelescudo.GameActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.HomeActivities.Actividad_home;
import org.esei.dm.adivinarelescudo.R;

public class Actividad_final extends AppCompatActivity {

    private String nombreUsuarioActivo; // Nombre del usuario activo recibido
    private int puntajeFinal; // Puntaje final recibido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // Referencias a las vistas
        TextView juegoTerminadoTextView = findViewById(R.id.juegoTerminado_textView);
        TextView puntosTextView = findViewById(R.id.puntos_textView);
        Button buttonVolver = findViewById(R.id.button_volver);

        // Recuperar datos desde el Intent
        nombreUsuarioActivo = getIntent().getStringExtra("nombre_usuario_activo");
        puntajeFinal = getIntent().getIntExtra("puntaje", 0);

        // Mostrar la puntuación en el TextView
        puntosTextView.setText(String.valueOf(puntajeFinal));

        // Configurar el botón para volver al inicio
        buttonVolver.setOnClickListener(v -> {
            Intent intent = new Intent(Actividad_final.this, Actividad_home.class);
            intent.putExtra("nombre_usuario_activo", nombreUsuarioActivo); // Pasar el usuario activo
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
