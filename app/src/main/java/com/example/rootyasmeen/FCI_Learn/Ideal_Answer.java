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

public class Ideal_Answer extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Question_Bank_Ideal_Answer";

    private static final String CHOICE_TABLE_NAME = "Choice";

    private static final String TF_TABLE_NAME = "tf";

    private static final String ASSAY_TABLE_NAME = "assay";

    private static final String UID = "id";

    private static final String QUESTION = "question";

    private static final String ANS1 = "ans1";

    private static final int DATABASE_VERSION = 1;
    Context cont;

    // Database creation sql statement
    private static final String CHOICE_DATABASE_CREATE = "create table " +CHOICE_TABLE_NAME +
            "( "+UID+" integer primary key , "
            +QUESTION+" varchar(255) not null , "
            +ANS1+" varchar(255) not null ); ";

    private static final String TF_DATABASE_CREATE = "create table " +TF_TABLE_NAME +
            "( "+UID+" integer primary key , "
            +QUESTION+" varchar(255) not null , "
            +ANS1+" varchar(255) not null ); ";

    private static final String ASSAY_DATABASE_CREATE = "create table " +ASSAY_TABLE_NAME +
            "( "+UID+" integer primary key , "
            +QUESTION+" varchar(255) not null , "
            +ANS1+" varchar(255) not null ); ";

    // Database Deletion
    private static final String CHOICE_DATABASE_DROP = "drop table if exists "+CHOICE_TABLE_NAME+";";
    private static final String TF_DATABASE_DROP = "drop table if exists "+TF_TABLE_NAME+";";
    private static final String ASSAY_DATABASE_DROP = "drop table if exists "+ASSAY_TABLE_NAME+";";

    public Ideal_Answer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CHOICE_DATABASE_CREATE);
            db.execSQL(TF_DATABASE_CREATE);
            db.execSQL(ASSAY_DATABASE_CREATE);
        }catch (SQLException e)
        {
            Toast.makeText(cont,"database doesn't created " +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(CHOICE_DATABASE_DROP);
            db.execSQL(TF_DATABASE_DROP);
            db.execSQL(ASSAY_DATABASE_DROP);
            onCreate(db);
            Toast.makeText(cont,"database upgraded", Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(cont,"database doesn't upgraded " +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean DropTable(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            sqLiteDatabase.execSQL(CHOICE_DATABASE_DROP);
            sqLiteDatabase.execSQL(TF_DATABASE_DROP);
            sqLiteDatabase.execSQL(ASSAY_DATABASE_DROP);
            return true;
        }catch (Exception e){
            Toast.makeText(cont,e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean InsertChoiceAnswer (String question, String ans1)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION,question);
        contentValues.put(ANS1,ans1);
        long result = sqLiteDatabase.insert(CHOICE_TABLE_NAME,null,contentValues);

        return result==-1?false:true;
    }

    public boolean InsertTfAnswer (String question, String ans1)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION,question);
        contentValues.put(ANS1,ans1);
        long result = sqLiteDatabase.insert(TF_TABLE_NAME,null,contentValues);

        return result==-1?false:true;
    }

    public boolean InsertAssayAnswer (String question, String ans1)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION,question);
        contentValues.put(ANS1,ans1);
        long result = sqLiteDatabase.insert(ASSAY_TABLE_NAME,null,contentValues);

        return result==-1?false:true;
    }

    public Cursor ShowChoiceData ()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+CHOICE_TABLE_NAME+" ;",null);
        return cursor;
    }
    public Cursor ShowTFData ()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TF_TABLE_NAME+" ;",null);
        return cursor;
    }public Cursor ShowAssayData ()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+ASSAY_TABLE_NAME+" ;",null);
        return cursor;
    }

}
