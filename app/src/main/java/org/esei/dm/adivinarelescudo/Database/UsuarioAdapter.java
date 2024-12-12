package org.esei.dm.adivinarelescudo.database;

//import static android.os.Build.VERSION_CODES.R;//linea puesta por borja

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//import com.example.myapplication.R; //linea puesta por borja

import org.esei.dm.adivinarelescudo.R;//Añadido por emilio por probar
import org.esei.dm.adivinarelescudo.database.Usuario;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {

    private final Context context;
    private final List<Usuario> usuarios;

    public UsuarioAdapter(Context context, List<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false);
        }

        Usuario usuario = usuarios.get(position);

        // Configurar posición
        TextView posicionTextView = convertView.findViewById(R.id.text_posicion);
        posicionTextView.setText(String.format("%dº", position + 1)); // 1º, 2º, etc.

        // Configurar nombre de usuario
        TextView nombreTextView = convertView.findViewById(R.id.text_nombre_usuario);
        nombreTextView.setText(usuario.getNombre());

        // Configurar puntaje
        TextView puntajeTextView = convertView.findViewById(R.id.text_puntuacion_usuario);
        puntajeTextView.setText(String.valueOf(usuario.getPuntuacion()));

        return convertView;
    }
}