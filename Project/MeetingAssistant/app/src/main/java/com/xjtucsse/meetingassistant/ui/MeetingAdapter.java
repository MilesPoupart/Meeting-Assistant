package com.xjtucsse.meetingassistant.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xjtucsse.meetingassistant.MeetingActivity;
import com.xjtucsse.meetingassistant.MeetingInfo;
import com.xjtucsse.meetingassistant.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.LinearViewHolder> {

    private Context Ct;
    private List<MeetingInfo> list;

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
    public void onBindViewHolder(@NonNull MeetingAdapter.LinearViewHolder holder, int position) {
        MeetingInfo mi = list.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        holder.tv1.setText(mi.meetingTopic);
        holder.tv2.setText("开始时间 "+sdf.format(mi.meetingStartTime.getTime()));
        holder.tv3.setText("结束时间 "+sdf.format(mi.meetingEndTime.getTime()));
        Log.d("MAD",mi.meetingTopic+" "+mi.meetingStartTime.getTime().toString()+" "+mi.meetingEndTime.getTime().toString());
        holder.tv1.setClickable(true);
        holder.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ct, MeetingActivity.class);
                Ct.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView tv1,tv2,tv3;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
        }

    }
}
