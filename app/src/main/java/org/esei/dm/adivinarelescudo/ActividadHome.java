package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActividadHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actividad_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //comentados aun no implementadas las actividades
       /* Button btClasificacion = (Button) this.findViewById(R.id.clasificacion_button);
        btClasificacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // this.startActivity( new Intent( this, actividad_clasificacion.class ) );
                        Intent intent = new Intent(actividad_home.this, actividad_clasificacion.class);
                        startActivity(intent);
                    }
                });
        Button btJugar = (Button) this.findViewById(R.id.jugar_button);
        btJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this.startActivity( new Intent( this, actividad_clasificacion.class ) );
                Intent intent = new Intent(ActividadHome.this, ActividadJugar.class);
                startActivity(intent);
            }
        });*/
        
        Button btperfil = (Button) this.findViewById(R.id.miPerfil_button);
        btperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this.startActivity( new Intent( this, actividad_clasificacion.class ) );
                Intent intent = new Intent(ActividadHome.this, ActividadPerfil.class);
                startActivity(intent);
            }
        });


    }
}