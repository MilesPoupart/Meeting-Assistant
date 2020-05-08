package com.xjtucsse.meetingassistant.ui.MeetingsNow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.xjtucsse.meetingassistant.R;

public class MeetingsNowFragment extends Fragment {

    private MeetingsNowViewModel meetingsNowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meetingsNowViewModel =
                ViewModelProviders.of(this).get(MeetingsNowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_meetings_now, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        meetingsNowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
