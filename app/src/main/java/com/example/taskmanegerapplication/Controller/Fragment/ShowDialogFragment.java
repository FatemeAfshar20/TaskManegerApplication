package com.example.taskmanegerapplication.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanegerapplication.Model.Task;
import com.example.taskmanegerapplication.R;
import com.example.taskmanegerapplication.Repository.TaskBDRepository;
import com.example.taskmanegerapplication.Utils.DateTimeFormat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.UUID;

public class ShowDialogFragment extends DialogFragment {
    public static final String ARGS_TASK_ID = "Task Id";
    private Task mTask;
    private TaskBDRepository mRepository;

    private MaterialTextView mShowTitle,mShowContent,
            mShowTime,mShowDate,mShowState;
    private MaterialButton mButtonClose;

    public ShowDialogFragment() {
        // Required empty public constructor
    }

    public static ShowDialogFragment newInstance(UUID taskId) {
        ShowDialogFragment fragment = new ShowDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID,taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository=TaskBDRepository.getInstance(getContext());
        UUID taskId=
                (UUID) getArguments().get(ARGS_TASK_ID);

        mTask=mRepository.get(taskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show, container, false);
        findViews(view);
        initView();

        setListener();
        return view;
    }

    private void setListener() {
        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void findViews(View view){
        mShowTitle=view.findViewById(R.id.task_title_show);
        mShowContent=view.findViewById(R.id.task_content_show);
        mShowTime=view.findViewById(R.id.task_time_show);
        mShowDate=view.findViewById(R.id.task_date_show);
        mShowState=view.findViewById(R.id.task_state_show);
        mButtonClose=view.findViewById(R.id.dialog_close_btn);
    }

    private void initView(){
        mShowTitle.setText(mTask.getTitle());
        mShowContent.setText(mTask.getContent());
        mShowTime.setText(DateTimeFormat.getTimeFormat(mTask.getTime()));
        mShowDate.setText(DateTimeFormat.getDateFormat(mTask.getDate()));
        mShowState.setText(mTask.getState().toString());
    }
}