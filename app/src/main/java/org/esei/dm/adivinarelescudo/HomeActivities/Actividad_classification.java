package org.esei.dm.adivinarelescudo.HomeActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.database.Usuario;
import org.esei.dm.adivinarelescudo.database.UsuarioAdapter;

import java.util.List;

public class Actividad_classification extends AppCompatActivity {

    private ListView listViewClasificacion;
    private UsuarioAdapter adapter;
    private List<Usuario> topUsuarios;

    private boolean isAscending = false; // Estado inicial: descendente

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

        // Configurar el botón de orden
        configurarBotonOrden();

        // Cargar los datos desde la base de datos
        cargarDatos();
    }

    private void cargarDatos() {
        AppDatabaseManager databaseManager = new AppDatabaseManager(this);
        databaseManager.open();

        try {
            topUsuarios = databaseManager.obtenerTopUsuarios();

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
                }
            }
        } finally {
            databaseManager.close();
        }
    }

    private void configurarBotonOrden() {
        Button btnOrdenarPorPuntos = findViewById(R.id.button_filtro);

        btnOrdenarPorPuntos.setOnClickListener(v -> {
            AppDatabaseManager databaseManager = new AppDatabaseManager(this);
            databaseManager.open();

            List<Usuario> usuarios;
            try {
                if (isAscending) {
                    // Cambiar a orden descendente
                    usuarios = databaseManager.obtenerTopUsuarios();
                    btnOrdenarPorPuntos.setText(getString(R.string.filter_points_desc));
                } else {
                    // Cambiar a orden ascendente
                    usuarios = databaseManager.obtenerTopUsuariosAscendente();
                    btnOrdenarPorPuntos.setText(getString(R.string.filter_points_asc));
                }

                // Alternar el estado
                isAscending = !isAscending;
                adapter.setAscending(isAscending); // Actualizar el estado del adaptador
                // Actualizar el ListView
                adapter.clear();
                adapter.addAll(usuarios);
                adapter.notifyDataSetChanged();
            } finally {
                databaseManager.close();
            }
        });

        Button volver = findViewById(R.id.button_vuelta);

        volver.setOnClickListener(v -> {
            Intent home = new Intent (Actividad_classification.this, Actividad_home.class);
            startActivity(home);
            finish();
        });
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
                finish();
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }
}

