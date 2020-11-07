package com.example.taskmanegerapplication.Controller.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskmanegerapplication.Controller.Activity.LoginActivity;
import com.example.taskmanegerapplication.Model.User;
import com.example.taskmanegerapplication.R;
import com.example.taskmanegerapplication.Repository.UserDBRepository;
import com.google.android.material.button.MaterialButton;

public class SignFragment extends Fragment {
    private UserDBRepository mRepository;
    private User mUser=new User();
    private MaterialButton mButtonSign;
    private EditText mUsername,
            mPassword;

    public SignFragment() {
        // Required empty public constructor
    }

    public static SignFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SignFragment fragment = new SignFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign,
                container,
                false);
        findViews(view);

        setListener();
        return view;
    }

    public void findViews(View view){
        mButtonSign=view.findViewById(R.id.btn_sign_up);
        mUsername=view.findViewById(R.id.user_name);
        mPassword=view.findViewById(R.id.pass);
    }

    private void setListener(){
        mButtonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mRepository.userExist(mUsername.getText().toString())){
                    if (!mUsername.getText().toString().equals("") && !mPassword.getText().toString().equals("")){
                        mUser.setUsername(mUsername.getText().toString());
                        mUser.setPass(mPassword.getText().toString());

                        mRepository.insert(mUser);

                        LoginActivity.start(getContext());
                    }else
                        returnToast(R.string.cant_null);
                }else
                    returnToast("User Already Exist");
            }
        });
    }

    private static boolean isNumeric(String strNum) {
        for (char c : strNum.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
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