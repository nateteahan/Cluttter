package com.example.clutter.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clutter.R;
import com.example.clutter.Transformations.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class StatusActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView tvHandle;
    private TextView tvStatus;
    private TextView tvName;
    private TextView tvTime;

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

        imageView = findViewById(R.id.ivStatus1);
        Picasso.get().load(profilePic).centerCrop()
                .transform(new RoundedTransformation(24, 24))
                .fit()
                .into(imageView);
        tvHandle = findViewById(R.id.tvHandle1);
        tvStatus = findViewById(R.id.tvStatus1);
        tvName = findViewById(R.id.tvName);
        tvTime = findViewById(R.id.tvTime);


        tvHandle.setText(handle);
        tvStatus.setText(status);
        tvTime.setText(time);
        tvName.setText(name);
    }
}
