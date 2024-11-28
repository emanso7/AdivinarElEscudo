package org.esei.dm.adivinarelescudo.database;

import org.esei.dm.adivinarelescudo.Adivinar;

public class User {
    private DBManager dbManager;
    public User(Adivinar adivinar){
        this.dbManager = adivinar.getDbManager();
    }

}
