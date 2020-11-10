package com.example.taskmanegerapplication.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanegerapplication.Adapter.StateAdapter;
import com.example.taskmanegerapplication.Model.Task;
import com.example.taskmanegerapplication.R;
import com.example.taskmanegerapplication.Repository.TaskBDRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.UUID;

public class StateFragment extends Fragment {

    public static final String ARGS_TASK_STATE = "Task State";
    public static final String ARGS_USER_ID = "User Id";
    public static final String FRAGMENT_ADD_TASK_DIALOG_FRAGMENT = "Add Task Dialog Fragment";
    public static final int REQUEST_CODE_ADD_TASK = 1;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mBtnAdd;
    private StateAdapter mAdapter;

    private UUID mUserId;
    private String mStrTaskState;

    private TaskBDRepository mRepository;

    public StateFragment() {
        // Required empty public constructor
    }

    public static StateFragment newInstance(String taskState, UUID userId) {
        StateFragment fragment = new StateFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_TASK_STATE, taskState);
        args.putSerializable(ARGS_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = (UUID) getArguments().get(ARGS_USER_ID);
        mRepository = TaskBDRepository.getInstance(getContext());
        mStrTaskState = (String)
                getArguments().get(ARGS_TASK_STATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.
                inflate(
                        R.layout.fragment_state,
                        container,
                        false);
        findViews(view);
        setListener();
        setupAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupAdapter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_ADD_TASK) {
            Task task = (Task)
                    data.getSerializableExtra(
                            AddTaskDialogFragment.
                                    EXTRA_NEW_TASK);
            mRepository.insert(task);
            mAdapter.notifyDataSetChanged();
            setupAdapter();
            //updateUI();
        }
    }

    /*private void updateUI() {
        if (mStrTaskState.equals("TODO")){
            mAdapter.setTasks(mRepository.getTodoList());
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }else if(mStrTaskState.equals("DONE")){
            mAdapter.setTasks(mRepository.getDoneList());
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }else if (mStrTaskState.equals("DOING")){
            mAdapter.setTasks(mRepository.getDoingList());
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
*/
    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mBtnAdd = view.findViewById(R.id.btn_add);
    }

    private void setListener() {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialogFragment addTaskDialogFragment =
                        AddTaskDialogFragment.
                                newInstance(mUserId, mStrTaskState);

                addTaskDialogFragment.setTargetFragment(
                        StateFragment.this,
                        REQUEST_CODE_ADD_TASK);

                addTaskDialogFragment.show(
                        getActivity().getSupportFragmentManager(),
                        FRAGMENT_ADD_TASK_DIALOG_FRAGMENT);
            }
        });
    }

    private void setupAdapter() {
/*        switch (mStrTaskState){
            case "TODO":
                mAdapter=new StateAdapter(mRepository.getTodoList(mUserId),getContext());
                break;
            case "DOING":
                mAdapter=new StateAdapter(mRepository.getDoingList(mUserId),getContext());
                break;
            case "DONE":
                mAdapter=new StateAdapter(mRepository.getDoneList(mUserId),getContext());
                break;
            default:
                mAdapter=new StateAdapter(mRepository.getListWithUserId(mUserId),getContext());
                break;
        }*/
            mAdapter = new StateAdapter(
                    mRepository.getListWithUserId(mUserId), getContext());

        mRecyclerView.
                setLayoutManager(
                        new LinearLayoutManager(getContext()));

        mRecyclerView.setAdapter(mAdapter);
    }
}