package com.example.taskmanegerapplication.Controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanegerapplication.Controller.Fragment.SignFragment;

public class SignActivity extends SingleFragmentActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SignActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return SignFragment.newInstance();
    }
}