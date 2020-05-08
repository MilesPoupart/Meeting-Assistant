package com.xjtucsse.meetingassistant.ui.MeetingsAhead;

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
import androidx.recyclerview.widget.RecyclerView;

import com.xjtucsse.meetingassistant.R;

import java.util.ArrayList;

public class MeetingsAheadFragment extends Fragment {

    private MeetingsAheadViewModel meetingsAheadViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        meetingsAheadViewModel =
                ViewModelProviders.of(this).get(MeetingsAheadViewModel.class);
        View root = inflater.inflate(R.layout.fragment_meetings_ahead, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        meetingsAheadViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
