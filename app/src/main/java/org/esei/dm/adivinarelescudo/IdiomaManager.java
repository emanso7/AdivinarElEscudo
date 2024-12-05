package org.esei.dm.adivinarelescudo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class IdiomaManager {
    private static final String PREFS_NAME = "locale_prefs";
    private static final String KEY_LANGUAGE = "language";

    // Guardar el idioma seleccionado
    public static void setLocale(Context context, String language) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LANGUAGE, language);
        editor.apply();
        updateResources(context, language);
    }

    // Obtener el idioma seleccionado
    public static String getLocale(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LANGUAGE, "es"); // "es" como predeterminado (Español)
    }

    // Actualizar los recursos para el idioma seleccionado
    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}
