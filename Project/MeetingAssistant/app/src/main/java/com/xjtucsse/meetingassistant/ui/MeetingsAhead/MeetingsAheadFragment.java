package com.xjtucsse.meetingassistant.ui.MeetingsAhead;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xjtucsse.meetingassistant.DatabaseDAO;
import com.xjtucsse.meetingassistant.DatabaseHelper;
import com.xjtucsse.meetingassistant.DatabaseInfo;
import com.xjtucsse.meetingassistant.MainActivity;
import com.xjtucsse.meetingassistant.MeetingActivity;
import com.xjtucsse.meetingassistant.MeetingInfo;
import com.xjtucsse.meetingassistant.R;
import com.xjtucsse.meetingassistant.ui.MeetingAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MeetingsAheadFragment extends Fragment {
    private MeetingsAheadViewModel meetingsAheadViewModel;
    private RecyclerView RV1;
    private List<MeetingInfo> list=new ArrayList<MeetingInfo>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        meetingsAheadViewModel =
                ViewModelProviders.of(this).get(MeetingsAheadViewModel.class);
        View view = inflater.inflate(R.layout.fragment_meetings_ahead, container, false);
        RV1 = view.findViewById(R.id.rv1);
        RV1.addItemDecoration(new SpacesItemDecoration(8));

        Calendar NowTime=Calendar.getInstance();
        String nowTime=sdf.format(NowTime.getTime());

        query_and_add("startTime>\""+nowTime+"\" order by startTime",view.getContext());
        DatabaseDAO dao = new DatabaseDAO(this.getActivity());
        //dao.query("startTime>\""+nowTime+"\" order by startTime");
        RV1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        RV1.setAdapter(new MeetingAdapter(getActivity(),list));
        RV1.addItemDecoration (new DividerItemDecoration (getActivity (), DividerItemDecoration.VERTICAL));
        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String note = data.getStringExtra("note");
//        String topic = data.getStringExtra("topic");
//        //写入数据库
//    }

    public void query_and_add(String condition,Context context) {
        DatabaseHelper dbHelper= new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="select * from "+ DatabaseInfo.TABLE_NAME+" where "+condition;
        //Log.d("QAD",sql);
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
            //Log.d("MAF",thismeeting.meetingStartTime.getTime().toString()+" "+thismeeting.meetingEndTime.getTime().toString());
        }
        cursor.close();
        db.close();
    }


    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }
}
