package com.xjtucsse.meetingassistant.ui.MeetingsBefore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeetingsBeforeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MeetingsBeforeViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("没有已经结束的会议");
    }

    public LiveData<String> getText() {
        return mText;
    }
}