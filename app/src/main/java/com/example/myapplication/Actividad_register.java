package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class Actividad_register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ScrollRegistro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        // Referencias a los elementos del diseño
        EditText editNombre = findViewById(R.id.edit_text_nombreApellidos);
        EditText editCorreo = findViewById(R.id.edit_text_correo);
        EditText editUsuario = findViewById(R.id.edit_text_usuario);
        EditText editContraseña = findViewById(R.id.edit_text_contraseña);
        EditText editRepetirContraseña = findViewById(R.id.edit_text_repetir_contraseña);
        CheckBox checkBoxAcepto = findViewById(R.id.check_acepto);
        Button botonRegistrarse = findViewById(R.id.button_registrarse);
        TextView textCondicionesClickable = findViewById(R.id.textView_condicionesUso);


        // Configurar el click del botón "Condiciones de Uso"
        textCondicionesClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad de condiciones de uso
                Intent intent = new Intent(Actividad_register.this, Actividad_conditions.class);
                startActivity(intent);
            }
        });


// Configurar el click del botón Registrarse
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar si se ha marcado el CheckBox
                if (!checkBoxAcepto.isChecked()) {
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            "No se han aceptado las condiciones",
                            Snackbar.LENGTH_SHORT
                    ).show();
                } else if (TextUtils.isEmpty(editNombre.getText().toString()) ||
                        TextUtils.isEmpty(editCorreo.getText().toString()) ||
                        TextUtils.isEmpty(editUsuario.getText().toString()) ||
                        TextUtils.isEmpty(editContraseña.getText().toString()) ||
                        TextUtils.isEmpty(editRepetirContraseña.getText().toString())) {
                    // Validar si algún campo está vacío
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            "No se han completado todos los campos",
                            Snackbar.LENGTH_SHORT
                    ).show();
                } else if (!editContraseña.getText().toString().equals(editRepetirContraseña.getText().toString())) {
                    // Validar si las contraseñas coinciden
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            "Las contraseñas no coinciden",
                            Snackbar.LENGTH_SHORT
                    ).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(editCorreo.getText().toString()).matches()) {
                    // Validar si el correo electrónico tiene un formato válido
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            "El correo electrónico no es válido",
                            Snackbar.LENGTH_SHORT
                    ).show();
                } else {
                    // Todo está correcto
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            "Registro Exitoso",
                            Snackbar.LENGTH_SHORT
                    ).show();

                    // Finalizar la actividad y volver a la anterior
                    finish();
                }
            }
        });
    }
}

