package com.xjtucsse.meetingassistant;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yzq.zxinglibrary.android.CaptureActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static com.xjtucsse.meetingassistant.MeetingInfo.getMeetingID;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton scanQR,addMeeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_meetings_ahead, R.id.navigation_meetings_now, R.id.navigation_meetings_before)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        Intent intent = new Intent(MainActivity.this, RemindService.class);
        startService(intent);
        scanQR= findViewById(R.id.scan_qr);
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,8453);
            }
        });
        addMeeting =findViewById(R.id.add_meeting);
        addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        dbHelper.getWritableDatabase();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8453 && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(com.yzq.zxinglibrary.common.Constant.CODED_CONTENT);
                String [] mtinfo=content.split("\\$,!\\$");
                if (!mtinfo[0].equals("MTAS"))
                {
                    Toast.makeText(MainActivity.this,"扫描会议码才能添加会议！\n扫码内容为"+content,Toast.LENGTH_LONG).show();
                    return;
                }
                String gettopic=mtinfo[1],getsttime=mtinfo[2],getedtime=mtinfo[3];
                String thismeetingid=getMeetingID(gettopic+getsttime+getedtime);
                DatabaseDAO mydb=new DatabaseDAO(MainActivity.this);
                if (mydb.query_items("meetingID=\""+thismeetingid+"\"")>0)
                {
                    Toast.makeText(MainActivity.this,"已存在相同会议，请检查！",Toast.LENGTH_LONG).show();
                    return;
                }
                mydb.insert(thismeetingid,gettopic,getsttime,getedtime,"");
                Toast.makeText(MainActivity.this,"添加成功！请刷新页面！",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
