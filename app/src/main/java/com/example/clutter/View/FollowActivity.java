package com.example.clutter.View;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clutter.Model.FollowInfo;
import com.example.clutter.R;

import java.util.List;

public class FollowActivity extends AppCompatActivity {
    private TextView followerTab;
    private TextView followingTab;
    private FragmentManager fm;
    private Fragment frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        fm = getSupportFragmentManager();
        frag = new FollowerFragment();
        fm.beginTransaction().add(R.id.follow_container, frag).commit();

        followerTab = findViewById(R.id.followerTab);
        followingTab = findViewById(R.id.followingTab);

        followerTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear the first loaded followerFragment and start a new followerFragment
                fm.beginTransaction().remove(frag).commit();
                frag = new FollowerFragment();
                fm.beginTransaction().replace(R.id.follow_container, frag).commit();

            }
        });

        followingTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().remove(frag).commit();
                frag = new FollowingFragment();
                fm.beginTransaction().replace(R.id.follow_container, frag).commit();
            }
        });

    }
}
