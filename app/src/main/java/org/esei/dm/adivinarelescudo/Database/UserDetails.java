package org.esei.dm.adivinarelescudo.database;

public class UserDetails {
    private String username;
    private String email;
    private int puntaje;
    private String name;

    public UserDetails( String name, String username, String email, int puntaje) {
        this.username = username;
        this.email = email;
        this.puntaje = puntaje;
        this.name=name;
    }

    public String getUsername() {
        return username;
    }

    public String getName(){
        return name;
    }
    public String getEmail() {
        return email;
    }

    public int getPoints() {
        return puntaje;
    }
}
