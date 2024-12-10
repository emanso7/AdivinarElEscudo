package org.esei.dm.adivinarelescudo.Language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class IdiomaManager {

    private final Context context;
    private static final String PREFS_NAME = "app_prefs";
    private static final String LANGUAGE_KEY = "app_language";

    public IdiomaManager(Context context) {
        this.context = context;
    }

    // Cambiar idioma
    public void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }

        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Guardar el idioma
        saveLanguage(languageCode);
    }

    // Guardar el idioma en SharedPreferences
    private void saveLanguage(String languageCode) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE_KEY, languageCode);
        editor.apply();
    }

    // Obtener el idioma guardado
    public String getSavedLanguage() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(LANGUAGE_KEY, "es"); // Español por defecto
    }
}
