package org.esei.dm.adivinarelescudo.LoginActivities;

import org.esei.dm.adivinarelescudo.R;
import org.esei.dm.adivinarelescudo.database.AppDatabaseManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class Actividad_register extends AppCompatActivity {

    private AppDatabaseManager userDatabase; // Base de datos de usuarios
    private CheckBox checkBoxAcepto; // Checkbox para aceptar las condiciones

    // Registrar el ActivityResultLauncher para manejar resultados
    private final ActivityResultLauncher<Intent> conditionsLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Habilitar y marcar el checkbox al aceptar las condiciones
                    checkBoxAcepto.setChecked(true);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ajustar la vista a los bordes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ScrollRegistro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar la base de datos
        userDatabase = new AppDatabaseManager(this);
        userDatabase.open();

        // Referencias a los elementos del diseño
        EditText editNombre = findViewById(R.id.edit_text_nombreApellidos);
        EditText editCorreo = findViewById(R.id.edit_text_correo);
        EditText editUsuario = findViewById(R.id.edit_text_usuario);
        EditText editContraseña = findViewById(R.id.edit_text_contraseña);
        EditText editRepetirContraseña = findViewById(R.id.edit_text_repetir_contraseña);
        checkBoxAcepto = findViewById(R.id.check_acepto);
        Button botonRegistrarse = findViewById(R.id.button_registrarse);

        // Configurar el OnTouchListener para el checkbox
        checkBoxAcepto.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Abrir la actividad de condiciones de uso
                Intent intent = new Intent(Actividad_register.this, Actividad_conditions.class);
                conditionsLauncher.launch(intent);
            }
            return true; // Interceptamos el evento
        });

        // Listener para el botón de registro
        botonRegistrarse.setOnClickListener(v -> {
            String nombre = editNombre.getText().toString().trim();
            String correo = editCorreo.getText().toString().trim();
            String usuario = editUsuario.getText().toString().trim();
            String contraseña = editContraseña.getText().toString().trim();
            String repetirContraseña = editRepetirContraseña.getText().toString().trim();

            // Validaciones
            if (!checkBoxAcepto.isChecked()) {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.accepted_conditions),
                        Snackbar.LENGTH_SHORT
                ).show();
            } else if (TextUtils.isEmpty(nombre) ||
                    TextUtils.isEmpty(correo) ||
                    TextUtils.isEmpty(usuario) ||
                    TextUtils.isEmpty(contraseña) ||
                    TextUtils.isEmpty(repetirContraseña)) {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.empty_fail),
                        Snackbar.LENGTH_SHORT
                ).show();
            } else if (!contraseña.equals(repetirContraseña)) {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.password_diferent),
                        Snackbar.LENGTH_SHORT
                ).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.email_wrong),
                        Snackbar.LENGTH_SHORT
                ).show();
            } else if (userDatabase.isEmailInUse(correo)) {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.email_exists),
                        Snackbar.LENGTH_SHORT
                ).show();
            } else if (userDatabase.isUsernameInUse(usuario)) {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.user_exists),
                        Snackbar.LENGTH_SHORT
                ).show();
            } else {
                long result = userDatabase.insertUser(nombre, usuario, contraseña, correo);
                if (result != -1) {
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.registered),
                            Snackbar.LENGTH_SHORT
                    ).show();
                    finish();
                } else {
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.not_registered),
                            Snackbar.LENGTH_SHORT
                    ).show();
                }
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
