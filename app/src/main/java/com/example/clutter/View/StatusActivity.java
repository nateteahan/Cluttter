package com.example.clutter.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.example.clutter.Transformations.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

public class StatusActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView tvHandle;
    private TextView tvStatus;
    private TextView tvName;
    private TextView tvTime;
    private ImageView ivAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        /* FIXME add links */
        String profilePic = getIntent().getStringExtra("PIC");
        String status = getIntent().getStringExtra("STATUS");
        String handle = getIntent().getStringExtra("HANDLE");
        String time = getIntent().getStringExtra("TIME");
        String name = getIntent().getStringExtra("NAME");
        String image = getIntent().getStringExtra("IMAGE");
        String video = getIntent().getStringExtra("VIDEO");

        imageView = findViewById(R.id.ivStatus1);
        Picasso.get().load(profilePic).centerCrop()
                .transform(new CircleTransform())
                .fit()
                .into(imageView);
        tvHandle = findViewById(R.id.tvHandle1);
        tvStatus = findViewById(R.id.tvStatus1);
        tvName = findViewById(R.id.tvName);
        tvTime = findViewById(R.id.tvTime);
        ivAttachment = findViewById(R.id.ivAttach);


        tvHandle.setText(handle);
        tvStatus.setText(status);
        tvTime.setText(time);
        tvName.setText(name);

        Pattern usernamePattern = Pattern.compile("@+[a-zA-Z0-9]*");
        Linkify.addLinks(tvStatus, usernamePattern, "input.my.scheme://"); //Goto androidmanifest.xml and look at the scheme of the UserActivity

        Pattern hashtagPattern = Pattern.compile("#+[a-zA-Z0-9]*");
        Linkify.addLinks(tvStatus, hashtagPattern, "input.hashtag.scheme://");

        if (image != null) {
            ivAttachment.setVisibility(View.VISIBLE);

            Picasso.get().load(image)
                    .centerCrop()
                    .transform(new RoundedTransformation(24, 24))
                    .fit()
                    .into(ivAttachment);
        }
        else if (video != null) {
            ivAttachment.setVisibility(View.VISIBLE);

            Glide.with(getApplicationContext())
                    .load(video)
                    .into(ivAttachment);
        }

        tvHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusActivity.this, UserActivity.class);
                intent.putExtra("userHandle", tvHandle.getText().toString());
                startActivity(intent);
            }
        });
    }
}
