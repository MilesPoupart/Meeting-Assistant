package com.xjtucsse.meetingassistant.ui.MeetingsBefore;

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

public class MeetingsBeforeFragment extends Fragment {

    private MeetingsBeforeViewModel meetingsBeforeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meetingsBeforeViewModel =
                ViewModelProviders.of(this).get(MeetingsBeforeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_meetings_before, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        meetingsBeforeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
