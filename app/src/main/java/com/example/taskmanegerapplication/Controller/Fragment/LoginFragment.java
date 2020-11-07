package com.example.taskmanegerapplication.Controller.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskmanegerapplication.Controller.Activity.SignActivity;
import com.example.taskmanegerapplication.Controller.Activity.TaskManagerActivity;
import com.example.taskmanegerapplication.Model.User;
import com.example.taskmanegerapplication.R;
import com.example.taskmanegerapplication.Repository.UserDBRepository;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private UserDBRepository mRepository;
    private MaterialButton mButtonLogin,mButtonSign;
    private EditText mUsername,mPassword;
    private User mUser;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository=UserDBRepository.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,
                container,
                false);
        findViews(view);
        setListener();
        return view;
    }

    public void findViews(View view){
        mButtonLogin=view.findViewById(R.id.btn_login);
        mButtonSign=view.findViewById(R.id.btn_sign);
        mUsername=view.findViewById(R.id.username);
        mPassword=view.findViewById(R.id.password);
    }

    private void setListener(){
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mRepository.userExist(mUsername.getText().toString())){
                        mUser=mRepository.get(mUsername.getText().toString());
                        if (mPassword.getText().toString().equals(mUser.getPass()))
                            TaskManagerActivity.start(getContext(),mUser.getId());
                        else
                            returnToast(R.string.invalid_input);
                    }else
                        returnToast("At first Sign Up");

            }
        });

        mButtonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: finish activity
                SignActivity.start(getContext());
            }
        });
    }

    public  void returnToast(int msgId){
        Toast.makeText(getContext(),msgId,Toast.LENGTH_LONG)
                .show();;
    }

    public  void returnToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG)
                .show();
    }
}