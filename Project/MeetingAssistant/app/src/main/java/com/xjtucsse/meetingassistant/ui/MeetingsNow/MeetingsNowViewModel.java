package com.xjtucsse.meetingassistant.ui.MeetingsNow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeetingsNowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MeetingsNowViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("没有正在进行的会议");
    }

    public LiveData<String> getText() {
        return mText;
    }
}