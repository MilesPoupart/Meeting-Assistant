package com.xjtucsse.meetingassistant;
import android.util.Log;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MeetingInfo {
    public String meetingID,meetingTopic;
    public Calendar meetingStartTime,meetingEndTime;
    public String meetingNote;
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public MeetingInfo(String topic,Calendar st,Calendar en,String note){
        meetingID = getMeetingID(topic+sdf.format(st.getTime())+sdf.format(en.getTime()));
        Log.d("MTIF",meetingID);
        meetingTopic = topic;
        meetingStartTime = st;
        meetingEndTime = en;
        meetingNote = note;
    }
    public static String getMeetingID(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
