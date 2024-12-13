package org.esei.dm.adivinarelescudo.database;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
}
