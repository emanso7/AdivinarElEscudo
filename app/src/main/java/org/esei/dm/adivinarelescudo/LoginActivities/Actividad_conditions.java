package org.esei.dm.adivinarelescudo.LoginActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import org.esei.dm.adivinarelescudo.R;

//import com.example.myapplication.R;

public class Actividad_conditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);

        Button buttonAceptar = findViewById(R.id.button_aceptar);

        // Configurar el botón "Acepto las condiciones"
        buttonAceptar.setOnClickListener(v -> {
            // Devolver RESULT_OK para indicar que las condiciones fueron aceptadas
            setResult(RESULT_OK);
            finish();
        });

        // Manejar el botón de retroceso del sistema
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Devolver RESULT_CANCELED si el usuario retrocede sin aceptar
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
