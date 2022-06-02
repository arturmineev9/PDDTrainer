package com.example.autoschool11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.autoschool11.db.db_classes.DbButtonClass;

import java.util.ArrayList;
import java.util.Random;

public class TrainingDataBaseHelper extends SQLiteOpenHelper {

    Context context;
    String question;
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "training.db";
    public static final String TABLE_NAME = "Training";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_KNOWING = "knowing_id";
    public static final String COLUMN_MARATHON = "marathon";

    public TrainingDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KNOWING + " INTEGER, " +
                COLUMN_MARATHON + " INTEGER);";
        db.execSQL(query);
        for (int i = 1; i < 801; i++) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ID, i);
            cv.put(COLUMN_KNOWING, 1);
            cv.put(COLUMN_MARATHON, 0);
            db.insert(TABLE_NAME, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String getCountQuestions(int knowing_id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where knowing_id = " + knowing_id, null);
        return Integer.toString(cursor.getCount());
    }

    public void increaseKnowingID(int id_question) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where _id = " + id_question, null);
        cursor.moveToNext();
        ContentValues cv = new ContentValues();
        if (cursor.getInt(1) != 4) {
            cv.put(COLUMN_KNOWING, cursor.getInt(1) + 1);
            db.update(TABLE_NAME, cv, COLUMN_ID + "=" + id_question, null);
        }
    }

    public void decreaseKnowingID(int id_question) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where _id = " + id_question, null);
        cursor.moveToNext();
        ContentValues cv = new ContentValues();
        if (cursor.getInt(1) != 1) {
            cv.put(COLUMN_KNOWING, cursor.getInt(1) - 1);
            db.update(TABLE_NAME, cv, COLUMN_ID + "=" + id_question, null);
        }
    }

    public void restartTrainingData() {
        SQLiteDatabase db = getWritableDatabase();
        for (int i = 1; i < 801; i++) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ID, i);
            cv.put(COLUMN_KNOWING, 1);
            cv.put(COLUMN_MARATHON, 0);
            db.update(TABLE_NAME, cv, COLUMN_ID + "=" + i, null);
        }
    }

    public String getTrainingQuestions(int knowing_id, int number_id) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        if (objSqliteDatabase != null) {
            Cursor objCursor = objSqliteDatabase.rawQuery("select * from Training where knowing_id =" + knowing_id + " and _id = " + number_id, null);
            if (objCursor.getCount() != 0) {
                objCursor.moveToNext();
                question = dataBaseHelper.getAllData(number_id);
            }
        }
        return question;
    }

    public ArrayList<DbButtonClass> getTrainingAnswers(int id) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        Cursor buttonCursor = objSqliteDatabase.rawQuery("select * from Training where _id = " + id, null);
        if (buttonCursor.getCount() != 0) {
            while (buttonCursor.moveToNext()) {
                dbButtonClassArrayList = dataBaseHelper.getAnswers(id);
            }
        }
        return dbButtonClassArrayList;
    }

    public int getTrainingId(int question_number, int knowing) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where knowing_id = " + knowing, null);
        cursor.moveToPosition(question_number - 1);
        return cursor.getInt(0);
    }

    public int getTrainingTableLength(int knowing_id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where knowing_id = " + knowing_id, null);
        return cursor.getCount();
    }

    public void increaseArrayId(ArrayList<Integer> arrayList) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db_read = getReadableDatabase();
        for (int i = 0; i < arrayList.size(); i++) {
            Cursor cursor = db_read.rawQuery("select * from Training where _id = " + arrayList.get(i), null);
            Log.d("arraylist", String.valueOf(arrayList.get(i)));
            cursor.moveToNext();
            ContentValues cv = new ContentValues();
            if (cursor.getInt(1) != 4) {
                cv.put(COLUMN_KNOWING, cursor.getInt(1) + 1);
                db.update(TABLE_NAME, cv, COLUMN_ID + "=" + arrayList.get(i), null);
            }
        }
    }

    public void decreaseArrayId(ArrayList<Integer> arrayList) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db_read = getReadableDatabase();
        for (int i = 0; i < arrayList.size(); i++) {
            Cursor cursor = db_read.rawQuery("select * from Training where _id = " + arrayList.get(i), null);
            ContentValues cv = new ContentValues();
            cursor.moveToNext();
            if (cursor.getInt(0) != 1) {
                cv.put(COLUMN_KNOWING, cursor.getInt(0) - 1);
                db.update(TABLE_NAME, cv, COLUMN_ID + "=" + arrayList.get(i), null);
            }
        }
    }

    public void setMarathonProgress(int id, int answer){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where _id = " + id, null);
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MARATHON, answer);
        db.update(TABLE_NAME, cv, COLUMN_ID + "=" + id, null);
    }

    public int getMarathonId(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where _id = " + id, null);
        cursor.moveToNext();
        return cursor.getInt(2);
    }

    public int getKnowingCount(){
        int res = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training", null);
        while (cursor.moveToNext()){
            if (cursor.getInt(1) != 1){
                res++;
            }
        }
        return res;
    }

}
