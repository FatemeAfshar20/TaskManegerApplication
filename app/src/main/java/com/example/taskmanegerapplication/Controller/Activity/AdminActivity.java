package com.example.taskmanegerapplication.Controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanegerapplication.Adapter.AdminAdapter;
import com.example.taskmanegerapplication.Controller.Fragment.AdminFragment;
import com.example.taskmanegerapplication.R;

public class AdminActivity extends SingleFragmentActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, AdminActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return AdminFragment.newInstance();
    }
}