package org.esei.dm.adivinarelescudo.HomeActivities;

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

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Database.UserDetails;

public class Actividad_details extends AppCompatActivity {

    private AppDatabaseManager userDatabase; // Base de datos de usuarios
    private String nombreUsuarioActivo; // Variable para el usuario activo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Inicializar la base de datos
        userDatabase = new AppDatabaseManager(this);
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

        Button buttonVolverHome = findViewById(R.id.button_volverHome);

        // Configurar botón "Volver"
        buttonVolverHome.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("nombre_usuario_modificado", nombreUsuarioActivo);
            setResult(RESULT_OK, intent);
            finish();
        });


    }

}
