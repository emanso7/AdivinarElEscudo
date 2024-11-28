package org.esei.dm.adivinarelescudo;

import android.app.Application;

import  org.esei.dm.adivinarelescudo.database.DBManager;

//clase relacionada con la BD
public class Adivinar extends Application{
    private DBManager dbManager;

    @Override
    public void onCreate() {
        super.onCreate();
        this.dbManager = new DBManager(this);
    }

    public DBManager getDbManager() {
        return dbManager;
    }
}
