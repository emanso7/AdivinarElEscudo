package org.esei.dm.adivinarelescudo.HomeActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Database.Usuario;
import org.esei.dm.adivinarelescudo.Database.UsuarioAdapter;

import java.util.List;

public class Actividad_classification extends AppCompatActivity {

    private ListView listViewClasificacion;
    private List<Usuario> topUsuarios; // Para mantener la lista de usuarios cargados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);

        listViewClasificacion = findViewById(R.id.list_view_clasificacion);

        // Agregar encabezado al ListView
        View headerView = getLayoutInflater().inflate(R.layout.header_classification, null);
        listViewClasificacion.addHeaderView(headerView);

        // Registrar el menú contextual
        registerForContextMenu(listViewClasificacion);

        // Inicializar la base de datos y cargar usuarios
        AppDatabaseManager databaseManager = new AppDatabaseManager(this);
        databaseManager.open();

        try {
            topUsuarios = databaseManager.obtenerTopUsuarios();

            UsuarioAdapter adapter = new UsuarioAdapter(this, topUsuarios);
            listViewClasificacion.setAdapter(adapter);
        } finally {
            databaseManager.close();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.list_view_clasificacion) {
            // Inflar el menú contextual
            getMenuInflater().inflate(R.menu.context_menu_ranking, menu);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position - 1; // Ajustar por el encabezado
        int opcionVerPerfil = R.id.menu_ver_perfil;
        int itemId = item.getItemId();

        // Validar posición
        if (position >= 0 && position < topUsuarios.size()) {
            Usuario usuarioSeleccionado = topUsuarios.get(position);

            // Verificar si se seleccionó la opción "Ver perfil"
            if (itemId == opcionVerPerfil) {
                // Abrir la actividad de perfil
                Intent intent = new Intent(this, Actividad_profile.class);
                intent.putExtra("nombre_usuario_activo", usuarioSeleccionado.getNombre());
                startActivity(intent);
                return true; // Acción manejada
            }
        }

        return super.onContextItemSelected(item); // Devolver al manejador base si no se manejó la acción
    }

}


