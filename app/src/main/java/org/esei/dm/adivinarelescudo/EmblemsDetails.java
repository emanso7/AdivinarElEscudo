package org.esei.dm.adivinarelescudo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;

public class EmblemsDetails {

    public static void insertarEquiposInicialesFacil(Context context) {
        GameDatabase gameDatabase = new GameDatabase(context);

        // Eliminar todos los registros de la tabla antes de insertar
        SQLiteDatabase db = gameDatabase.getWritableDatabase();
        db.execSQL("DELETE FROM " + GameDatabase.TABLE_ESCUDOS);
        // Inserta el FC Barcelona
        byte[] imagenBarcelona = convertirImagenAByteArray(R.drawable.fc_barcelona, context);
        gameDatabase.insertarEscudo("FC Barcelona", imagenBarcelona);

        // Inserta el Real Madrid
        byte[] imagenMadrid = convertirImagenAByteArray(R.drawable.real_madrid, context);
        gameDatabase.insertarEscudo("Real Madrid", imagenMadrid);

        // Inserta el Valencia
        byte[] imagenValencia = convertirImagenAByteArray(R.drawable.valencia, context);
        gameDatabase.insertarEscudo("Valencia", imagenValencia);

        // Inserta el Villarreal
        byte[] imagenVillarreal = convertirImagenAByteArray(R.drawable.villarreal, context);
        gameDatabase.insertarEscudo("Villarreal", imagenVillarreal);

        // Inserta el Celta
        byte[] imagenCelta = convertirImagenAByteArray(R.drawable.celta, context);
        gameDatabase.insertarEscudo("Celta", imagenCelta);

        // Inserta el Mallorca
        byte[] imagenMallorca = convertirImagenAByteArray(R.drawable.mallorca, context);
        gameDatabase.insertarEscudo("Mallorca", imagenMallorca);

        // Inserta el Atlético
        byte[] imagenAtletico = convertirImagenAByteArray(R.drawable.atletico, context);
        gameDatabase.insertarEscudo("Atlético Madrid", imagenAtletico);

        // Inserta el Atlhetic
        byte[] imagenAthletic = convertirImagenAByteArray(R.drawable.athletic, context);
        gameDatabase.insertarEscudo("Athletic", imagenAthletic);



    }

    public static void insertarEquiposInicialesMedio(Context context) {
        GameDatabase gameDatabase = new GameDatabase(context);

        // Eliminar todos los registros de la tabla antes de insertar
        SQLiteDatabase db = gameDatabase.getWritableDatabase();
        db.execSQL("DELETE FROM " + GameDatabase.TABLE_ESCUDOS);

        // Inserta el Granada
        byte[] imagenGranada = convertirImagenAByteArray(R.drawable.granada, context);
        gameDatabase.insertarEscudo("Granada", imagenGranada);

        // Inserta el Zaragoza
        byte[] imagenZaragoza = convertirImagenAByteArray(R.drawable.zaragoza, context);
        gameDatabase.insertarEscudo("Zaragoza", imagenZaragoza);

        // Inserta el Albacete
        byte[] imagenAlbacete = convertirImagenAByteArray(R.drawable.albacete, context);
        gameDatabase.insertarEscudo("Albacete", imagenAlbacete);

        // Inserta el Cadiz
        byte[] imagenCadiz = convertirImagenAByteArray(R.drawable.cadiz, context);
        gameDatabase.insertarEscudo("Cadiz", imagenCadiz);

        // Inserta el Cordoba
        byte[] imagenCordoba = convertirImagenAByteArray(R.drawable.cordoba, context);
        gameDatabase.insertarEscudo("Córdoba", imagenCordoba);

        // Inserta el Oviedo
        byte[] imagenOviedo = convertirImagenAByteArray(R.drawable.oviedo, context);
        gameDatabase.insertarEscudo("Oviedo", imagenOviedo);

        // Inserta el Gijón
        byte[] imagenSporting = convertirImagenAByteArray(R.drawable.sporting_gijon, context);
        gameDatabase.insertarEscudo("Sporting Gijón", imagenSporting);

        // Inserta el Tenerife
        byte[] imagenTenerife = convertirImagenAByteArray(R.drawable.tenerife, context);
        gameDatabase.insertarEscudo("Tenerife", imagenTenerife);

    }

    private static byte[] convertirImagenAByteArray(int resourceId, Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
