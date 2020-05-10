package com.xjtucsse.meetingassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xjtucsse.meetingassistant.ui.MeetingAdapter;

public class MeetingActivity extends AppCompatActivity {
    FloatingActionButton saveNote,deleteNote;
    EditText ET;
    TextView thistopic,thistime;
    String note,topic,starttime,endtime,thismeetingid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        //note = getIntent().getStringExtra("note");
        topic=getIntent().getStringExtra("topic");
        starttime=getIntent().getStringExtra("starttime");
        endtime=getIntent().getStringExtra("endtime");
        saveNote=(FloatingActionButton) findViewById(R.id.save_note);
        deleteNote=(FloatingActionButton) findViewById(R.id.delete_note);
        thistopic=findViewById(R.id.this_meeting_topic);
        thistopic.setText(topic);
        thistime=findViewById(R.id.this_meeting_time);
        thistime.setText("开始时间 "+starttime+"\n结束时间 "+endtime);
        thismeetingid=MeetingInfo.getMeetingID(topic+starttime+endtime);
        note=query_and_get_note(thismeetingid);
        Log.d("TMT",topic+starttime+endtime+" "+thismeetingid);
        ET=findViewById(R.id.et);
        ET.setText(note);
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note=ET.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("note",note);
                intent.putExtra("topic",topic);
                DatabaseDAO dao = new DatabaseDAO(MeetingActivity.this);
                dao.updateNote(thismeetingid,ET.getText().toString());
                Toast.makeText(MeetingActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseDAO dao = new DatabaseDAO(MeetingActivity.this);
                dao.delete(thismeetingid);
                Toast.makeText(MeetingActivity.this,"删除成功！请刷新页面！",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public String query_and_get_note(String mtid) {
        DatabaseHelper dbHelper= new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="select note from "+ DatabaseInfo.TABLE_NAME+" where meetingID=\""+mtid+"\"";
        //Log.d("QAGN",sql);

        Cursor cursor =db.rawQuery(sql,null);
        cursor.moveToNext();
        String note=cursor.getString(cursor.getColumnIndex("note"));
        cursor.close();
        db.close();
        return note;
    }
}
