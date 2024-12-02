package org.esei.dm.adivinarelescudo.database;

import org.esei.dm.adivinarelescudo.Adivinar;

public class User {
    /*private DBManager dbManager;
    public User(Adivinar adivinar){
        this.dbManager = adivinar.getDbManager();*/
    private String email;
    private String userName;
    private String password;
    private Integer points;
    public User(){

    }

    public User(String email, String userName, String password, Integer points) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
