package com.example.taskmanegerapplication.Controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanegerapplication.Controller.Fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return LoginFragment.newInstance();
    }
}