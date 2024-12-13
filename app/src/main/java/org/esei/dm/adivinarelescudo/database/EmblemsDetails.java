package org.esei.dm.adivinarelescudo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//import com.example.myapplication.R;

import org.esei.dm.adivinarelescudo.R;

import java.io.ByteArrayOutputStream;

public class EmblemsDetails {

    public static void insertarEquipos(Context context) {
        AppDatabase appDatabase = new AppDatabase(context);
        SQLiteDatabase db = appDatabase.getWritableDatabase();

        // Eliminar todos los registros de la tabla escudos para empezar de cero
        db.execSQL("DELETE FROM " + AppDatabase.TABLE_ESCUDOS);

        // Inserta escudos de la dificultad "fácil"
        insertarEscudo(db, "FC Barcelona", convertirImagenAByteArray(R.drawable.fc_barcelona, context), "fácil");
        insertarEscudo(db, "Real Madrid", convertirImagenAByteArray(R.drawable.real_madrid, context), "fácil");
        insertarEscudo(db, "Valencia", convertirImagenAByteArray(R.drawable.valencia, context), "fácil");
        insertarEscudo(db, "Villarreal", convertirImagenAByteArray(R.drawable.villarreal, context), "fácil");
        insertarEscudo(db, "Athletic", convertirImagenAByteArray(R.drawable.athletic, context), "fácil");
        insertarEscudo(db, "Atlético", convertirImagenAByteArray(R.drawable.atletico, context), "fácil");
        insertarEscudo(db, "Celta", convertirImagenAByteArray(R.drawable.celta, context), "fácil");
        insertarEscudo(db, "Mallorca", convertirImagenAByteArray(R.drawable.mallorca, context), "fácil");




        // Inserta escudos de la dificultad "medio"
        insertarEscudo(db, "Granada", convertirImagenAByteArray(R.drawable.granada, context), "medio");
        insertarEscudo(db, "Real Zaragoza", convertirImagenAByteArray(R.drawable.zaragoza, context), "medio");
        insertarEscudo(db, "Albacete", convertirImagenAByteArray(R.drawable.albacete, context), "medio");
        insertarEscudo(db, "Cádiz", convertirImagenAByteArray(R.drawable.cadiz, context), "medio");
        insertarEscudo(db, "Córdoba", convertirImagenAByteArray(R.drawable.cordoba, context), "medio");
        insertarEscudo(db, "Oviedo", convertirImagenAByteArray(R.drawable.oviedo, context), "medio");
        insertarEscudo(db, "Sporting Gijón", convertirImagenAByteArray(R.drawable.sporting_gijon, context), "medio");
        insertarEscudo(db, "Tenerife", convertirImagenAByteArray(R.drawable.tenerife, context), "medio");


        db.close(); // Cerrar la base de datos
    }

    private static void insertarEscudo(SQLiteDatabase db, String nombre, byte[] imagen, String dificultad) {
        String sql = "INSERT INTO " + AppDatabase.TABLE_ESCUDOS + " (nombre, imagen, dificultad) VALUES (?, ?, ?)";
        db.execSQL(sql, new Object[]{nombre, imagen, dificultad});
    }

    private static byte[] convertirImagenAByteArray(int resourceId, Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
