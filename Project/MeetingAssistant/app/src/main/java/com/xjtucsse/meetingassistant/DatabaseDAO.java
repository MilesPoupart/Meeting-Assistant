package com.xjtucsse.meetingassistant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseDAO {
    private final DatabaseHelper dbHelper;

    public DatabaseDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    public void insert(String meetid,String meettopic,String meetsttime,String meetedtime,String meetnote){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String sql="insert into "+ DatabaseInfo.TABLE_NAME+"(meetingID,topic,startTime,endTime,note) values(?,?,?,?,?)";
        db.execSQL(sql,new Object[]{meetid,meettopic,meetsttime,meetedtime,meetnote});
        db.close();
    }
    public void delete(String meetid){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String sql="delete from "+ DatabaseInfo.TABLE_NAME+" where meetingID=?";
        db.execSQL(sql,new Object[]{meetid});
        db.close();
    }
    public void updateNote(String meetid,String meetnote){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String sql="update "+ DatabaseInfo.TABLE_NAME+" set note=? where meetingID=?";
        db.execSQL(sql,new Object[]{meetnote,meetid});
        db.close();
    }
    public void query(String condition){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String sql="select * from "+ DatabaseInfo.TABLE_NAME+" where "+condition;
        Cursor cursor =db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String meetingid=cursor.getString(cursor.getColumnIndex("meetingID"));
            String topic=cursor.getString(cursor.getColumnIndex("topic"));
            String sttime=cursor.getString(cursor.getColumnIndex("startTime"));
            String edtime=cursor.getString(cursor.getColumnIndex("endTime"));
            String note=cursor.getString(cursor.getColumnIndex("note"));
            Log.d("DAO",meetingid+" "+topic+" "+note+" "+sttime+" "+edtime);
        }
        cursor.close();
        db.close();
    }
}
