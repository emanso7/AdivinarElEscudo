package org.esei.dm.adivinarelescudo.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.esei.dm.adivinarelescudo.Prueba;
import org.esei.dm.adivinarelescudo.Adivinar;
import org.esei.dm.adivinarelescudo.database.Question;

public class QuestionFacade {
    private DBManager dbManager;
    public QuestionFacade(Adivinar adivinar) {
        this.dbManager = adivinar.getDbManager();
    }
    public static Question readQuestion(Cursor cursor){
        Question toret = null;
        if (cursor!=null){
            try {
                toret = new Question();
                //mantener este orden
                toret.setCorrect(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_QUIZ_COLUMN_CORRECT)));
                toret.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_QUIZ_COLUMN_PHOTO)));
                toret.setOption1(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP1)));
                toret.setOption2(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP2)));
                toret.setOption3(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP3)));
                toret.setOption4(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP4)));
                toret.setDificult(cursor.getString(cursor.getColumnIndexOrThrow(DBManager.ADIVINAESCUDO_QUIZ_COLUMN_DIFICULTAD)));

            }catch (Exception e){
                Log.e(QuestionFacade.class.getName(),"readQuestion" ,e);
            }
        }
        return toret;
    }
    public void createQuestion(Question question) {
        SQLiteDatabase writableDatabase = dbManager.getWritableDatabase();
        try{
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "INSERT INTO " +DBManager.ADIVINAESCUDO_QUIZ_TABLE_NAME +
                            "(" +
                            DBManager.ADIVINAESCUDO_QUIZ_COLUMN_PHOTO +
                            ","+
                            DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP1+
                            ","+
                            DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP2+
                            ","+
                            DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP3+
                            ","+
                            DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP4+
                            ") VALUES (?,?)"
                    , new Object[]{question.getPhoto(),question.getOption1(), question.getOption2(),question.getOption3(),question.getOption4()});
            writableDatabase.setTransactionSuccessful();
        }catch(SQLException exception){
            Log.e(QuestionFacade.class.getName(), "createQuestions", exception);
        }finally {
            writableDatabase.endTransaction();
        }
    }

    public void removeQuestion(Question question) {
        SQLiteDatabase writableDatabase = dbManager.getWritableDatabase();
        try{
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "DELETE FROM "
                            + DBManager.ADIVINAESCUDO_QUIZ_TABLE_NAME
                            + " WHERE "
                            + DBManager.ADIVINAESCUDO_QUIZ_COLUMN_ID +"=?"
                    , new Object[]{question.getId()});
            writableDatabase.setTransactionSuccessful();
        }catch(SQLException exception){
            Log.e(QuestionFacade.class.getName(), "removeQuestion", exception);
        }finally {
            writableDatabase.endTransaction();
        }
    }

    public void updateQuestion(Question question) {
        SQLiteDatabase writableDatabase = dbManager.getWritableDatabase();
        try{
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "UPDATE "
                            + DBManager.ADIVINAESCUDO_QUIZ_TABLE_NAME
                            + " SET "
                            + DBManager.ADIVINAESCUDO_QUIZ_COLUMN_CORRECT + "=?, "
                            + DBManager.ADIVINAESCUDO_QUIZ_COLUMN_PHOTO + "=?, "
                            + DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP1 + "=?, "
                            + DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP2 + "=?, "
                            + DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP3 + "=?, "
                            + DBManager.ADIVINAESCUDO_QUIZ_COLUMN_OP4 + "=?, "
                            + DBManager.ADIVINAESCUDO_QUIZ_COLUMN_DIFICULTAD + "=? "
                            + "WHERE "+DBManager.ADIVINAESCUDO_QUIZ_COLUMN_ID +"=?",
                    new Object[]{question.getCorrect(), question.getPhoto(), question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4(),question.getDificult(), question.getId()});

            writableDatabase.setTransactionSuccessful();
        }catch(SQLException exception){
            Log.e(QuestionFacade.class.getName(), "updateQuestion", exception);
        }finally {
            writableDatabase.endTransaction();
        }
    }
    public Cursor getQuestions(){
        Cursor toret = null;

        toret = dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.ADIVINAESCUDO_QUIZ_TABLE_NAME,
                null);

        return toret;
    }
    public Question getQuestionsById(Integer id) {
        Question toret = null;
        if (id!=null){
            Cursor cursor =
                    dbManager.getReadableDatabase().rawQuery("SELECT * FROM "
                                    + DBManager.ADIVINAESCUDO_QUIZ_TABLE_NAME
                                    + " WHERE "
                                    + DBManager.ADIVINAESCUDO_QUIZ_COLUMN_ID + " = ?",
                            new String[]{id+""});

            if (cursor.moveToFirst()){
                toret = readQuestion(cursor);
            }
            cursor.close();
        }

        return toret;

    }
}
