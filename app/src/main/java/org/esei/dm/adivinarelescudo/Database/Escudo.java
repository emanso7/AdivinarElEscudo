package org.esei.dm.adivinarelescudo.database;

public class Escudo {
    private int id;
    private String nombre;
    private byte[] imagen;

    public Escudo(int id, String nombre, byte[] imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }
}
