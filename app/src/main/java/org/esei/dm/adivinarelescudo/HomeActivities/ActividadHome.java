package org.esei.dm.adivinarelescudo.HomeActivities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.GameActivities.ActividadPlay;
import org.esei.dm.adivinarelescudo.R;
import org.esei.dm.adivinarelescudo.database.DBManager;

import org.esei.dm.adivinarelescudo.LoginActivities.ActividadLogin;
import org.esei.dm.adivinarelescudo.SesionManager.SesionManager;

public class ActividadHome extends AppCompatActivity {

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
            Intent intentLogin = new Intent(this, ActividadLogin.class);
            startActivity(intentLogin);
            finish();
            return;
        }

        // Inicializar la base de datos
       //AppDatabase appDatabase = new AppDatabase(this);
        DBManager dbHelper = new DBManager(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Referencia al botón del perfil
        ImageButton buttonPerfil = findViewById(R.id.imagen_perfil);

        // Configurar el click para mostrar el menú desplegable
        buttonPerfil.setOnClickListener(v -> showPopupMenu(buttonPerfil));

        Button botonJugar= findViewById(R.id.button_jugar);

        // Configurar el click para ir a la actividad Jugar
        botonJugar.setOnClickListener(v -> {
            Intent intentJugar = new Intent(ActividadHome.this, ActividadPlay.class);
            startActivity(intentJugar);
        });

        Button botonClasificacion = findViewById(R.id.button_clasificacion);

        botonClasificacion.setOnClickListener(v -> {
            Intent intentJugar = new Intent(ActividadHome.this, ActividadClassification.class);
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
                Intent intentPerfil = new Intent(ActividadHome.this, ActividadProfile.class);
                startActivity(new Intent(ActividadHome.this, ActividadProfile.class));
                return true;
            }  else if (itemId == opcionCerrarSesion) {
                sesionManager.cerrarSesion(); // Cerrar la sesión
                Intent intentLogin = new Intent(ActividadHome.this, ActividadLogin.class);
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
