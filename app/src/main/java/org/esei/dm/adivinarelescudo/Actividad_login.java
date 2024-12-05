package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class Actividad_login extends AppCompatActivity {
    private TextView registro;
    private EditText editUsuario, editContraseña;
    private Button iniciarSesion;

    private UserDatabase userDatabase; // Base de datos de usuarios

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Aplicar el idioma guardado antes de configurar el diseño
        IdiomaManager.setLocale(this, IdiomaManager.getLocale(this));

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Ajustar el diseño para bordes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencia a los campos y botones
        registro = findViewById(R.id.text_view_sin_cuenta);
        editUsuario = findViewById(R.id.edit_text_usuario);
        editContraseña = findViewById(R.id.edit_text_contraseña);
        iniciarSesion = findViewById(R.id.button_inicio_sesion);

        // Inicializar la base de datos
        userDatabase = new UserDatabase(this);
        userDatabase.open();

        // Redirigir al registro si el usuario no tiene cuenta
        registro.setOnClickListener(v -> {
            Intent intent = new Intent(Actividad_login.this, Actividad_register.class);
            startActivity(intent);
        });

        // Botón para cambiar el idioma
        findViewById(R.id.button_idioma).setOnClickListener(view -> {
            String newLanguage = IdiomaManager.getLocale(this).equals("es") ? "en" : "es";
            IdiomaManager.setLocale(this, newLanguage);
            recreate();
        });

        // Botón para iniciar sesión
        iniciarSesion.setOnClickListener(v -> {
            String usuario = editUsuario.getText().toString().trim();
            String contraseña = editContraseña.getText().toString().trim();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "Por favor, completa todos los campos",
                        Snackbar.LENGTH_SHORT
                ).show();
            } else if (userDatabase.checkUser(usuario, contraseña)) {
                // Usuario y contraseña correctos, ir a la actividad Home
                Intent intent = new Intent(Actividad_login.this, Actividad_home.class);
                startActivity(intent);
                finish();
            } else {
                // Usuario o contraseña incorrectos
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "Usuario o contraseña incorrectos",
                        Snackbar.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userDatabase != null) {
            userDatabase.close();
        }
    }
}
