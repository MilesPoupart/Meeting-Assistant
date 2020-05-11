package com.xjtucsse.meetingassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xjtucsse.meetingassistant.ui.MeetingAdapter;
import com.yzq.zxinglibrary.encode.CodeCreator;

public class MeetingActivity extends AppCompatActivity {
    FloatingActionButton saveNote,deleteNote;
    EditText ET;
    TextView thistopic,thistime;
    String note,topic,starttime,endtime,thismeetingid;
    ImageView IV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        topic=getIntent().getStringExtra("topic");
        starttime=getIntent().getStringExtra("starttime");
        endtime=getIntent().getStringExtra("endtime");
        saveNote=(FloatingActionButton) findViewById(R.id.save_note);
        deleteNote=(FloatingActionButton) findViewById(R.id.delete_note);
        thistopic=findViewById(R.id.this_meeting_topic);
        thistopic.setText(topic);
        thistopic.setSelected(true);
        thistime=findViewById(R.id.this_meeting_time);
        thistime.setText("开始时间 "+starttime+"\n结束时间 "+endtime);
        thismeetingid=MeetingInfo.getMeetingID(topic+starttime+endtime);
        note=query_and_get_note(thismeetingid);
        Log.d("TMT",topic+starttime+endtime+" "+thismeetingid);
        ET=findViewById(R.id.et);
        ET.setText(note);
        IV=findViewById(R.id.meeting_qr);
        String qrstring="MTAS"+"$,!$"+topic+"$,!$"+starttime+"$,!$"+endtime;
        Log.d("QRSTR",qrstring);
        Bitmap QR = CodeCreator.createQRCode(qrstring,100,100,null);
        IV.setImageBitmap(QR);
        OnClick onClick = new OnClick();
        saveNote.setOnClickListener(onClick);
        deleteNote.setOnClickListener(onClick);
    }
    public String query_and_get_note(String mtid) {
        DatabaseHelper dbHelper= new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="select note from "+ DatabaseInfo.TABLE_NAME+" where meetingID=\""+mtid+"\"";

        Cursor cursor =db.rawQuery(sql,null);
        cursor.moveToNext();
        String note=cursor.getString(cursor.getColumnIndex("note"));
        cursor.close();
        db.close();
        return note;
    }
    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()) {
                case R.id.save_note:
                    note=ET.getText().toString();
                    Intent intent=new Intent();
                    intent.putExtra("note",note);
                    intent.putExtra("topic",topic);
                    DatabaseDAO dao = new DatabaseDAO(MeetingActivity.this);
                    dao.updateNote(thismeetingid,ET.getText().toString());
                    Toast.makeText(MeetingActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                    break;

                case R.id.delete_note:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MeetingActivity.this);
                    builder.setTitle("清空记录").setMessage("确定清空当前的记录内容吗?").setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            DatabaseDAO dao = new DatabaseDAO(MeetingActivity.this);
                            dao.updateNote(thismeetingid,"");
                            Toast.makeText(MeetingActivity.this,"清除成功！",Toast.LENGTH_SHORT).show();
                            note=query_and_get_note(thismeetingid);

                            ET=findViewById(R.id.et);
                            ET.setText(note);
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    break;
            }
        }
    }
}
