package com.xjtucsse.meetingassistant.ui.MeetingsBefore;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xjtucsse.meetingassistant.DatabaseHelper;
import com.xjtucsse.meetingassistant.DatabaseInfo;
import com.xjtucsse.meetingassistant.MeetingInfo;
import com.xjtucsse.meetingassistant.R;
import com.xjtucsse.meetingassistant.ui.MeetingAdapter;
import com.xjtucsse.meetingassistant.ui.MeetingsAhead.MeetingsAheadFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MeetingsBeforeFragment extends Fragment {

    private MeetingsBeforeViewModel meetingsBeforeViewModel;
    private RecyclerView RV1;
    private List<MeetingInfo> list=new ArrayList<MeetingInfo>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meetingsBeforeViewModel =
                ViewModelProviders.of(this).get(MeetingsBeforeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_meetings_ahead, container, false);
        RV1 = view.findViewById(R.id.rv1);
        RV1.addItemDecoration(new MeetingsAheadFragment.SpacesItemDecoration(8));

        Calendar NowTime=Calendar.getInstance();
        String nowTime=sdf.format(NowTime.getTime());

        query_and_add("endTime<\""+nowTime+"\" order by startTime");
        RV1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        RV1.setAdapter(new MeetingAdapter(getActivity(),list));
        RV1.addItemDecoration (new DividerItemDecoration(getActivity (), DividerItemDecoration.VERTICAL));
        return view;
    }

    public void query_and_add(String condition) {
        DatabaseHelper dbHelper= new DatabaseHelper(this.getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="select * from "+ DatabaseInfo.TABLE_NAME+" where "+condition;
        Cursor cursor =db.rawQuery(sql,null);

        while (cursor.moveToNext()){
            Calendar stC=Calendar.getInstance(),enC=Calendar.getInstance();
            String meetingid=cursor.getString(cursor.getColumnIndex("meetingID"));
            String topic=cursor.getString(cursor.getColumnIndex("topic"));
            String st=cursor.getString(cursor.getColumnIndex("startTime"));
            try {
                stC.setTime(Objects.requireNonNull(sdf.parse(st)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String en=cursor.getString(cursor.getColumnIndex("endTime"));
            try {
                enC.setTime(Objects.requireNonNull(sdf.parse(en)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String note=cursor.getString(cursor.getColumnIndex("note"));
            MeetingInfo thismeeting =new MeetingInfo(topic,stC,enC,note);
            list.add(thismeeting);
        }
        cursor.close();
        db.close();
    }
}
