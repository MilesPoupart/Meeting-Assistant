package com.xjtucsse.meetingassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;



public class DatabaseHelper extends SQLiteOpenHelper {
    public static String TAG="DatabaseHelper";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseInfo.DB_NAME, null, DatabaseInfo.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+DatabaseInfo.TABLE_NAME+"(meetingID varchar PRIMARY KEY,topic varchar,startTime varchar(30),endTime varchar(30),note varchar)";
        db.execSQL(sql);
        Log.d(TAG,"创建数据库");
        Log.d(TAG,sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
