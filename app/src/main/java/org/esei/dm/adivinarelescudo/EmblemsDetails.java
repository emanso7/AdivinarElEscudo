package org.esei.dm.adivinarelescudo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;

public class EmblemsDetails {

    public static void insertarEquiposIniciales(Context context) {
        GameDatabase gameDatabase = new GameDatabase(context);

        // Inserta el FC Barcelona
        byte[] imagenBarcelona = convertirImagenAByteArray(R.drawable.fc_barcelona, context);
        gameDatabase.insertarEscudo("FC Barcelona", imagenBarcelona);

        // Inserta el Real Madrid
        byte[] imagenMadrid = convertirImagenAByteArray(R.drawable.real_madrid, context);
        gameDatabase.insertarEscudo("Real Madrid", imagenMadrid);
    }

    private static byte[] convertirImagenAByteArray(int resourceId, Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
