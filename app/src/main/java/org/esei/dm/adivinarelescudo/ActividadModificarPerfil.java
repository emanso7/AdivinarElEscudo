package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActividadModificarPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actividad_modificar_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btCancel=(Button) this.findViewById(R.id.cancel_button);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActividadModificarPerfil.this, ActividadPerfil.class);
                startActivity(intent);
                finish();

            }
        });
        Button btAcept=(Button) this.findViewById(R.id.acept_button);
        btAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cargar nuevos datos en BD
                Toast.makeText(ActividadModificarPerfil.this,"modifica", Toast.LENGTH_SHORT).show();

            }
        });
    }
}