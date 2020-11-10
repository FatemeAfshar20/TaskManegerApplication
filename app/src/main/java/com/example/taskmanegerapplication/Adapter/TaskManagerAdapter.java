package com.example.taskmanegerapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.taskmanegerapplication.Controller.Fragment.StateFragment;
import com.example.taskmanegerapplication.Model.User;
import com.google.android.material.tabs.TabLayout;

import java.util.UUID;

public class TaskManagerAdapter extends FragmentStateAdapter {
    private UUID mUserId;

    public TaskManagerAdapter(@NonNull FragmentActivity fragmentActivity, UUID userId) {
        super(fragmentActivity);
        mUserId=userId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return StateFragment.newInstance("TODO",mUserId);
            case 1:
               return StateFragment.newInstance("DOING",mUserId);
            case 2:
                return StateFragment.newInstance("DONE",mUserId);
            default:
                return null;
        }


    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
