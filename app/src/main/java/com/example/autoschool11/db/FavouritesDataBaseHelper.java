package com.example.autoschool11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.autoschool11.db.db_classes.DbButtonClass;

import java.util.ArrayList;

public class FavouritesDataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favourites.db";
    public static final String TABLE_NAME = "Favourites";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUESTION_ID = "id_question";
    String question;
    static int cursorplace;
    Context context;
    static String explanation;

    public static int getCursorplace() {
        return cursorplace;
    }

    public FavouritesDataBaseHelper(Context context) {
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


    public void insertFavourite(int question_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION_ID, question_id);
        db.insert(TABLE_NAME, null, cv);
    }

    public void deleteAllFavourite(ArrayList<Integer> arrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < arrayList.size(); i++) {
            String where = COLUMN_ID + "=" + arrayList.get(i);
            db.delete(TABLE_NAME, where, null);
        }
    }

    public void deleteFavourite(int id_question) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_QUESTION_ID + "=" + id_question, null);
    }

    public boolean isInFavourites(int id_question) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites where id_question = " + id_question, null);
        return cursor.getCount() != 0;
    }

    public String getFavouriteQuestions(int id, Context context) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        Cursor cursor = db.rawQuery("select * from Favourites where _id =" + id, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                question = dataBaseHelper.getAllData(cursor.getInt(1));
                explanation = DataBaseHelper.getExplanation();
                dataBaseHelper.getTicketNumber(cursor.getInt(1));
            }
        }
        return question;
    }

    public ArrayList<DbButtonClass> getFavouriteAnswers(int id, Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        Cursor buttonCursor = objSqliteDatabase.rawQuery("select * from Favourites where _id =" + id, null);
        if (buttonCursor.getCount() != 0) {
            while (buttonCursor.moveToNext()) {
                dbButtonClassArrayList = dataBaseHelper.getAnswers(buttonCursor.getInt(1));
            }
        }
        return dbButtonClassArrayList;
    }

    public int getTableLength() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites", null);
        return cursor.getCount();
    }

    public void setOrderedId() {
        int id = 1;
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Mistakes", null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(0) != id) {
                    cv.put(COLUMN_ID, 1);
                    db.update(TABLE_NAME, cv, COLUMN_ID + "=" + cursor.getInt(0), null);
                }
                id++;
            }
        }
    }

    public int getId(int question_number) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites", null);
        cursor.moveToPosition(question_number);
        return cursor.getInt(0);
    }

    public int getQuestionId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites where _id = " + id, null);
        cursor.moveToNext();
        return cursor.getInt(1);
    }

    public int getPositionId(int position) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites", null);
        cursor.moveToPosition(position);
        cursorplace = position;
        return cursor.getInt(0);
    }


    public static String getExplanation() {
        return explanation;
    }
}

