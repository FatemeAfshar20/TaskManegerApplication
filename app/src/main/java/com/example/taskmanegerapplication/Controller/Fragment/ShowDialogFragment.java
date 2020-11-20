package com.example.taskmanegerapplication.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.taskmanegerapplication.Model.Task;
import com.example.taskmanegerapplication.Model.TaskState;
import com.example.taskmanegerapplication.R;
import com.example.taskmanegerapplication.Repository.TaskBDRepository;
import com.example.taskmanegerapplication.Utils.DateTimeFormat;
import com.example.taskmanegerapplication.Utils.PhotoUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ShowDialogFragment extends DialogFragment {
    public static final String ARGS_TASK_ID = "Task Id";
    public static final String EXTRA_CHANGING_SOME_THING =
            "com.example.taskmanegerapplication.Is Change SomeThing";
    private Task mTask;
    private TaskBDRepository mRepository;

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final String AUTHORITY =
            "com.example.taskmanegerapplication.fileprovider";

    private EditText mShowTitle,mShowContent,
            mShowTime,mShowDate,mShowState;

    private MaterialButton mButtonClose,mButtonEdit,
            mButtonDelete,mButtonCamera;

    private AppCompatImageView mImageView;

    private static File mPhotoFile;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_CAMERA) {
            receiveResult();

            mImageView.setImageBitmap(
                    PhotoUtils.getScalePhoto(
                            mTask.getImgAddress(),
                            getActivity()));
        }
    }

    private void setListener() {
        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepository.update(getTaskInput());
                sendData(true);
                dismiss();
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepository.delete(mTask);
                sendData(true);
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

    public void findViews(View view){
        mShowTitle=view.findViewById(R.id.task_title_show);
        mShowContent=view.findViewById(R.id.task_content_show);
        mShowTime=view.findViewById(R.id.task_time_show);
        mShowDate=view.findViewById(R.id.task_date_show);
        mShowState=view.findViewById(R.id.task_state_show);
        mButtonClose=view.findViewById(R.id.dialog_close_btn);
        mButtonDelete=view.findViewById(R.id.dialog_delete_btn);
        mButtonEdit=view.findViewById(R.id.dialog_edit_btn);

        mButtonCamera=view.findViewById(R.id.edit_dialog_camera);
        mImageView=view.findViewById(R.id.edit_dialog_img);
    }

    private void initView(){
        mShowTitle.setText(mTask.getTitle());
        mShowContent.setText(mTask.getContent());
        mShowTime.setText(DateTimeFormat.getTimeFormat(mTask.getTime()));
        mShowDate.setText(DateTimeFormat.getDateFormat(mTask.getDate()));
        mShowState.setText(mTask.getState().toString());

        mImageView.setImageBitmap(PhotoUtils.
                getScalePhoto(mTask.getImgAddress(),getActivity()));
    }

    private Task getTaskInput(){
        mTask.setTitle(mShowTitle.getText().toString());
        mTask.setContent(mShowContent.getText().toString());

        //TODO:INPUT FROM DATA PICKER & TIME PICKER
        mTask.setDate(new Date());
        mTask.setTime(new Date());
        mTask.setState(TaskState.valueOf(mShowState.getText().toString()));

        return mTask;
    }

    private void sendData(boolean isChangeSomeThing){
        Intent intent=new Intent();

        intent.putExtra(EXTRA_CHANGING_SOME_THING,isChangeSomeThing);

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