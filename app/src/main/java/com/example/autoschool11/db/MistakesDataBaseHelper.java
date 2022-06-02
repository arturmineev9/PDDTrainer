package com.example.autoschool11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.autoschool11.db.db_classes.DbButtonClass;

import java.util.ArrayList;

public class MistakesDataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "mistakes_db.db";
    public static final String TABLE_NAME = "Mistakes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUESTION_ID = "id_question";
    String question;
    static int cursorplace;
    int id;
    Context context;
    static String explanation;

    public static int getCursorplace() {
        return cursorplace;
    }

    public MistakesDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION_ID + " INTEGER UNIQUE);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void insertMistake(int question_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION_ID, question_id);
        db.insert(TABLE_NAME, null, cv);
    }

    public void deleteMistakes(ArrayList<Integer> arrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i = 0; i < arrayList.size(); i++){
            String where = COLUMN_ID + "=" + arrayList.get(i);
            db.delete(TABLE_NAME, where, null);
        }

    }

    public String getMistakeQuestions(int id, Context context) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        Cursor cursor = db.rawQuery("select * from Mistakes where _id =" + id, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                question = dataBaseHelper.getAllData(cursor.getInt(1));
                explanation = DataBaseHelper.getExplanation();
                dataBaseHelper.getTicketNumber(cursor.getInt(1));
            }
        }
        return question;
    }

    public ArrayList<DbButtonClass> getMistakesAnswers(int id, Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        Cursor buttonCursor = objSqliteDatabase.rawQuery("select * from Mistakes where _id =" + id, null);
        Log.d("getCount", String.valueOf(buttonCursor.getCount()));
        if (buttonCursor.getCount() != 0) {
            while (buttonCursor.moveToNext()) {
                dbButtonClassArrayList = dataBaseHelper.getAnswers(buttonCursor.getInt(1));
                Log.d("cursor", String.valueOf(buttonCursor.getInt(1)));
            }
        }
        return dbButtonClassArrayList;
    }

    public int getTableLength() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Mistakes", null);
        return cursor.getCount();
    }
    public void setOrderedId(){
        int id = 1;
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Mistakes", null);
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                if (cursor.getInt(0) != id){
                    cv.put(COLUMN_ID, 1);
                    db.update(TABLE_NAME, cv, COLUMN_ID + "=" + cursor.getInt(0), null);
                }
                id++;
            }
        }
    }
    public int getId(int question_number){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Mistakes", null);
        cursor.moveToPosition(question_number);
        return cursor.getInt(0);
    }
    public int getPositionId(int position){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Mistakes", null);
        cursor.moveToPosition(position);
        cursorplace = position;
        return cursor.getInt(0);
    }
    public void restartMistakes(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public static String getExplanation() {
        return explanation;
    }
}
