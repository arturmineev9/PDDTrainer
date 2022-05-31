package com.example.autoschool11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;

import com.example.autoschool11.MainActivity;
import com.example.autoschool11.R;
import com.example.autoschool11.ui.tickets.Ticket1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "russia_pdd.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 21;
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


    public DataBaseHelper(Context context) {
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
        /*for(int i = 1; i < 801; i++){
            int k = 0;
            Cursor cursor = db.rawQuery("select * from Questions where _id = " + i, null);
            ContentValues cv = new ContentValues();
            cursor.moveToNext();
            String oldexp = cursor.getString(3);
            char[] b = oldexp.toCharArray();
            StringBuilder newstr = new StringBuilder();
            for (int j = 0; j < b.length; j++) {
                if (b[j] == '<') {
                    k = 1;
                } else if (k != 1) {
                    newstr.append(b[j]);
                }
                if (b[j] == '>'){
                    k = 0;
                }
            }
            cv.put("explanation", String.valueOf(newstr));
            db.update("Questions", cv, "_id =" + i, null);
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    public String getAllData(int id) {
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
            }
        }
        return question;
    }

    public void insertResults(int ticket_number, int result) {
        Log.d("insertResults:", "ticket_number" + ticket_number + " result " + result);
        SQLiteDatabase objSqliteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ticket_number", ticket_number);
        contentValues.put("result", result);
        String where = "ticket_number = " + ticket_number;
        objSqliteDatabase.insert("Results", null, contentValues);
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String cd = dateFormat.format(date);
        return cd;
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
                    Log.d("getAllData", String.valueOf(objCursor.getInt(1)));
                    getAllData(objCursor.getInt(1));
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

    public int getPositionId(int position) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Questions", null);
        cursor.moveToPosition(position);
        return cursor.getInt(0);
    }

    /*public void changeExplanation(){
        SQLiteDatabase database = getWritableDatabase();
        SQLiteDatabase database1 = getReadableDatabase();
        for(int i = 1; i < 801; i++){
            int k = 0;
            Cursor cursor = database1.rawQuery("select * from Questions where _id = " + i, null);
            ContentValues cv = new ContentValues();
            cursor.moveToNext();
            String oldexp = cursor.getString(3);
            char[] b = oldexp.toCharArray();
            StringBuilder newstr = new StringBuilder();
            for (int j = 0; j < b.length; j++) {
                if (b[j] == '<') {
                    k = 1;
                } else if (k != 1) {
                    newstr.append(b[j]);
                }
                if (b[j] == '>'){
                    k = 0;
                }
            }
            cv.put("explanation", String.valueOf(newstr));
            database.update("Questions", cv, "_id =" + i, null);
        }
    }*/


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

    public ArrayList<Integer> getCategoryId(int category){
        ArrayList<Integer> arrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Questions where category = " + category, null);
        while (cursor.moveToNext()){
            arrayList.add(cursor.getInt(0));
        }
        return arrayList;
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