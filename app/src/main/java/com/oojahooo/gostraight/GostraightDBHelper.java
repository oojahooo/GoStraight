package com.oojahooo.gostraight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class GostraightDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gostraight.db";
    private static final int DATABASE_VERSION = 1;

    public GostraightDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GostraightDBCtruct.SQL_CREATE_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS data");
        onCreate(db);
    }

}
