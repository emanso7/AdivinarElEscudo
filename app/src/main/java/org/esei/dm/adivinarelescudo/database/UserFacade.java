package org.esei.dm.adivinarelescudo.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.esei.dm.adivinarelescudo.Adivinar;
import org.esei.dm.adivinarelescudo.database.User;




public class UserFacade {
       private DBManager dbManager;
    public UserFacade(Adivinar adivinar) {
        this.dbManager = adivinar.getDbManager();
    }
    public static User readUser(Cursor cursor){
        User toret = null;
        if (cursor!=null){
            try {
                toret = new User();
                toret.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_USR_COLUMN_EMAIL)));
                toret.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_USR_COLUMN_NAME)));
                toret.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_USR_COLUMN_PASSWORD)));
                toret.setPoints(cursor.getInt(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_USR_COLUMN_POINTS)));

            }catch (Exception e){
                Log.e(UserFacade.class.getName(),"readUser" ,e);
            }
        }
        return toret;
    }
    public void createUser(User user) {
        SQLiteDatabase writableDatabase = dbManager.getWritableDatabase();
        try{
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "INSERT INTO " +DBManager.ADIVINAESCUDO_USR_TABLE_NAME +
                            "(" +
                            DBManager.ADIVINAESCUDO_USR_COLUMN_EMAIL +
                            ","+
                            DBManager.ADIVINAESCUDO_USR_COLUMN_NAME+
                            ","+
                            DBManager.ADIVINAESCUDO_USR_COLUMN_PASSWORD+
                            ","+
                            DBManager.ADIVINAESCUDO_USR_COLUMN_POINTS+
                            ") VALUES (?,?)"
                    , new Object[]{user.getEmail(),user.getUserName(), user.getPassword(),user.getPoints()});
            writableDatabase.setTransactionSuccessful();
        }catch(SQLException exception){
            Log.e(UserFacade.class.getName(), "createUser", exception);
        }finally {
            writableDatabase.endTransaction();
        }
    }

    public void removeUser(User user) {
        SQLiteDatabase writableDatabase = dbManager.getWritableDatabase();
        try{
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "DELETE FROM "
                            + DBManager.ADIVINAESCUDO_USR_TABLE_NAME
                            + " WHERE "
                            + DBManager.ADIVINAESCUDO_USR_COLUMN_EMAIL +"=?"
                    , new Object[]{user.getEmail()});
            writableDatabase.setTransactionSuccessful();
        }catch(SQLException exception){
            Log.e(UserFacade.class.getName(), "removeUser", exception);
        }finally {
            writableDatabase.endTransaction();
        }
    }

    public void updateUser(User user) {
        SQLiteDatabase writableDatabase = dbManager.getWritableDatabase();
        try{
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "UPDATE "
                            + DBManager.ADIVINAESCUDO_USR_TABLE_NAME
                            + " SET "
                            + DBManager.ADIVINAESCUDO_USR_COLUMN_NAME + "=?, "
                            + DBManager.ADIVINAESCUDO_USR_COLUMN_PASSWORD + "=?, "
                            + DBManager.ADIVINAESCUDO_USR_COLUMN_POINTS + "=? "
                            + "WHERE "+DBManager.ADIVINAESCUDO_USR_COLUMN_EMAIL +"=?",
                    new Object[]{user.getUserName(), user.getPassword(), user.getPoints(), user.getEmail()});

            writableDatabase.setTransactionSuccessful();
        }catch(SQLException exception){
            Log.e(UserFacade.class.getName(), "updateUser", exception);
        }finally {
            writableDatabase.endTransaction();
        }
    }
    public Cursor getUsers(){
        Cursor toret = null;

        toret = dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.ADIVINAESCUDO_USR_TABLE_NAME,
                null);

        return toret;
    }

    public User getUsersByEmail(String email) {
        User toret = null;
        if (email!=null){
            Cursor cursor =
                    dbManager.getReadableDatabase().rawQuery("SELECT * FROM " + DBManager.ADIVINAESCUDO_USR_TABLE_NAME
                                    + " WHERE "
                                    + DBManager.ADIVINAESCUDO_USR_COLUMN_EMAIL + " = ?",
                            new String[]{email+""});

            if (cursor.moveToFirst()){
                toret = readUser(cursor);
            }
            cursor.close();
        }

        return toret;

    }


}
