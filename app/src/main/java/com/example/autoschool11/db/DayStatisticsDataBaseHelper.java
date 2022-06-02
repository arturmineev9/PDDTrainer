package com.example.autoschool11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.autoschool11.db.db_classes.IntensityClass;

import java.util.ArrayList;

public class DayStatisticsDataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "day_statistics.db";
    public static final String TABLE_NAME = "DayResults";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_RESULT = "result";

    public DayStatisticsDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_RESULT + " INTEGER);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertDayResults(String date, int results) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase dbread = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        Cursor cursor = dbread.rawQuery("select * from DayResults", null);
        cursor.moveToLast();
        if (cursor.getPosition() == -1) {
            cv.put(COLUMN_DATE, date);
            cv.put(COLUMN_RESULT, results);
            db.insert(TABLE_NAME, null, cv);
        } else if (!date.equals(cursor.getString(1))) {
            cv.put(COLUMN_DATE, date);
            cv.put(COLUMN_RESULT, results);
            db.insert(TABLE_NAME, null, cv);
        } else {
            cv.put(COLUMN_RESULT, cursor.getInt(2) + results);
            db.update(TABLE_NAME, cv, "date = ?", new String[]{date});
        }
    }

    public ArrayList<IntensityClass> getStatisticsData() {
        ArrayList<IntensityClass> intensity = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("select * from DayResults", null);
            if (cursor.getPosition() != 0) {
                while (cursor.moveToNext()) {
                    intensity.add(new IntensityClass(cursor.getString(1), cursor.getInt(2)));
                }
            }
        }
        return intensity;
    }

    public void restartDayStatisticsDB(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }


}
