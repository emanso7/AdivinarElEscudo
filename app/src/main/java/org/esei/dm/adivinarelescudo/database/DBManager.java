package org.esei.dm.adivinarelescudo.database;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {

    private static String ADIVINAESCUDO_DATABASE_NAME = "adivina_db";
    private static int ADIVINAESCUDO_DATABASE_VERSION =1;
    public static final String ADIVINAESCUDO_USR_TABLE_NAME = "usuarios";
    public static final String ADIVINAESCUDO_USR_COLUMN_EMAIL = "email";
    public static final String ADIVINAESCUDO_USR_COLUMN_NAME = "userName";
    public static final String ADIVINAESCUDO_USR_COLUMN_PASSWORD = "password";
    public static final String ADIVINAESCUDO_USR_COLUMN_POINTS = "points";

    //tabla preguntas
    public static final String ADIVINAESCUDO_QUIZ_TABLE_NAME = "preguntas";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_ID = "_id";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_CORRECT = "correct";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_PHOTO = "photo";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_OP1 = "op1";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_OP2 = "op2";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_OP3 = "op3";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_OP4 = "op4";

    public DBManager(@Nullable Context context) {
        super(context, ADIVINAESCUDO_DATABASE_NAME, null,
                ADIVINAESCUDO_DATABASE_VERSION, null);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " + ADIVINAESCUDO_USR_TABLE_NAME +"(" +
                    ADIVINAESCUDO_USR_COLUMN_EMAIL +" TEXT PRIMARY KEY," +
                    ADIVINAESCUDO_USR_COLUMN_NAME + " TEXT NOT NULL," +
                    ADIVINAESCUDO_USR_COLUMN_PASSWORD + " TEXT NOT NULL" +
                    ADIVINAESCUDO_USR_COLUMN_POINTS +"INTEGER NOT NULL"+
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS"+ADIVINAESCUDO_QUIZ_TABLE_NAME+"("+
                    ADIVINAESCUDO_QUIZ_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_CORRECT + " INTEGER NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_PHOTO + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_OP1 + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_OP2 + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_OP3 + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_OP4 + " TEXT NOT NULL" +
                    ")");
            db.setTransactionSuccessful();

        }catch (SQLException exception){
            Log.e(DBManager.class.getName(), "onCreate", exception);
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(DBManager.class.getSimpleName(), "onUpgrade call!");
    }
}