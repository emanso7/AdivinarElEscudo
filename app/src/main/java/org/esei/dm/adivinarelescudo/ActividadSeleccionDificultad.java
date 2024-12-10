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

public class ActividadSeleccionDificultad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actividad_seleccion_dificultad);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btFacil = (Button) this.findViewById(R.id.buttonFacil);
        btFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this.startActivity( new Intent( this, actividad_clasificacion.class ) );
                Intent intent = new Intent(ActividadSeleccionDificultad.this, Prueba.class);
                startActivity(intent);
            }
        });
    }
}