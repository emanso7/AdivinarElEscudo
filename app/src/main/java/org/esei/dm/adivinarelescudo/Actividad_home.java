package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class Actividad_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

        // Referencia al botón del perfil
        ImageButton buttonPerfil = findViewById(R.id.imagen_perfil);

        // Configurar la acción al pulsar el botón
        buttonPerfil.setOnClickListener(v -> {
            // Nombre de usuario activo (ya obtenido al iniciar sesión)
            String nombreUser = getIntent().getStringExtra("nombre_usuario_activo");

            // Crear Intent y pasar el usuario activo
            Intent intent = new Intent(Actividad_home.this, Actividad_perfil.class);
            intent.putExtra("nombre_usuario_activo", nombreUser);
            startActivity(intent);
        });
    }
}