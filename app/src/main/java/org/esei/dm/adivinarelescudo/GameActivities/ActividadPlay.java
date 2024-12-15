package org.esei.dm.adivinarelescudo.GameActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.HomeActivities.ActividadHome;
import org.esei.dm.adivinarelescudo.LoginActivities.ActividadLogin;
import org.esei.dm.adivinarelescudo.Prueba;
import org.esei.dm.adivinarelescudo.R;

public class ActividadPlay extends AppCompatActivity {
    private String nombreUsuarioActivo; // Usuario activo recibido desde Home
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Referencia al botón de opciones
        ImageButton imagenOpciones = findViewById(R.id.imagen_opciones);
        Button facilButton = findViewById(R.id.button_easy);
        Button medioButton = findViewById(R.id.button_medium);
        Button dificilButton = findViewById(R.id.button_hard);
        // Configurar el clic para ir a la actividad fácil
        facilButton.setOnClickListener(v -> {
                Intent intent = new Intent(ActividadPlay.this, Prueba.class);
                intent.putExtra("clave", "Facil");
                startActivity(intent);
        });


        medioButton.setOnClickListener(v -> {
                Intent intent = new Intent(ActividadPlay.this, Prueba.class);
                intent.putExtra("clave", "Media");
                startActivity(intent);
        });

        dificilButton.setOnClickListener(v -> {
                Intent intent = new Intent(ActividadPlay.this, Prueba.class);
                intent.putExtra("clave", "Dificil");
                startActivity(intent);
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
                    Intent intentInicio = new Intent(ActividadPlay.this, ActividadHome.class);
                    startActivity(intentInicio);
                    finish();
                    return true;
                } else if (id == R.id.opcion_cerrar_sesion) {
                    // Acción para cerrar sesión
                    Intent intentCerrarSesion = new Intent(ActividadPlay.this, ActividadLogin.class);
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
