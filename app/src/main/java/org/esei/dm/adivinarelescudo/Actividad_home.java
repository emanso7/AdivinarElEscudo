package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Actividad_home extends AppCompatActivity {

    private String nombreUsuarioActivo; // Usuario activo pasado desde Login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Obtener el usuario activo del Intent
        nombreUsuarioActivo = getIntent().getStringExtra("nombre_usuario_activo");

        // Referencia al botón del perfil
        ImageButton buttonPerfil = findViewById(R.id.imagen_perfil);

        // Configurar el click para mostrar el menú desplegable
        buttonPerfil.setOnClickListener(v -> showPopupMenu(buttonPerfil));
    }

    // Método para mostrar el PopupMenu
    private void showPopupMenu(ImageButton anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.menu_opciones, popupMenu.getMenu());

        // Variables para los IDs de las opciones del menú
        int opcionVerPerfil = R.id.opcion_ver_perfil;
        int opcionCerrarSesion = R.id.opcion_cerrar_sesion;

        // Configurar las acciones del menú
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId(); // Obtener el ID del ítem seleccionado
            if (itemId == opcionVerPerfil) {
                // Abrir la actividad de perfil
                Intent intentPerfil = new Intent(Actividad_home.this, Actividad_perfil.class);
                intentPerfil.putExtra("nombre_usuario_activo", nombreUsuarioActivo);
                startActivity(intentPerfil);
                return true;
            } else if (itemId == opcionCerrarSesion) {
                // Cerrar sesión y volver al Login
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
