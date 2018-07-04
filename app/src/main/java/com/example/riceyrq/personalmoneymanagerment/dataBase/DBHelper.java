package com.example.riceyrq.personalmoneymanagerment.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String databaseName = "money.db";
    private static final int databaseVersion = 1;

    public DBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists user" +
                "(username varchar,password varchar,pasquestion varchar,pasanswer varchar)");
        db.execSQL("create table if not exists message" +
                "(username varchar,inorout integer,value real,time integer,other varchar,kind varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}