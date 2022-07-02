package com.example.autoschool11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.autoschool11.db.db_classes.DbButtonClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// База данных с вопросами и ответами
public class PDD_DataBaseHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "QuestionsPDD.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 23;
    private SQLiteDatabase mDataBase;
    static int correctans;
    static String question;
    static String explanation;
    static int table_length;
    static int bilet;
    static int number;
    static int count;
    static int _id;
    private final Context mContext;
    static String sign_number;
    private boolean mNeedUpdate = false;


    public PDD_DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();
        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    public String getQuestion(int id) {
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        if (objSqliteDatabase != null) {
            Cursor objCursor = objSqliteDatabase.rawQuery("select * from Questions where _id =" + id, null);
            if (objCursor.getCount() != 0) {
                while (objCursor.moveToNext()) {
                    question = objCursor.getString(2);
                    correctans = objCursor.getInt(6);
                    explanation = objCursor.getString(3);
                    bilet = objCursor.getInt(5);
                    number = objCursor.getInt(4);
                    _id = objCursor.getInt(0);
                }
            }
        }
        return question;
    }

    public ArrayList<DbButtonClass> getAnswers(int id) {
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        Cursor buttonCursor = objSqliteDatabase.rawQuery("select * from Answers where question_id =" + id, null);
        if (buttonCursor.getCount() != 0) {
            while (buttonCursor.moveToNext()) {
                String ans = buttonCursor.getString(2);
                dbButtonClassArrayList.add(new DbButtonClass(ans));
            }
        }
        return dbButtonClassArrayList;
    }

    public String getThemesQuestions(int id_category, int number_id) {
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        if (objSqliteDatabase != null) {
            Cursor objCursor = objSqliteDatabase.rawQuery("select * from Questions where category =" + id_category, null);
            if (objCursor.getCount() != 0) {
                count = objCursor.getCount();
                objCursor.moveToPosition(number_id);
                question = objCursor.getString(2);
                correctans = objCursor.getInt(6);
                bilet = objCursor.getInt(5);
                number = objCursor.getInt(4);
                _id = objCursor.getInt(0);
                table_length = objCursor.getCount();
                explanation = objCursor.getString(3);
            }
        }
        return question;
    }


    public void getTicketNumber(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Questions where _id =" + id, null);
        while (cursor.moveToNext()) {
            bilet = cursor.getInt(5);
            number = cursor.getInt(4);
            correctans = cursor.getInt(6);
        }
    }

    public void getHardQuestions(int id) {
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        if (objSqliteDatabase != null) {
            Cursor objCursor = objSqliteDatabase.rawQuery("select * from HardQuestions where _id =" + id, null);
            if (objCursor.getCount() != 0) {
                while (objCursor.moveToNext()) {
                    Log.d("getQuestion", String.valueOf(objCursor.getInt(1)));
                    getQuestion(objCursor.getInt(1));
                    _id = objCursor.getInt(1);
                    getTicketNumber(objCursor.getInt(1));
                }
            }
        }
    }

    public ArrayList<DbButtonClass> getHardQuestionsAnswers(int id) {
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        Cursor buttonCursor = objSqliteDatabase.rawQuery("select * from HardQuestions where _id =" + id, null);
        if (buttonCursor.getCount() != 0) {
            while (buttonCursor.moveToNext()) {
                dbButtonClassArrayList = getAnswers(buttonCursor.getInt(1));
            }
        }
        return dbButtonClassArrayList;
    }

    public int getId(int question_number, int category) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Questions where category = " + category, null);
        cursor.moveToPosition(question_number);
        return cursor.getInt(0);
    }

    public void getFinesQuestions(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from FinesQuestions where _id =" + (id + 1), null);
        while (cursor.moveToNext()) {
            question = cursor.getString(1);
            correctans = cursor.getInt(2);
        }
    }

    public ArrayList<DbButtonClass> getFinesAnswers(int id) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from FinesAnswers where question_id =" + (id + 1), null);
        while (cursor.moveToNext()) {
            String ans = cursor.getString(2);
            dbButtonClassArrayList.add(new DbButtonClass(ans));
        }
        return dbButtonClassArrayList;
    }

    public void getSignsQuestions(int id) {
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        if (objSqliteDatabase != null) {
            Cursor objCursor = objSqliteDatabase.rawQuery("select * from SignsQuestions where _id =" + id, null);
            if (objCursor.getCount() != 0) {
                while (objCursor.moveToNext()) {
                    sign_number = objCursor.getString(2);
                    correctans = objCursor.getInt(3);
                    explanation = objCursor.getString(4);
                }
            }
        }
    }

    public ArrayList<DbButtonClass> getSignsAnswers(int id) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from SignsAnswers where question_id =" + id, null);
        while (cursor.moveToNext()) {
            String ans = cursor.getString(2);
            dbButtonClassArrayList.add(new DbButtonClass(ans));
        }
        return dbButtonClassArrayList;
    }



    public static String getSign_number() {
        return sign_number;
    }

    public static int get_id() {
        return _id;
    }

    public static int getCorrectans() {
        return correctans;
    }

    public static String getExplanation() {
        return explanation;
    }

    public static int getCount() {
        return count;
    }

    public static int getBilet() {
        return bilet;
    }

    public static int getNumber() {
        return number;
    }

    public static String getQuestion() {
        return question;
    }

    public static int getTable_length() {
        return table_length;
    }
}