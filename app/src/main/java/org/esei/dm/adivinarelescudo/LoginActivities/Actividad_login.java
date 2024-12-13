package org.esei.dm.adivinarelescudo.LoginActivities;

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

//import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

import org.esei.dm.adivinarelescudo.HomeActivities.Actividad_home;
import org.esei.dm.adivinarelescudo.R;
import org.esei.dm.adivinarelescudo.database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Language.IdiomaManager;
import org.esei.dm.adivinarelescudo.SesionManager.SesionManager;

public class Actividad_login extends AppCompatActivity {
    private TextView registro;
    private EditText editUsuario, editContraseña;
    private Button iniciarSesion;

    private SesionManager sesionManager;
    private AppDatabaseManager userDatabase; // Base de datos de usuarios

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Configuración del idioma
        IdiomaManager idiomaManager = new IdiomaManager(this);
        String idiomaGuardado = idiomaManager.getSavedLanguage();
        idiomaManager.setLocale(idiomaGuardado);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Inicializar el gestor de sesión
        sesionManager = new SesionManager(this);

        // Verificar si ya hay una sesión activa
        if (sesionManager.isSesionActiva()) {
            // Ir directamente a la actividad Home si hay sesión activa
            Intent intent = new Intent(this, Actividad_home.class);
            startActivity(intent);
            finish(); // Cierra la actividad de login
            return;
        }

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
        userDatabase = new AppDatabaseManager(this);
        userDatabase.open();

        // Redirigir al registro si el usuario no tiene cuenta
        registro.setOnClickListener(v -> {
            Intent intent = new Intent(Actividad_login.this, Actividad_register.class);
            startActivity(intent);
        });

        // Botón para cambiar el idioma
        findViewById(R.id.button_idioma).setOnClickListener(view -> {
            IdiomaManager idiomaManager2 = new IdiomaManager(this);

            // Alternar entre español e inglés
            String idiomaActual = idiomaManager2.getSavedLanguage();
            String nuevoIdioma = idiomaActual.equals("es") ? "en" : "es";

            idiomaManager2.setLocale(nuevoIdioma);

            // Reinicia la actividad para aplicar los cambios
            recreate();
        });

        // Botón para iniciar sesión
        iniciarSesion.setOnClickListener(v -> {
            String usuario = editUsuario.getText().toString().trim();
            String contraseña = editContraseña.getText().toString().trim();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.empty_fail),
                        Snackbar.LENGTH_SHORT
                ).show();
            } else if (userDatabase.checkUser(usuario, contraseña)) {
                // Usuario y contraseña correctos, guardar sesión
                sesionManager.iniciarSesion(usuario);

                // Ir a la actividad Home
                Intent intent = new Intent(Actividad_login.this, Actividad_home.class);
                startActivity(intent);
                finish();
            } else {
                // Usuario o contraseña incorrectos
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.user_pass_fail),
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
