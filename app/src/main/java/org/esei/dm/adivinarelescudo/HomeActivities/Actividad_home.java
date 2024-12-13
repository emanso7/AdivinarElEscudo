package org.esei.dm.adivinarelescudo.HomeActivities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Database.UserDetails;
import org.esei.dm.adivinarelescudo.GameActivities.Actividad_play;
import org.esei.dm.adivinarelescudo.Database.AppDatabase;
import org.esei.dm.adivinarelescudo.Database.EmblemsDetails;
import org.esei.dm.adivinarelescudo.LoginActivities.Actividad_login;
import org.esei.dm.adivinarelescudo.SesionManager.SesionManager;

public class Actividad_home extends AppCompatActivity {

    private String nombreUsuarioActivo; // Usuario activo pasado desde Login
    private SesionManager sesionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializar el gestor de sesión
        sesionManager = new SesionManager(this);

        // Verificar si hay una sesión activa
        if (!sesionManager.isSesionActiva()) {
            Intent intentLogin = new Intent(this, Actividad_login.class);
            startActivity(intentLogin);
            finish();
            return;
        }

        // Inicializar la base de datos
        AppDatabase appDatabase = new AppDatabase(this);

        // Verificar si la tabla escudos está vacía e insertar datos iniciales si es necesario
       if (appDatabase.isTablaEscudosVacia()) {
           //Insertar los equipos de las preguntas
           EmblemsDetails.insertarEquipos(this);
       }

        // Referencia al botón del perfil
        ImageButton buttonPerfil = findViewById(R.id.imagen_perfil);

        // Configurar el click para mostrar el menú desplegable
        buttonPerfil.setOnClickListener(v -> showPopupMenu(buttonPerfil));

        Button botonJugar= findViewById(R.id.button_jugar);

        // Configurar el click para ir a la actividad Jugar
        botonJugar.setOnClickListener(v -> {
            Intent intentJugar = new Intent(Actividad_home.this, Actividad_play.class);
            startActivity(intentJugar);
        });

        Button botonClasificacion = findViewById(R.id.button_clasificacion);

        botonClasificacion.setOnClickListener(v -> {
            Intent intentJugar = new Intent(Actividad_home.this, Actividad_classification.class);
            startActivity(intentJugar);
        });
        
    }

    // Método para mostrar el PopupMenu
    private void showPopupMenu(ImageButton anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.menu_opciones_home, popupMenu.getMenu());

        // Variables para los IDs de las opciones del menú
        int opcionVerPerfil = R.id.opcion_ver_perfil;
        int opcionCerrarSesion = R.id.opcion_cerrar_sesion;

        // Configurar las acciones del menú
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId(); // Obtener el ID del ítem seleccionado
            if (itemId == opcionVerPerfil) {
                // Abrir la actividad de perfil
                Intent intentPerfil = new Intent(Actividad_home.this, Actividad_profile.class);
                startActivity(new Intent(Actividad_home.this, Actividad_profile.class));
                return true;
            }  else if (itemId == opcionCerrarSesion) {
                sesionManager.cerrarSesion(); // Cerrar la sesión
                Intent intentLogin = new Intent(Actividad_home.this, Actividad_login.class);
                intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentLogin);
                finish();
                return true;
            }

            return false;
        });

        // Mostrar el menú
        popupMenu.show();
    }


}
