package org.esei.dm.adivinarelescudo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.esei.dm.adivinarelescudo.database.DBManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

                 DBManager dbHelper = new DBManager(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Intent intent = new Intent(org.esei.dm.adivinarelescudo.MainActivity.this, ActividadHome.class);
       // Intent intent = new Intent(org.esei.dm.adivinarelescudo.MainActivity.this, Actividad_login.class);

        startActivity(intent);
                finish();
            }



}