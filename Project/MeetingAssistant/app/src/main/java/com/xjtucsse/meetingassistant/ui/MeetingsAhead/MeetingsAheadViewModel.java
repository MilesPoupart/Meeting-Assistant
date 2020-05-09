package com.xjtucsse.meetingassistant.ui.MeetingsAhead;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeetingsAheadViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MeetingsAheadViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("没有即将召开的会议");
    }

    public LiveData<String> getText() {
        return mText;
    }
}