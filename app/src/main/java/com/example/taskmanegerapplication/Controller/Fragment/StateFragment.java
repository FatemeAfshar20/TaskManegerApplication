package com.example.taskmanegerapplication.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanegerapplication.Adapter.StateAdapter;
import com.example.taskmanegerapplication.Model.Task;
import com.example.taskmanegerapplication.R;
import com.example.taskmanegerapplication.Repository.TaskBDRepository;
import com.example.taskmanegerapplication.Utils.PhotoUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StateFragment extends Fragment {

    public static final String ARGS_TASK_STATE = "Task State";
    public static final String ARGS_USER_ID = "User Id";
    public static final String FRAGMENT_ADD_TASK_DIALOG_FRAGMENT = "Add Task Dialog Fragment";
    public static final int REQUEST_CODE_ADD_TASK = 1;
    public static final String FRAGMENT_SHOW_TASK = "Show Task";
    public static final int REQUEST_CODE_SHOW = 2;
    public static final String AUTHORITY = "com.example.taskmanegerapplication.fileprovider";
    public static final int REQUEST_CODE_CAMERA = 3;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mBtnAdd;
    private StateAdapter mAdapter;

    private UUID mUserId;
    private String mStrTaskState;

    private TaskBDRepository mRepository;
    private File mPhotoFile;
    private Task mTask;
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
            updateUI();

        }else if (requestCode==REQUEST_CODE_SHOW){
            updateUI();
        }else if (requestCode==REQUEST_CODE_CAMERA){
            Uri photoUri = FileProvider.getUriForFile(getActivity(),
                    AUTHORITY, mPhotoFile);

            getActivity().revokeUriPermission(photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updateUI();
        }
    }

    private void updateUI() {
        mAdapter.notifyDataSetChanged();
        setupAdapter();
    }

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
                    mRepository.getListWithState(mStrTaskState,mUserId), getContext(), new StateAdapter.OnIconSelectListener() {
                @Override
                public void onSelectShowBtn(UUID taskId) {
                    ShowDialogFragment showDialogFragment=
                            ShowDialogFragment.newInstance(taskId);

                    showDialogFragment.setTargetFragment(
                            StateFragment.this,
                            REQUEST_CODE_SHOW);

                    showDialogFragment.
                            show(getFragmentManager(),
                                    FRAGMENT_SHOW_TASK);
                }

                @Override
                public void onShareTaskInfo(String taskInfo) {
 /*                   Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT,taskInfo);
                    intent.setType("text/plain");*/
                    Intent intent = ShareCompat.IntentBuilder
                            .from(getActivity()).
                                    setText(taskInfo).
                                    setType("text/plain").
                                    setSubject("Sharing Task Information ").
                                    getIntent();
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                        startActivity(intent);
                }

                @Override
                public String onTakePhoto(Task task) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        mPhotoFile = null;

                        try {
                            mPhotoFile=createImageFile();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        if (mPhotoFile != null) {
                            Uri photoUri= FileProvider.getUriForFile(
                                    getContext(),
                                    AUTHORITY,
                                    mPhotoFile);
                            task.setImgAddress(mPhotoFile.getAbsolutePath());
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                        }
                    }
                    return mPhotoFile.getAbsolutePath();
                }

                @Override
                public void onSetImage(AppCompatImageView imageView) {
                }

            });

        mRecyclerView.
                setLayoutManager(
                        new LinearLayoutManager(getContext()));

        mRecyclerView.setAdapter(mAdapter);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getFilesDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}