package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Actividad_play extends AppCompatActivity {
    private String nombreUsuarioActivo; // Usuario activo recibido desde Home
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Recuperar el nombre del usuario desde el Intent
        nombreUsuarioActivo = getIntent().getStringExtra("nombre_usuario_activo");
        // Referencia al botón de opciones
        ImageButton imagenOpciones = findViewById(R.id.imagen_opciones);
        Button facilButton = findViewById(R.id.button_easy);
        Button medioButton = findViewById(R.id.button_medium);
        // Configurar el clic para ir a la actividad fácil
        facilButton.setOnClickListener(v -> {
            // Inicializar la base de datos
            // Verifica e inserta los datos iniciales si es necesario
            GameDatabase gameDatabase = new GameDatabase(this);

            gameDatabase.reiniciarTablaEscudos();
            EmblemsDetails.insertarEquiposInicialesFacil(this);
            gameDatabase.imprimirTablaEscudos();

            Intent intentEasy = new Intent(Actividad_play.this, Actividad_easy.class);
            intentEasy.putExtra("nombre_usuario_activo", nombreUsuarioActivo); // Pasar usuario activo
            startActivity(intentEasy);
        });

        medioButton.setOnClickListener(v -> {
            // Inicializar la base de datos
            // Verifica e inserta los datos iniciales si es necesario
            GameDatabase gameDatabase = new GameDatabase(this);

            gameDatabase.reiniciarTablaEscudos();
            EmblemsDetails.insertarEquiposInicialesMedio(this);
            gameDatabase.imprimirTablaEscudos();

            Intent intentEasy = new Intent(Actividad_play.this, Actividad_medium.class);
            intentEasy.putExtra("nombre_usuario_activo", nombreUsuarioActivo); // Pasar usuario activo
            startActivity(intentEasy);
        });
        // Configurar el clic para mostrar el menú
        imagenOpciones.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, imagenOpciones);
            popupMenu.getMenuInflater().inflate(R.menu.menu_opciones_difficulty, popupMenu.getMenu());

            // Configurar acciones del menú
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.opcion_inicio) {
                    // Acción para ir al inicio
                    Intent intentInicio = new Intent(Actividad_play.this, Actividad_home.class);
                    startActivity(intentInicio);
                    finish();
                    return true;
                } else if (id == R.id.opcion_cerrar_sesion) {
                    // Acción para cerrar sesión
                    Intent intentCerrarSesion = new Intent(Actividad_play.this, Actividad_login.class);
                    intentCerrarSesion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentCerrarSesion);
                    finish();
                    return true;
                }
                return false;
            });

            // Mostrar el menú
            popupMenu.show();
        });
    }
}
