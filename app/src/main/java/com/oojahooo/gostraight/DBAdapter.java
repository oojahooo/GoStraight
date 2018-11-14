package com.oojahooo.gostraight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    public static final String KEY_CATEGORY = "category";
    public static final String KEY_SECTION = "section";
    public static final String KEY_DETAIL = "detail";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "DBAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS FACILITY (_id integer primary key autoincrement, category integer, section integer, detail text not null);";
    private static final String DATABASE_NAME = "gostraight.db";
    private static final String DATABASE_TABLE = "FACILITY";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading db from version" + oldVersion + " to" +
                    newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS data");
            onCreate(db);
        }
    }

    public DBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DBAdapter open() throws SQLException{
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDbHelper.close();
    }

    public long createFacility(int category, int section, String detail){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CATEGORY, category);
        initialValues.put(KEY_SECTION, section);
        initialValues.put(KEY_DETAIL, detail);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteFacility(long rowID){
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowID, null) > 0;
    }

    public Cursor fetchAllFacilities(){
        return mDb.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_CATEGORY, KEY_SECTION, KEY_DETAIL}, null, null, null, null, null);
    }

    public Cursor fetchFacility(long rowID) throws SQLException{
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[]{KEY_ROWID, KEY_CATEGORY, KEY_SECTION, KEY_DETAIL}, KEY_ROWID + "=" + rowID, null, null, null, null, null);
        if(mCursor != null)
            mCursor.moveToFirst();
        return mCursor;
    }

    public boolean updateBook(long rowID, int category, int section, String detail){
        ContentValues args = new ContentValues();
        args.put(KEY_CATEGORY, category);
        args.put(KEY_SECTION, section);
        args.put(KEY_DETAIL, detail);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowID, null) > 0;
    }

}
