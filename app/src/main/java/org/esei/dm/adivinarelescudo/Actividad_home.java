package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Actividad_home extends AppCompatActivity {

    private String nombreUsuarioActivo; // Usuario activo pasado desde Login
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Inicializar la base de datos
        // Verifica e inserta los datos iniciales si es necesario
        GameDatabase gameDatabase = new GameDatabase(this);

        gameDatabase.reiniciarTablaEscudos();
        EmblemsDetails.insertarEquiposIniciales(this);
        gameDatabase.imprimirTablaEscudos();
        // Obtener el usuario activo del Intent
        nombreUsuarioActivo = getIntent().getStringExtra("nombre_usuario_activo");

        // Referencia al botón del perfil
        ImageButton buttonPerfil = findViewById(R.id.imagen_perfil);

        // Configurar el click para mostrar el menú desplegable
        buttonPerfil.setOnClickListener(v -> showPopupMenu(buttonPerfil));

        Button botonJugar= findViewById(R.id.button_jugar);

        // Configurar el click para ir a la actividad Jugar
        botonJugar.setOnClickListener(v -> {
            Intent intentJugar = new Intent(Actividad_home.this, Actividad_play.class);
            intentJugar.putExtra("nombre_usuario_activo", nombreUsuarioActivo); // Pasar usuario activo
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
                // Abrir la actividad de perfil con resultado
                Intent intentPerfil = new Intent(Actividad_home.this, Actividad_profile.class);
                intentPerfil.putExtra("nombre_usuario_activo", nombreUsuarioActivo);
                startActivityForResult(intentPerfil, 1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Recuperar el nombre de usuario modificado
            String nombreUsuarioModificado = data.getStringExtra("nombre_usuario_modificado");

            if (nombreUsuarioModificado != null) {
                // Actualizar el nombre de usuario activo
                nombreUsuarioActivo = nombreUsuarioModificado;
            }
        }
    }
}
