package com.example.taskmanegerapplication.Controller.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskmanegerapplication.R;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class TaskManagerFragment extends Fragment {

    public static final String ARGS_USER_ID = "User Id";
    private UUID mUserId;

    public TaskManagerFragment() {
        // Required empty public constructor
    }

    public static TaskManagerFragment newInstance(UUID userId) {
        Bundle args = new Bundle();

        TaskManagerFragment fragment = new TaskManagerFragment();
        args.putSerializable(ARGS_USER_ID,userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId= (UUID) getArguments().get(ARGS_USER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_task_manager
                ,container
                ,false);
        return view;
    }
}