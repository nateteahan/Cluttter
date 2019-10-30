package com.example.clutter.View;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.clutter.R;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {
    private TextView tvFollowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        tvFollowers = findViewById(R.id.tvNumFollowers);

        //you can now parse your data here
        Uri data = getIntent().getData();
        if (data!=null){
            if (data.toString().contains("input.my.scheme://")){
                String[] strip_id = data.toString().split("//");
                try {
                    JSONObject jsonObject_mention = new JSONObject(strip_id[1]);
//                    getSpecificUserProfile(jsonObject_mention.optString("mention_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
