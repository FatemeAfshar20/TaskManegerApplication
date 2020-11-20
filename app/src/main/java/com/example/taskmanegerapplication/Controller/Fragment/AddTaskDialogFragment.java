package com.example.taskmanegerapplication.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanegerapplication.Model.Task;
import com.example.taskmanegerapplication.Model.TaskState;
import com.example.taskmanegerapplication.R;
import com.example.taskmanegerapplication.Repository.TaskBDRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddTaskDialogFragment extends DialogFragment {
    public static final String EXTRA_NEW_TASK = "com.example.taskmanagerapp.New Task";
    public static final String ARG_USER_ID = "User Id";
    public static final String ARGS_TASK_STATE = "task state";

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final String AUTHORITY =
            "com.example.taskmanegerapplication.fileprovider";

    private UUID mUserId;
    private TaskBDRepository mTaskDBRepository;
    private Task mTask = new Task();
    private String mStrTaskState;

    private TextInputEditText mAddTitle, mAddContent;
    private MaterialButton mButtonOK, mButtonCancel, mButtonCamera;

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    private static File mPhotoFile;

    public AddTaskDialogFragment() {
        // Required empty public constructor
    }

    public static AddTaskDialogFragment newInstance(UUID userId, String taskState) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);
        args.putString(ARGS_TASK_STATE, taskState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = (UUID)
                getArguments().getSerializable(ARG_USER_ID);
        mTaskDBRepository=
                TaskBDRepository.getInstance(
                        getContext());
        mStrTaskState=getArguments().getString(ARGS_TASK_STATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_add_task_dialog,
                container,
                false);
        findViews(view);
        setListener();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_CAMERA) {
            receiveResult();
        }
    }

    public void findViews(View view) {
        mAddTitle = view.findViewById(R.id.exist_task_title);
        mAddContent = view.findViewById(R.id.exist_task_content);
        mButtonOK = view.findViewById(R.id.exist_dialog_ok_btn);
        mButtonCancel = view.findViewById(R.id.exist_dialog_cancel_btn);
        mButtonCamera = view.findViewById(R.id.exist_dialog_camera);
        mDatePicker = view.findViewById(R.id.date_picker_exist);
        mTimePicker = view.findViewById(R.id.time_picker_exist);
    }

    private void setListener() {
        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                returnNewTask();
                sendData();
                dismiss();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    takePhoto();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void returnNewTask() {

        if (!mAddTitle.equals(""))
            mTask.setTitle(mAddTitle.getText().toString());
        else
            mTask.setTitle("");

        if (!mAddContent.equals(""))
            mTask.setContent(mAddContent.getText().toString());
        else
            mTask.setContent("");

        mTask.setState(TaskState.valueOf(mStrTaskState));

        if (mDatePicker != null)
            mTask.setDate(getDate(mDatePicker));
        else
            mTask.setDate(new Date());

        if (mTimePicker != null)
            mTask.setTime(getTime(mTimePicker));
        else
            mTask.setTime(new Date());

        mTask.setUserId(mUserId);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date getTime(TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();
        int hour = timePicker.getHour();
        calendar.set(Calendar.HOUR, hour);
        int minute = timePicker.getMinute();
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    private Date getDate(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        int year = datePicker.getYear();
        calendar.set(Calendar.YEAR, year);
        int month = datePicker.getMonth();
        calendar.set(Calendar.MONTH, month);
        int dayOfMonth = datePicker.getDayOfMonth();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return calendar.getTime();
    }

    private void sendData() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NEW_TASK, mTask);

        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                Activity.RESULT_OK,
                intent);
    }

    public void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            mPhotoFile = null;

            try {
                mPhotoFile = createImageFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            if (mPhotoFile != null) {
                Uri photoUri = getPhotoUri();

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
            }
        }
    }

    public File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getFilesDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    private Uri getPhotoUri() {
        return FileProvider.getUriForFile(getActivity(),
                AUTHORITY, mPhotoFile);
    }

    public void receiveResult() {
        Uri photoUri = getPhotoUri();
        mTask.setImgAddress(mPhotoFile.getAbsolutePath());

        getActivity().revokeUriPermission(photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }
}