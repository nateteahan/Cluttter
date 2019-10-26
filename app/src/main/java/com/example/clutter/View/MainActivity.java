package com.example.clutter.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.clutter.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private FragmentManager fm;
    private Fragment fragment;
//    private ImageButton ibCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        ibCompose = findViewById(R.id.compose);
        fm = getSupportFragmentManager();

        // Start feedFragment on startup
        fragment = new FeedFragment();
        fm.beginTransaction().add(R.id.fragment_container, fragment).commit();

//        ibCompose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserActivity.this, ComposeMessageActivity.class);
//                startActivity(intent);
//            }
//        });

        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (fragment != null) {
                    fm.beginTransaction().remove(fragment).commit();
                }

                if (menuItem.getItemId() == R.id.navigation_home) {
                    fragment = new FeedFragment();
                    fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
//                    transaction.replace(R.id.fragment_container, new FeedFragment());
                }

                else if (menuItem.getItemId() == R.id.compose) {
                    // Story feedFragment
//                    fragment = new StoryFragment();
//                    fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    Intent intent = new Intent(MainActivity.this, ComposeMessageActivity.class);
                    startActivity(intent);
                }

                else if (menuItem.getItemId() == R.id.account) {
                    // Following frag
                    fragment = new AccountFragment();
                    fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
                return false;
            }
        });
    }
}
