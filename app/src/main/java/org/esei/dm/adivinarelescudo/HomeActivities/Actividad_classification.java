package org.esei.dm.adivinarelescudo.HomeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import org.esei.dm.adivinarelescudo.SesionManager.SesionManager;

import java.util.List;

public class Actividad_classification extends AppCompatActivity {

    private ListView listViewClasificacion;
    private UsuarioAdapter adapter;
    private List<Usuario> topUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);

        // Inicializar el ListView
        listViewClasificacion = findViewById(R.id.list_view_clasificacion);

        // Agregar encabezado al ListView
        View headerView = getLayoutInflater().inflate(R.layout.header_classification, null);
        listViewClasificacion.addHeaderView(headerView);

        // Registrar el menú contextual
        registerForContextMenu(listViewClasificacion);

        // Cargar los datos desde la base de datos
        cargarDatos();
    }

    private void cargarDatos() {
        AppDatabaseManager databaseManager = new AppDatabaseManager(this);
        databaseManager.open();


        try {
            topUsuarios = databaseManager.obtenerTopUsuarios();

            // Registrar los usuarios cargados
            Log.d("Clasificacion", "Usuarios en la base de datos:");
            for (Usuario usuario : topUsuarios) {
                Log.d("Clasificacion", "Nombre: " + usuario.getNombre() + ", Puntos: " + usuario.getPuntuacion());
            }

            if (topUsuarios == null || topUsuarios.isEmpty()) {
                mostrarVistaVacia();
            } else {
                if (adapter == null) {
                    adapter = new UsuarioAdapter(this, topUsuarios);
                    listViewClasificacion.setAdapter(adapter);
                } else {
                    adapter.clear();
                    adapter.addAll(topUsuarios);
                    adapter.notifyDataSetChanged();
                    Log.d("Clasificacion", "Adapter actualizado con nuevos datos.");
                }
            }
        } finally {
            databaseManager.close();
        }
    }



    private void mostrarVistaVacia() {
        // Ocultar el ListView y mostrar un mensaje de vacío
        listViewClasificacion.setVisibility(View.GONE);
        findViewById(R.id.vista_vacia).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar los datos al volver a la actividad
        cargarDatos();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
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
        // Obtener la información del menú contextual
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position - 1; // Ajustar la posición por el encabezado

        if (position >= 0 && position < topUsuarios.size()) {
            Usuario usuarioSeleccionado = topUsuarios.get(position);

            if (item.getItemId() == R.id.menu_ver_perfil) {
                // Abrir la actividad de perfil sin enviar datos
                Actividad_details.setUsuarioSeleccionado(usuarioSeleccionado.getNombre());
                Intent intent = new Intent(this, Actividad_details.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }
}
