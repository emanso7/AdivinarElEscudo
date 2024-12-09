package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Actividad_profile extends AppCompatActivity {

    private UserDatabase userDatabase; // Base de datos de usuarios
    private String nombreUsuarioActivo; // Variable para el usuario activo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar la base de datos
        userDatabase = new UserDatabase(this);
        userDatabase.open();

        // Referencias a los TextView
        TextView usuarioActivo = findViewById(R.id.usuarioActivo_textView);
        TextView correoActivo = findViewById(R.id.correoActivo_textView);
        TextView puntosActivo = findViewById(R.id.puntosActivo_textView);

        // Obtener el nombre de usuario activo del Intent
        nombreUsuarioActivo = getIntent().getStringExtra("nombre_usuario_activo");

        if (nombreUsuarioActivo != null) {
            // Consultar los detalles del usuario
            UserDetails userDetails = userDatabase.getUserDetails(nombreUsuarioActivo);

            // Mostrar los datos del usuario
            if (userDetails != null) {
                usuarioActivo.setText(userDetails.getUsername());
                correoActivo.setText(userDetails.getEmail());
                puntosActivo.setText(String.valueOf(userDetails.getPoints()));
            } else {
                usuarioActivo.setText(getString(R.string.user_not_found));
                correoActivo.setText("");
                puntosActivo.setText("");
            }
        } else {
            usuarioActivo.setText(getString(R.string.user_not_found));
            correoActivo.setText("");
            puntosActivo.setText("");
        }

        Button buttonModificarUsuario = findViewById(R.id.button_modificar_usuario);
        Button buttonVolverHome = findViewById(R.id.button_volverHome);

        // Configurar botón "Volver"
        buttonVolverHome.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("nombre_usuario_modificado", nombreUsuarioActivo);
            setResult(RESULT_OK, intent);
            finish();
        });

        // Configurar botón "Modificar Usuario"
        buttonModificarUsuario.setOnClickListener(v -> mostrarDialogoModificarUsuario());
    }

    private void mostrarDialogoModificarUsuario() {
        // Crear el diálogo personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // Para que no se cierre al hacer clic fuera

        // Inflar el diseño del cuadro de diálogo
        final View dialogView = getLayoutInflater().inflate(R.layout.activity_dialog_alert, null);
        builder.setView(dialogView);

        // Referencia al campo de texto del diseño
        EditText input = dialogView.findViewById(R.id.dialogEditUsuario);

        // Configurar botones
        builder.setPositiveButton(getString(R.string.save), (dialog, which) -> {
            String nuevoUsuario = input.getText().toString().trim();

            if (!nuevoUsuario.isEmpty()) {
                // Verificar si el nombre de usuario ya existe
                if (userDatabase.isUsernameInUse(nuevoUsuario)) {
                    Toast.makeText(this, getString(R.string.user_exists), Toast.LENGTH_SHORT).show();
                } else {
                    // Actualizar el nombre de usuario en la base de datos
                    userDatabase.updateUsername(nombreUsuarioActivo, nuevoUsuario);

                    // Actualizar la variable de usuario activo
                    nombreUsuarioActivo = nuevoUsuario;

                    // Actualizar la vista con el nuevo nombre de usuario
                    TextView usuarioActivo = findViewById(R.id.usuarioActivo_textView);
                    usuarioActivo.setText(nuevoUsuario);

                    // Mostrar un mensaje de éxito
                    Toast.makeText(this, getString(R.string.user_modified), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.field_empty), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel());

        // Mostrar el cuadro de diálogo
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userDatabase != null) {
            userDatabase.close();
        }
    }
}
