package org.esei.dm.adivinarelescudo;
public class UserDetails {
    private String username;
    private String email;
    private int points;
    private String fullName;

    public UserDetails(String username, String email, int points, String fullName) {
        this.username = username;
        this.email = email;
        this.points = points;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getPoints() {
        return points;
    }

    public String getFullName() {
        return fullName;
    }
}
