package org.esei.dm.adivinarelescudo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Actividad_perfil extends AppCompatActivity {

    private UserDatabase userDatabase; // Base de datos de usuarios

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
        String nombreUsuarioActivo = getIntent().getStringExtra("nombre_usuario_activo");

        if (nombreUsuarioActivo != null) {
            // Consultar los detalles del usuario
            UserDetails userDetails = userDatabase.getUserDetails(nombreUsuarioActivo);

            // Mostrar los datos del usuario
            if (userDetails != null) {
                usuarioActivo.setText(userDetails.getUsername());
                correoActivo.setText(userDetails.getEmail());
                puntosActivo.setText(String.valueOf(userDetails.getPoints()));
            } else {
                usuarioActivo.setText("Usuario no encontrado");
                correoActivo.setText("");
                puntosActivo.setText("");
            }
        } else {
            usuarioActivo.setText("Usuario no encontrado");
            correoActivo.setText("");
            puntosActivo.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userDatabase != null) {
            userDatabase.close();
        }
    }
}
