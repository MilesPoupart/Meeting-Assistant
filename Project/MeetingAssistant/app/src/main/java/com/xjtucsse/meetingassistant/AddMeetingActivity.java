package com.xjtucsse.meetingassistant;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.xjtucsse.meetingassistant.MeetingInfo.getMeetingID;

public class AddMeetingActivity extends AppCompatActivity {
    private Button addMeeting;
    private EditText meetTopic,meetHours,meetMins;
    private CalendarView meetDate;
    private TimePicker meetStartTime;
    private String newMeetTopic;
    private Calendar newMeetDateTimeStart=Calendar.getInstance(),newMeetDateTimeEnd=Calendar.getInstance();
    private int meetDateYear=newMeetDateTimeStart.get(Calendar.YEAR),
            meetDateMonth=newMeetDateTimeStart.get(Calendar.MONTH),
            meetDateDay=newMeetDateTimeStart.get(Calendar.DAY_OF_MONTH),
            meetDateHour=newMeetDateTimeStart.get(Calendar.HOUR_OF_DAY),
            meetDateMin=newMeetDateTimeStart.get(Calendar.MINUTE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        addMeeting=(Button) findViewById(R.id.add_submit);
        meetTopic = (EditText) findViewById(R.id.con_topic);
        meetDate = (CalendarView) findViewById(R.id.con_date);
        meetStartTime = (TimePicker) findViewById(R.id.con_time);
        meetHours = (EditText) findViewById(R.id.con_hour);
        meetMins = (EditText) findViewById(R.id.con_min);
        meetDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                meetDateYear=year;
                meetDateMonth=month;
                meetDateDay=dayOfMonth;
            }
        });
        meetStartTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                meetDateHour=hourOfDay;
                meetDateMin=minute;
            }
        });
        addMeeting.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                newMeetTopic=meetTopic.getText().toString();
                String getHour=meetHours.getText().toString(),getMin=meetMins.getText().toString();
                if (newMeetTopic.equals("")||getHour.equals("")||getMin.equals("")) {
                    Toast.makeText(AddMeetingActivity.this,"请完整填写会议主题、持续时间！",Toast.LENGTH_SHORT).show();
                    return;
                }
                int duringHour=Integer.parseInt(getHour),duringMin=Integer.parseInt(getMin);
                if(duringMin>=60){
                    Toast.makeText(AddMeetingActivity.this,"持续时间中分钟范围为0-59",Toast.LENGTH_SHORT).show();
                    return;
                }
                newMeetDateTimeStart.set(meetDateYear,meetDateMonth,meetDateDay,meetDateHour,meetDateMin);
                newMeetDateTimeEnd.set(meetDateYear,meetDateMonth,meetDateDay,meetDateHour,meetDateMin);
                newMeetDateTimeEnd.add(Calendar.HOUR,duringHour);
                newMeetDateTimeEnd.add(Calendar.MINUTE,duringMin);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String startTimeStr = sdf.format(newMeetDateTimeStart.getTime());
                String endTimeStr = sdf.format(newMeetDateTimeEnd.getTime());
                String thismeetingid=getMeetingID(newMeetTopic+startTimeStr+endTimeStr);
                DatabaseDAO mydb=new DatabaseDAO(AddMeetingActivity.this);
                if (mydb.query_items("meetingID=\""+thismeetingid+"\"")>0)
                {
                    Toast.makeText(AddMeetingActivity.this,"已存在相同会议，请检查！",Toast.LENGTH_LONG).show();
                    return;
                }
                mydb.insert(thismeetingid,newMeetTopic,startTimeStr,endTimeStr,"");
                Toast.makeText(AddMeetingActivity.this,"添加成功！请刷新页面！\n会议主题："+newMeetTopic+"\n会议时间：\n"+startTimeStr+"到"+endTimeStr,Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
