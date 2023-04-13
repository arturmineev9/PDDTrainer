package com.example.autoschool11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.example.autoschool11.db.db_classes.IntensityClass;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    String question;
    static int cursorplace;
    static String explanation;
    Context context;


    public static final int DATABASE_VERSION = 12;
    public static final String DATABASE_NAME = "SQLite.db";


    // колонки таблицы для дневной статистики

    public static final String TABLE_NAME_1 = "DailyResults";
    public static final String COLUMN_ID_1 = "_id";
    public static final String COLUMN_DATE_1 = "date";
    public static final String COLUMN_RESULT_1 = "result";


    // колонки таблицы для избранных вопросов

    public static final String TABLE_NAME_2 = "Favourites";
    public static final String COLUMN_ID_2 = "_id";
    public static final String COLUMN_QUESTION_ID_2 = "id_question";


    // колонки таблицы для ошибок

    public static final String TABLE_NAME_3 = "Mistakes";
    public static final String COLUMN_ID_3 = "_id";
    public static final String COLUMN_QUESTION_ID_3 = "id_question";


    // колонки таблицы для статистики

    public static final String TABLE_NAME_4 = "Results";
    public static final String COLUMN_ID_4 = "_id";
    public static final String COLUMN_TICKET_4 = "ticket_number";
    public static final String COLUMN_RESULT_4 = "result";
    public static final String COLUMN_RESULT_THEMES_4 = "result_themes";
    public static final String COLUMN_RESULT_THEMES_MAX_4 = "result_themes_max";


    // колонки для режимов "Умная тренировка" и "Марафон"

    public static final String TABLE_NAME_5 = "Training";
    public static final String COLUMN_ID_5 = "_id";
    public static final String COLUMN_KNOWING_5 = "knowing_id";
    public static final String COLUMN_MARATHON_5 = "marathon";

    // колонки для статистики успешности


    public static final String TABLE_NAME_6 = "SuccessResults";
    public static final String COLUMN_CORRECT = "correct_count";
    public static final String COLUMN_INCORRECT = "incorrect_count";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // создание бд
        String daily_stat = "CREATE TABLE " + TABLE_NAME_1 +
                " (" + COLUMN_ID_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE_1 + " TEXT, " +
                COLUMN_RESULT_1 + " INTEGER);";

        String favourite = "CREATE TABLE " + TABLE_NAME_2 +
                " (" + COLUMN_ID_2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION_ID_2 + " INTEGER UNIQUE);";

        String mistakes = "CREATE TABLE " + TABLE_NAME_3 +
                " (" + COLUMN_ID_3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION_ID_3 + " INTEGER UNIQUE);";

        String stat = "CREATE TABLE " + TABLE_NAME_4 +
                " (" + COLUMN_ID_4 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TICKET_4 + " INTEGER, " +
                COLUMN_RESULT_4 + " INTEGER, " +
                COLUMN_RESULT_THEMES_4 + " INTEGER, " +
                COLUMN_RESULT_THEMES_MAX_4 + " INTEGER);";

        ContentValues cv = new ContentValues();

        String success = "CREATE TABLE " + TABLE_NAME_6 +
                " (" + COLUMN_CORRECT + " INTEGER, " +
                COLUMN_INCORRECT + " INTEGER);";

        db.execSQL(success);

        ContentValues cv1 = new ContentValues();
        cv1.put(COLUMN_CORRECT, 0);
        cv1.put(COLUMN_INCORRECT, 0);
        db.insert(TABLE_NAME_6, null, cv1);


        db.execSQL(stat);

        int[] amount = {28, 11, 6, 122, 38, 34, 9, 112, 22, 18, 40, 39, 120, 3, 10, 13, 9, 7, 19, 8, 1, 4, 3, 2, 26, 59, 20, 17};
        for (int i = 1; i < 41; i++) {

            cv.put(COLUMN_TICKET_4, i);
            cv.put(COLUMN_RESULT_4, 0);
            db.insert(TABLE_NAME_4, null, cv);
        }
        /*for (int i = 1; i < 29; i++) {
            cv.put(COLUMN_RESULT_THEMES_4, 0);
            cv.put(COLUMN_RESULT_THEMES_MAX_4, amount[i - 1]);
            db.update(TABLE_NAME_4, cv, COLUMN_ID_4 + "=" + i, null);
        }*/

        String training = "CREATE TABLE " + TABLE_NAME_5 +
                " (" + COLUMN_ID_5 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KNOWING_5 + " INTEGER, " +
                COLUMN_MARATHON_5 + " INTEGER);";
        db.execSQL(training);
        for (int i = 1; i < 801; i++) {
            ContentValues cv2 = new ContentValues();
            cv2.put(COLUMN_ID_5, i);
            cv2.put(COLUMN_KNOWING_5, 1);
            cv2.put(COLUMN_MARATHON_5, 0);
            db.insert(TABLE_NAME_5, null, cv2);
        }

        db.execSQL(daily_stat);
        db.execSQL(favourite);
        db.execSQL(mistakes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_6);
        onCreate(db);
    }


    //                                                                                 МЕТОДЫ ТАБЛИЦЫ DailyStatistics


    public void insertDayResults(String date, int results) { // метод, вносящий результаты в таблицу DailyStatistics
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase dbread = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        Cursor cursor = dbread.rawQuery("select * from DailyResults", null);
        cursor.moveToLast();
        if (cursor.getPosition() == -1) {
            cv.put(COLUMN_DATE_1, date);
            cv.put(COLUMN_RESULT_1, results);
            db.insert(TABLE_NAME_1, null, cv);
        } else if (!date.equals(cursor.getString(1))) {
            cv.put(COLUMN_DATE_1, date);
            cv.put(COLUMN_RESULT_1, results);
            db.insert(TABLE_NAME_1, null, cv);
        } else {
            cv.put(COLUMN_RESULT_1, cursor.getInt(2) + results);
            db.update(TABLE_NAME_1, cv, "date = ?", new String[]{date});
        }
    }


    public ArrayList<IntensityClass> getStatisticsData() { // метод, получающий результаты из таблицы DailyStatistics
        ArrayList<IntensityClass> intensity = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("select * from DailyResults", null);
            if (cursor.getPosition() != 0) {
                while (cursor.moveToNext()) {
                    intensity.add(new IntensityClass(cursor.getString(1), cursor.getInt(2)));
                }
            }
        }
        return intensity;
    }

    public void restartDayStatisticsDB() { //метод, сбрасывающий таблицу DailyStatistics
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_1, null, null);
    }


    //                                                                                 МЕТОДЫ ТАБЛИЦЫ Favourites


    public void insertFavourite(int question_id) { // метод, вносящий id вопроса в таблицу
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION_ID_2, question_id);
        db.insert(TABLE_NAME_2, null, cv);
    }

    public void deleteAllFavourite(ArrayList<Integer> arrayList) { // метод, удаляющий id вопроса из таблицы
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < arrayList.size(); i++) {
            String where = COLUMN_ID_2 + "=" + arrayList.get(i);
            db.delete(TABLE_NAME_2, where, null);
        }
    }

    public void deleteFavourite(int id_question) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_2, COLUMN_QUESTION_ID_2 + "=" + id_question, null);
    }

    public boolean isInFavourites(int id_question) { // метод, проверяющий наличие id в таблице
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites where id_question = " + id_question, null);
        return cursor.getCount() != 0;
    }

    public String getFavouriteQuestions(int id, Context context) { // получение вопроса по id
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        PDD_DataBaseHelper dataBaseHelper = new PDD_DataBaseHelper(context);
        Cursor cursor = db.rawQuery("select * from Favourites where _id =" + id, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                question = dataBaseHelper.getQuestion(cursor.getInt(1));
                explanation = PDD_DataBaseHelper.getExplanation();
                dataBaseHelper.getTicketNumber(cursor.getInt(1));
            }
        }
        return question;
    }

    public ArrayList<DbButtonClass> getFavouriteAnswers(int id, Context context) { // получение вариантов ответов по id
        PDD_DataBaseHelper dataBaseHelper = new PDD_DataBaseHelper(context);
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

    public int getTableLength() { // получение длины таблицы
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites", null);
        return cursor.getCount();
    }

    public int getId(int question_number) { // получение id по номеру вопроса
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites", null);
        cursor.moveToPosition(question_number);
        return cursor.getInt(0);
    }

    public int getQuestionId(int id) { // получение id
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites where _id = " + id, null);
        cursor.moveToNext();
        return cursor.getInt(1);
    }

    public int getPositionId(int position) { // получение id по позиции
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Favourites", null);
        cursor.moveToPosition(position);
        cursorplace = position;
        return cursor.getInt(0);
    }

    public static String getExplanation() {
        return explanation;
    } // получение объяснения вопроса

    public static int getCursorplace() {
        return cursorplace;
    }


    //                                                                                 МЕТОДЫ ТАБЛИЦЫ Mistakes

    public void insertMistake(int question_id) { // внесение id вопроса с ошибкой в таблицу
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION_ID_3, question_id);
        db.insert(TABLE_NAME_3, null, cv);
    }

    public void deleteMistakes(ArrayList<Integer> arrayList) { // удаление id вопроса с ошибкой из таблицы
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < arrayList.size(); i++) {
            String where = COLUMN_ID_3 + "=" + arrayList.get(i);
            db.delete(TABLE_NAME_3, where, null);
        }

    }

    public String getMistakeQuestions(int id, Context context) { // получение вопроса по id
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        PDD_DataBaseHelper dataBaseHelper = new PDD_DataBaseHelper(context);
        Cursor cursor = db.rawQuery("select * from Mistakes where _id =" + id, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                question = dataBaseHelper.getQuestion(cursor.getInt(1));
                explanation = PDD_DataBaseHelper.getExplanation();
                dataBaseHelper.getTicketNumber(cursor.getInt(1));
            }
        }
        return question;
    }

    public ArrayList<DbButtonClass> getMistakesAnswers(int id, Context context) { // получение вариантов ответов по id
        PDD_DataBaseHelper dataBaseHelper = new PDD_DataBaseHelper(context);
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        ArrayList<DbButtonClass> dbButtonClassArrayList = new ArrayList<>();
        Cursor buttonCursor = objSqliteDatabase.rawQuery("select * from Mistakes where _id =" + id, null);
        if (buttonCursor.getCount() != 0) {
            while (buttonCursor.moveToNext()) {
                dbButtonClassArrayList = dataBaseHelper.getAnswers(buttonCursor.getInt(1));
            }
        }
        return dbButtonClassArrayList;
    }

    public int getMistakesTableLength() { // получение длины таблицы
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Mistakes", null);
        return cursor.getCount();
    }


    public int getMistakesId(int question_number) { // получение id
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Mistakes", null);
        cursor.moveToPosition(question_number);
        return cursor.getInt(0);
    }

    public int getMistakesPositionId(int position) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Mistakes", null);
        cursor.moveToPosition(position);
        cursorplace = position;
        return cursor.getInt(0);
    }

    public void restartMistakes() { // сброс таблицы
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_3, null, null);
    }


    //                                                                                 МЕТОДЫ ТАБЛИЦЫ Results

    public void insertResults(int ticket_number, int result) { // внесение результатов в таблицу
        SQLiteDatabase dbread = getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RESULT_4, result);
        String where = COLUMN_TICKET_4 + "=" + ticket_number;
        db.update(TABLE_NAME_4, cv, where, null);
    }

    public void insertThemeResults(int theme_number, int result) { // внесение результатов по темам в таблицу
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RESULT_THEMES_4, result);
        db.update(TABLE_NAME_4, cv, COLUMN_ID_4 + "=" + theme_number, null);
    }

    public String[] getResults() { // получение результатов
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

    public String[] getThemesResults() { // получение результатов по темам
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

    public int getAllResults() { // сумма пройденных вопросов
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

    public int get20Tickets() { // получение кол-ва билетов, сделанных идеально
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

    public void restartStatisticsDB() { // сброс таблицы
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RESULT_4, 0);
        cv.put(COLUMN_RESULT_THEMES_4, 0);
        db.update(TABLE_NAME_4, cv, null, null);
    }

    public int getFullThemesCount() { // получение кол-ва идеально сделанных тем
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


    //                                                                              МЕТОДЫ ТАБЛИЦЫ SuccessResults


    public void increaseCorrectAnswers() {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase dbread = getReadableDatabase();
        Cursor cursor = dbread.rawQuery("select * from SuccessResults", null);
        cursor.moveToNext();
        cv.put(COLUMN_CORRECT, cursor.getInt(0) + 1);
        db.update(TABLE_NAME_6, cv, null, null);
    }

    public void increaseIncorrectAnswers() {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase dbread = getReadableDatabase();
        Cursor cursor = dbread.rawQuery("select * from SuccessResults", null);
        cursor.moveToNext();
        cv.put(COLUMN_INCORRECT, cursor.getInt(1) + 1);
        db.update(TABLE_NAME_6, cv, null, null);
    }

    public ArrayList<PieEntry> getPieChartStatistics() {
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("select * from SuccessResults", null);
            cursor.moveToNext();
            pieEntryArrayList.add(new PieEntry(cursor.getInt(0), "Правильные ответы"));
            pieEntryArrayList.add(new PieEntry(cursor.getInt(1), "Неправильные ответы"));

        }
        return pieEntryArrayList;
    }

    public boolean isTableEmpty(){
        SQLiteDatabase db = getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("select * from SuccessResults", null);
            cursor.moveToNext();
            return cursor.getInt(0) == 0 || cursor.getInt(1) == 0;
        }
        return false;
    }

    public void restartSuccessTable(){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CORRECT, 0);
        cv.put(COLUMN_INCORRECT, 0);
        db.update(TABLE_NAME_6, cv, null,null);
    }

    //                                                                                 МЕТОДЫ ТАБЛИЦЫ Training


    public String getCountQuestions(int knowing_id) { // получение кол-ва вопросов из определенной категории
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where knowing_id = " + knowing_id, null);
        return Integer.toString(cursor.getCount());
    }

    public void increaseKnowingID(int id_question) { // повышение вопроса по знанию
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where _id = " + id_question, null);
        cursor.moveToNext();
        ContentValues cv = new ContentValues();
        if (cursor.getInt(1) != 4) {
            cv.put(COLUMN_KNOWING_5, cursor.getInt(1) + 1);
            db.update(TABLE_NAME_5, cv, COLUMN_ID_5 + "=" + id_question, null);
        }
    }

    public void decreaseKnowingID(int id_question) { // понижение вопроса по знанию
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where _id = " + id_question, null);
        cursor.moveToNext();
        ContentValues cv = new ContentValues();
        if (cursor.getInt(1) != 1) {
            cv.put(COLUMN_KNOWING_5, cursor.getInt(1) - 1);
            db.update(TABLE_NAME_5, cv, COLUMN_ID_5 + "=" + id_question, null);
        }
    }

    public void restartTrainingData() { // сброс таблицы
        SQLiteDatabase db = getWritableDatabase();
        for (int i = 1; i < 801; i++) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ID_5, i);
            cv.put(COLUMN_KNOWING_5, 1);
            cv.put(COLUMN_MARATHON_5, 0);
            db.update(TABLE_NAME_5, cv, COLUMN_ID_5 + "=" + i, null);
        }
    }

    public String getTrainingQuestions(int knowing_id, int number_id, Context context) { // получение вопроса
        PDD_DataBaseHelper dataBaseHelper = new PDD_DataBaseHelper(context);
        SQLiteDatabase objSqliteDatabase = getReadableDatabase();
        if (objSqliteDatabase != null) {
            Cursor objCursor = objSqliteDatabase.rawQuery("select * from Training where knowing_id =" + knowing_id + " and _id = " + number_id, null);
            if (objCursor.getCount() != 0) {
                objCursor.moveToNext();
                question = dataBaseHelper.getQuestion(number_id);
            }
        }
        return question;
    }

    public ArrayList<DbButtonClass> getTrainingAnswers(int id, Context context) { // получение вариантов ответов
        PDD_DataBaseHelper dataBaseHelper = new PDD_DataBaseHelper(context);
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

    public int getTrainingId(int question_number, int knowing) { // получение id
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where knowing_id = " + knowing, null);
        cursor.moveToPosition(question_number - 1);
        return cursor.getInt(0);
    }

    public int getTrainingTableLength(int knowing_id) { // получение длины таблицы
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where knowing_id = " + knowing_id, null);
        return cursor.getCount();
    }

    public void increaseArrayId(ArrayList<Integer> arrayList) { // повышение знания массива вопросов
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db_read = getReadableDatabase();
        for (int i = 0; i < arrayList.size(); i++) {
            Cursor cursor = db_read.rawQuery("select * from Training where _id = " + arrayList.get(i), null);
            Log.d("arraylist", String.valueOf(arrayList.get(i)));
            cursor.moveToNext();
            ContentValues cv = new ContentValues();
            if (cursor.getInt(1) != 4) {
                cv.put(COLUMN_KNOWING_5, cursor.getInt(1) + 1);
                db.update(TABLE_NAME_5, cv, COLUMN_ID_5 + "=" + arrayList.get(i), null);
            }
        }
    }

    public void decreaseArrayId(ArrayList<Integer> arrayList) { // понижение знания массива вопросов
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db_read = getReadableDatabase();
        for (int i = 0; i < arrayList.size(); i++) {
            Cursor cursor = db_read.rawQuery("select * from Training where _id = " + arrayList.get(i), null);
            ContentValues cv = new ContentValues();
            cursor.moveToNext();
            if (cursor.getInt(0) != 1) {
                cv.put(COLUMN_KNOWING_5, cursor.getInt(0) - 1);
                db.update(TABLE_NAME_5, cv, COLUMN_ID_5 + "=" + arrayList.get(i), null);
            }
        }
    }

    public void setMarathonProgress(int id, int answer) { // внесение ответа в марафоне
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where _id = " + id, null);
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MARATHON_5, answer);
        db.update(TABLE_NAME_5, cv, COLUMN_ID_5 + "=" + id, null);
    }

    public int getMarathonId(int id) { // получение id
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training where _id = " + id, null);
        cursor.moveToNext();
        return cursor.getInt(2);
    }

    public int getKnowingCount() {
        int res = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Training", null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(1) != 1) {
                res++;
            }
        }
        return res;
    }
}



