package org.esei.dm.adivinarelescudo.HomeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Database.UserDetails;
import org.esei.dm.adivinarelescudo.SesionManager.SesionManager;

public class Actividad_profile extends AppCompatActivity {

    private AppDatabaseManager userDatabase;
    private SesionManager sesionManager; // Gestor de sesión

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar el gestor de sesión y la base de datos
        sesionManager = new SesionManager(this);
        userDatabase = new AppDatabaseManager(this);
        userDatabase.open();

        // Referencias a las vistas
        TextView usuarioActivo = findViewById(R.id.usuarioActivo_textView);
        TextView correoActivo = findViewById(R.id.correoActivo_textView);
        TextView puntosActivo = findViewById(R.id.puntosActivo_textView);

        // Cargar los datos del usuario activo
        cargarDatosUsuario(usuarioActivo, correoActivo, puntosActivo);

        // Botones
        Button buttonModificarUsuario = findViewById(R.id.button_modificar_usuario);
        Button buttonVolverHome = findViewById(R.id.button_volverHome);

        // Configurar botón "Volver"
        buttonVolverHome.setOnClickListener(v -> {
            startActivity(new Intent(Actividad_profile.this, Actividad_home.class));
            finish();
        });

        // Configurar botón "Modificar Usuario"
        buttonModificarUsuario.setOnClickListener(v -> mostrarDialogoModificarUsuario(usuarioActivo));
    }

    private void mostrarDialogoModificarUsuario(TextView usuarioActivo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View dialogView = getLayoutInflater().inflate(R.layout.activity_dialog_alert, null);
        builder.setView(dialogView);

        EditText input = dialogView.findViewById(R.id.dialogEditUsuario);

        builder.setPositiveButton(getString(R.string.save), (dialog, which) -> {
            String nuevoUsuario = input.getText().toString().trim();
            String usuarioActual = sesionManager.getNombreUsuario(); // Usuario actual desde el gestor de sesión

            if (!nuevoUsuario.isEmpty()) {
                if (userDatabase.isUsernameInUse(nuevoUsuario)) {
                    Toast.makeText(this, getString(R.string.user_exists), Toast.LENGTH_SHORT).show();
                } else {
                    // Actualizar el nombre de usuario en la base de datos
                    userDatabase.updateUsername(usuarioActual, nuevoUsuario);

                    // Actualizar el usuario en el gestor de sesión
                    sesionManager.iniciarSesion(nuevoUsuario);

                    // Actualizar la vista
                    usuarioActivo.setText(nuevoUsuario);
                    Toast.makeText(this, getString(R.string.user_modified), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.field_empty), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void cargarDatosUsuario(TextView usuarioActivo, TextView correoActivo, TextView puntosActivo) {
        String usuarioActual = sesionManager.getNombreUsuario(); // Obtener usuario desde el gestor de sesión

        if (usuarioActual != null) {
            UserDetails userDetails = userDatabase.getUserDetails(usuarioActual);
            if (userDetails != null) {
                usuarioActivo.setText(userDetails.getUsername());
                correoActivo.setText(userDetails.getEmail());
                puntosActivo.setText(String.valueOf(userDetails.getPoints()));
            } else {
                usuarioActivo.setText(getString(R.string.user_not_found));
                correoActivo.setText("");
                puntosActivo.setText("");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosUsuario(findViewById(R.id.usuarioActivo_textView),
                findViewById(R.id.correoActivo_textView),
                findViewById(R.id.puntosActivo_textView));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userDatabase != null) {
            userDatabase.close();
        }
    }
}
