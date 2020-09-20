package com.dilmurodjonov_abbos_daf_4course.myaccounts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class DBHelper extends SQLiteOpenHelper {

    public  DBHelper(Context context){
        super(context,"myDb",null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями

        db.execSQL("create table if not exists accounts ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "password text" + ");");

        db.execSQL("create table if not exists sites("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "login text,"
                + "password text,"
                + "comment text, account_id integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table accounts");
        db.execSQL("drop table sites");

        db.execSQL("create table if not exists accounts ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "password text" + ");");

        db.execSQL("create table if not exists sites("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "login text,"
                + "password text,"
                + "comment text, account_id integer" + ");");

    }

}
