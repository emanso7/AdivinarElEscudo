package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.esei.dm.adivinarelescudo.R;

import java.util.Locale;

public class Actividad_login extends AppCompatActivity {
    private TextView registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        registro=findViewById(R.id.text_view_sin_cuenta);

        registro.setOnClickListener(v -> {
            Intent intent = new Intent(Actividad_login.this, Actividad_register.class);
            startActivity(intent);
        });

        // Botón para cambiar el idioma
        findViewById(R.id.button_idioma).setOnClickListener(view -> {
            // Cambiar entre español e inglés
            String newLanguage = Locale.getDefault().getLanguage().equals("es") ? "en" : "es";
            setLocale(newLanguage);
        });
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Reinicia la actividad para aplicar los cambios
        recreate();

    }

}