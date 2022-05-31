package com.example.autoschool11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StatisticsDataBaseHelper extends SQLiteOpenHelper {
    Context context;
    public static final int DATABASE_VERSION = 14;
    public static final String DATABASE_NAME = "statistics.db";
    public static final String TABLE_NAME = "Results";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TICKET = "ticket_number";
    public static final String COLUMN_RESULT = "result";
    public static final String COLUMN_RESULT_THEMES = "result_themes";
    public static final String COLUMN_RESULT_THEMES_MAX = "result_themes_max";


    public StatisticsDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TICKET + " INTEGER, " +
                COLUMN_RESULT + " INTEGER, " +
                COLUMN_RESULT_THEMES + " INTEGER, " +
                COLUMN_RESULT_THEMES_MAX + " INTEGER);";
        db.execSQL(query);
        int[] amount = {28, 11, 6, 122, 38, 34, 9, 112, 22, 18, 40, 39, 120, 3, 10, 13, 9, 7, 19, 8, 1, 4, 3, 2, 26, 59, 20, 17};
        for (int i = 1; i < 41; i++) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TICKET, i);
            cv.put(COLUMN_RESULT, 0);
            db.insert(TABLE_NAME, null, cv);
        }
        for (int i = 1; i < 29; i++) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_RESULT_THEMES, 0);
            cv.put(COLUMN_RESULT_THEMES_MAX, amount[i - 1]);
            db.update(TABLE_NAME, cv, COLUMN_ID + "=" + i, null);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void insertResults(int ticket_number, int result) {
        SQLiteDatabase dbread = getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RESULT, result);
        String where = COLUMN_TICKET + "=" + ticket_number;
        db.update(TABLE_NAME, cv, where, null);
    }

    public void insertThemeResults(int theme_number, int result) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RESULT_THEMES, result);
        db.update(TABLE_NAME, cv, COLUMN_ID + "=" + theme_number, null);
    }

    public String[] getResults() {
        String[] a = new String[40];
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            Cursor objCursor = db.rawQuery("select * from Results", null);
            if (objCursor.getCount() != 0) {
                while (objCursor.moveToNext()) {
                    int result = objCursor.getInt(2);
                    a[objCursor.getInt(0) - 1] = Integer.toString(result);
                }
            }
        }
        return a;
    }

    public String[] getThemesResults() {
        String[] a = new String[28];
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            for (int i = 1; i < 29; i++) {
                Cursor objCursor = db.rawQuery("select * from Results where _id = " + i, null);
                if (objCursor.getCount() != 0) {
                    while (objCursor.moveToNext()) {
                        int result = objCursor.getInt(3);
                        a[objCursor.getInt(0) - 1] = Integer.toString(result);
                    }
                }
            }
        }
        return a;
    }

    public int getAllResults() {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("select * from Results", null);
            if (cursor.getPosition() != 0) {
                while (cursor.moveToNext()) {
                    result += cursor.getInt(2);
                }
            }
        }
        return result;
    }

    public int get20Tickets() {
        int result20 = 0;
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("select * from Results where result == 20", null);
            if (cursor.getPosition() != 0) {
                while (cursor.moveToNext()) {
                    result20 += 1;
                }
            }
        }
        return result20;
    }

    public void restartStatisticsDB() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RESULT, 0);
        cv.put(COLUMN_RESULT_THEMES, 0);
        db.update(TABLE_NAME, cv, null, null);
    }

    public int getFullThemesCount() {
        int full_count = 0;
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            for (int i = 1; i < 29; i++) {
                Cursor cursor = db.rawQuery("select * from Results where _id = " + i, null);
                if (cursor.getPosition() != 0) {
                    while (cursor.moveToNext()) {
                        if (cursor.getInt(3) == cursor.getInt(4)) {
                            full_count += 1;
                        }
                    }
                }
            }
        }
        return full_count;
    }

    public void add10() {
        for (int i = 1; i < 15; i++) {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_RESULT, 20);
            db.update(TABLE_NAME, cv, COLUMN_ID + "=" + i, null);
        }
    }

    public void addthemes() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        TrainingDataBaseHelper trainingDataBaseHelper = new TrainingDataBaseHelper(context);
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase dbread = getReadableDatabase();
        for (int i = 1; i < 12; i++){
            Cursor cursor = dbread.rawQuery("select * from Results where _id = " + i, null);
            ContentValues cv = new ContentValues();
            cursor.moveToNext();
            cv.put(COLUMN_RESULT_THEMES, cursor.getInt(4));
            db.update(TABLE_NAME, cv, COLUMN_ID + "=" + i, null);
            trainingDataBaseHelper.increaseArrayId(dataBaseHelper.getCategoryId(i));
        }
    }
}



