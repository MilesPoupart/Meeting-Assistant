package com.xjtucsse.meetingassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MeetingActivity extends AppCompatActivity {
    FloatingActionButton saveNote,deleteNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        saveNote=(FloatingActionButton) findViewById(R.id.save_note);
        deleteNote=(FloatingActionButton) findViewById(R.id.delete_note);
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MeetingActivity.this,"正在保存（假）...",Toast.LENGTH_SHORT).show();
            }
        });
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MeetingActivity.this,"正在删除（假）...",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
