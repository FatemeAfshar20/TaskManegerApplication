package com.example.taskmanegerapplication.Controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanegerapplication.Controller.Fragment.TaskManagerFragment;

import java.util.UUID;

/**
 * for start this Activity you should pass uuid,
 * from uuid we can find , which user did login
 */

public class TaskManagerActivity extends SingleFragmentActivity {
    private UUID mUserId;
    public static final String EXTRA_USER_ID =
            "com.example.taskmanagerapp.User Id";

    public static void start(Context context,UUID userId) {
        Intent starter = new Intent(context, TaskManagerActivity.class);
        starter.putExtra(EXTRA_USER_ID,userId);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        Intent intent=getIntent();
        mUserId= (UUID)
                intent.getSerializableExtra(EXTRA_USER_ID);
        return TaskManagerFragment.newInstance(mUserId);
    }

/*    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
    }*/
}