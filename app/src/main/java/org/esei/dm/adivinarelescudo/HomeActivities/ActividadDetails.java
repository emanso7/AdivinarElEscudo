package org.esei.dm.adivinarelescudo.HomeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.R;
import org.esei.dm.adivinarelescudo.database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.database.UserDetails;

public class ActividadDetails extends AppCompatActivity {

    private AppDatabaseManager userDatabase; // Base de datos de usuarios
    private static String usuarioSeleccionado; // Usuario seleccionado

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

        Button volver = findViewById(R.id.button_volverHome);


        volver.setOnClickListener(v -> {
            startActivity(new Intent(ActividadDetails.this, ActividadClassification.class));
            finish();
        });

        if (usuarioSeleccionado != null) {
            // Consultar los detalles del usuario
            UserDetails userDetails = userDatabase.getUserDetails(usuarioSeleccionado);

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
    }

    public static void setUsuarioSeleccionado(String usuario) {
        usuarioSeleccionado = usuario;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userDatabase != null) {
            userDatabase.close();
        }
    }
}
