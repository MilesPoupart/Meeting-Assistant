package com.xjtucsse.meetingassistant.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xjtucsse.meetingassistant.DatabaseDAO;
import com.xjtucsse.meetingassistant.DatabaseHelper;
import com.xjtucsse.meetingassistant.DatabaseInfo;
import com.xjtucsse.meetingassistant.MainActivity;
import com.xjtucsse.meetingassistant.MeetingActivity;
import com.xjtucsse.meetingassistant.MeetingInfo;
import com.xjtucsse.meetingassistant.R;
import com.xjtucsse.meetingassistant.ui.MeetingsAhead.MeetingsAheadFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.LinearViewHolder> {

    private Context Ct;
    private List<MeetingInfo> list;
    MeetingInfo mi;
    private String sttimestr,edtimestr,topocstr;

    public MeetingAdapter(Context context, List<MeetingInfo> list){
        this.Ct = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MeetingAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.linear_item,parent,false);
        LinearViewHolder holder = new LinearViewHolder (view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MeetingAdapter.LinearViewHolder holder, int position) {
        mi = list.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        holder.tv1.setText(mi.meetingTopic);
        holder.tv2.setText("开始时间 "+sdf.format(mi.meetingStartTime.getTime()));
        holder.tv3.setText("结束时间 "+sdf.format(mi.meetingEndTime.getTime()));
        holder.tv1.setClickable(true);
        holder.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sttimestr=holder.tv2.getText().toString().substring(5);
                edtimestr=holder.tv3.getText().toString().substring(5);
                topocstr=holder.tv1.getText().toString();
                Intent intent = new Intent(Ct, MeetingActivity.class);
                intent.putExtra("topic",topocstr);
                intent.putExtra("starttime",sttimestr);
                intent.putExtra("endtime",edtimestr);
                //intent.putExtra("note","thismeetingnote");
                ((Activity) Ct).startActivityForResult(intent,1);
            }
        });
        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sttimestr=holder.tv2.getText().toString().substring(5);
                edtimestr=holder.tv3.getText().toString().substring(5);
                topocstr=holder.tv1.getText().toString();
                String thismeetingid=MeetingInfo.getMeetingID(topocstr+sttimestr+edtimestr);
                DatabaseDAO dao = new DatabaseDAO(v.getContext());
                dao.delete(thismeetingid);
                Toast.makeText(v.getContext(),"删除成功！请刷新页面！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView tv1,tv2,tv3;
        private ImageButton delbtn;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            delbtn=itemView.findViewById(R.id.btn_del_note);
        }
    }

}
