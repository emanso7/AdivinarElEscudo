package org.esei.dm.adivinarelescudo.database;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBManager extends SQLiteOpenHelper {


    private static String ADIVINAESCUDO_DATABASE_NAME = "adivina_db";
    private static int ADIVINAESCUDO_DATABASE_VERSION =1;
    public static final String ADIVINAESCUDO_USR_TABLE_NAME = "usuarios";
    public static final String ADIVINAESCUDO_USR_COLUMN_ID = "id";
    public static final String ADIVINAESCUDO_USR_COLUMN_EMAIL = "email";
    public static final String ADIVINAESCUDO_USR_COLUMN_USER_NAME = "nombre_usuario";
    public static final String ADIVINAESCUDO_USR_COLUMN_NAME = "nombre";
    public static final String ADIVINAESCUDO_USR_COLUMN_PASSWORD = "contraseña";
    public static final String ADIVINAESCUDO_USR_COLUMN_POINTS = "puntaje";

    //tabla preguntas
    public static final String ADIVINAESCUDO_QUIZ_TABLE_NAME = "preguntas";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_ID = "_id";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_CORRECT = "correct";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_PHOTO = "photo";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_OP1 = "op1";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_OP2 = "op2";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_OP3 = "op3";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_OP4 = "op4";
    public static final String ADIVINAESCUDO_QUIZ_COLUMN_DIFICULTAD = "dif";

    private Context context;

    public DBManager(@Nullable Context context) {
        super(context, ADIVINAESCUDO_DATABASE_NAME, null,
                ADIVINAESCUDO_DATABASE_VERSION, null);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " + ADIVINAESCUDO_USR_TABLE_NAME +"(" +
                    ADIVINAESCUDO_USR_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ADIVINAESCUDO_USR_COLUMN_NAME + " TEXT NOT NULL," +
                    ADIVINAESCUDO_USR_COLUMN_USER_NAME + " TEXT NOT NULL UNIQUE," +
                    ADIVINAESCUDO_USR_COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_USR_COLUMN_EMAIL +" TEXT NOT NULL UNIQUE ," +
                    ADIVINAESCUDO_USR_COLUMN_POINTS +" INTEGER DEFAULT 0"+
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS "+ADIVINAESCUDO_QUIZ_TABLE_NAME+"("+
                    ADIVINAESCUDO_QUIZ_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_PHOTO + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_CORRECT + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_OP1 + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_OP2 + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_OP3 + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_OP4 + " TEXT NOT NULL, " +
                    ADIVINAESCUDO_QUIZ_COLUMN_DIFICULTAD + " TEXT NOT NULL" +
                    ")");
            executeSqlFromFile(db, context, "data.sql");

            db.setTransactionSuccessful();

        }catch (SQLException exception){
            Log.e(DBManager.class.getName(), "onCreate", exception);
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina las tablas anteriores si hay cambios en la versión
        db.execSQL("DROP TABLE IF EXISTS " + ADIVINAESCUDO_USR_TABLE_NAME);
        onCreate(db);
    }

    private void executeSqlFromFile(SQLiteDatabase db, Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line);
                if (line.endsWith(";")) {
                    db.execSQL(sql.toString());
                    sql = new StringBuilder();
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.e(DBManager.class.getName(), "Error leyendo archivo SQL", e);
        }
    }

}