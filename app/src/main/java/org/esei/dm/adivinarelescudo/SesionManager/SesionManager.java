package org.esei.dm.adivinarelescudo.SesionManager;

import android.content.Context;
import android.content.SharedPreferences;

public class SesionManager {

    private static final String PREFS_NAME = "sesion_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_NAME = "user_name"; // Opcional, para guardar el nombre del usuario

    private final SharedPreferences sharedPreferences;

    public SesionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Método para iniciar sesión
    public void iniciarSesion(String userName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_NAME, userName); // Guardar nombre de usuario (opcional)
        editor.apply();
    }

    // Método para cerrar sesión
    public void cerrarSesion() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Elimina todos los datos de sesión
        editor.apply();
    }

    public boolean verificarSesion() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }


    // Verificar si la sesión está activa
    public boolean isSesionActiva() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Obtener el nombre de usuario guardado (opcional)
    public String getNombreUsuario() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }
}
