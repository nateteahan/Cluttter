package com.example.clutter.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.clutter.R;

public class FollowActivity extends AppCompatActivity {
    private TextView followerTab;
    private TextView followingTab;
    private FragmentManager fm;
    private Fragment frag;
    private String handle;
    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        handle = getIntent().getStringExtra("user");
        args = new Bundle();
        args.putString("handle", handle);

        fm = getSupportFragmentManager();
        frag = new FollowerFragment();
        frag.setArguments(args);

        fm.beginTransaction().add(R.id.follow_container, frag).commit();

        followerTab = findViewById(R.id.followerTab);
        followingTab = findViewById(R.id.followingTab);

        followerTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear the first loaded followerFragment and start a new followerFragment
                fm.beginTransaction().remove(frag).commit();
                frag = new FollowerFragment();
                frag.setArguments(args);
                fm.beginTransaction().replace(R.id.follow_container, frag).commit();

            }
        });

        followingTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().remove(frag).commit();
                frag = new FollowingFragment();
                frag.setArguments(args);
                fm.beginTransaction().replace(R.id.follow_container, frag).commit();
            }
        });

    }
}
