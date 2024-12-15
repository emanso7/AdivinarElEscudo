package org.esei.dm.adivinarelescudo.SesionManager;

import android.content.Context;
import android.content.SharedPreferences;

public class SesionManager {

    private static final String PREFS_NAME = "sesion_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_NAME = "user_name"; // Guarda el nombre de usuario

    private final SharedPreferences sharedPreferences;

    public SesionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Iniciar sesión
    public void iniciarSesion(String userName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_NAME, userName); // Guarda el usuario
        editor.apply();
    }

    // Cerrar sesión
    public void cerrarSesion() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Limpia todos los datos
        editor.apply();
    }

    // Verificar si la sesión está activa
    public boolean isSesionActiva() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Obtener el nombre del usuario activo
    public String getNombreUsuario() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }
}
