package org.esei.dm.adivinarelescudo.Database;
public class Usuario {
    private String nombre;
    private int puntuacion;

    public Usuario(String nombre, int puntuacion) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
}
