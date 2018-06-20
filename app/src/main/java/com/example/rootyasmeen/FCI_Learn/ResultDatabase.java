package com.example.rootyasmeen.FCI_Learn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by fci on 11/03/17.
 */

public class ResultDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FCI_Learn";

    private static final String USER_TABLE_NAME = "User";

    private static final String UID = "id";

    private static final String EMAIL = "email";

    private static final String LOGIN = "login";

    private static final String RESULT_TABLE_NAME = "Result";

    private static final String SID = "id";

    private static final String QUESTION = "question";

    private static final String ANSWER = "answer";

    private static final String RESULT = "result";

    private static final int DATABASE_VERSION = 2;
    Context cont;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + USER_TABLE_NAME +
            "( " + UID + " integer primary key , " + EMAIL + " varchar(255) not null, " + LOGIN + " varchar(2) not null);";

    private static final String ANSWER_CREATE = "create table " + RESULT_TABLE_NAME +
            "( " + SID + " integer primary key , " + QUESTION + " varchar(255) not null, " + ANSWER + " varchar(255) not null, " + RESULT + " varchar(255) not null);";
    // Database Deletion
    private static final String DATABASE_DROP = "drop table if exists " + USER_TABLE_NAME + ";";

    private static final String ANSWER_DROP = "drop table if exists " + RESULT_TABLE_NAME + ";";

    public ResultDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(ANSWER_CREATE);
            db.execSQL("insert into " + USER_TABLE_NAME + " ( " + UID + ", " + EMAIL + ", " + LOGIN + ") values ( '1', 'e', '0');");
            Toast.makeText(cont, "database created", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(cont, "database doesn't created " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DATABASE_DROP);
            db.execSQL(ANSWER_DROP);
            onCreate(db);
            Toast.makeText(cont, "database upgraded", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(cont, "database doesn't upgraded " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // For Login & Register
    public boolean InsertData(String email, String state) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL, email);
        contentValues.put(LOGIN, state);
        long result = sqLiteDatabase.insert(USER_TABLE_NAME, null, contentValues);

        return result == -1 ? false : true;
    }

    public Cursor ShowData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + USER_TABLE_NAME + " ;", null);
        return cursor;
    }

    public Cursor ShowAnswer() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + RESULT_TABLE_NAME + " ;", null);
        return cursor;
    }

    public boolean UpdateData(String id, String state) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UID, id);
        contentValues.put(LOGIN, state);
        sqLiteDatabase.update(USER_TABLE_NAME, contentValues, "id = " + Integer.parseInt(id), null);

        return true;
    }

    public int DeleteData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(USER_TABLE_NAME, "ID = ?", new String[]{id});
    }

    // For Result
    public boolean InsertAnswer(String question, String answer, String degree) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION, question);
        contentValues.put(ANSWER, answer);
        contentValues.put(RESULT, degree);
        long result = sqLiteDatabase.insert(RESULT_TABLE_NAME, null, contentValues);

        return result == -1 ? false : true;
    }

    public Cursor ShowQuestionANDAnswer() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + RESULT_TABLE_NAME + " ;", null);
        return cursor;
    }

    public void DeleteTableAnswer() {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.execSQL(ANSWER_DROP);
            Toast.makeText(cont,"Answers Rest Done!",Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(ANSWER_CREATE);
            Toast.makeText(cont,"Your Sheet Is Ready For Next Exam!",Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(cont, "database doesn't upgraded " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
