package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActividadPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actividad_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btmenu = (Button) this.findViewById(R.id.menu_button);
        btmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this.startActivity( new Intent( this, actividad_clasificacion.class ) );
                Intent intent = new Intent(ActividadPerfil.this, ActividadHome.class);
                startActivity(intent);
            }
        });
        Button btedit=(Button) this.findViewById(R.id.editar_button);
        btedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lanza actividad modificar datos(aun no creada)
                Intent intent = new Intent(ActividadPerfil.this, ActividadModificarPerfil.class);
                startActivity(intent);

            }
        });
    }


}