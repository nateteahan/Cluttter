package com.example.clutter.View;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clutter.R;

public class StatusActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView tvHandle;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        /* FIXME add links */
        String status = getIntent().getStringExtra("STATUS");
        String handle = getIntent().getStringExtra("HANDLE");

        Drawable drawable = getResources().getDrawable(R.drawable.me);
        imageView = findViewById(R.id.ivStatus1);
        imageView.setImageDrawable(drawable);
        tvHandle = findViewById(R.id.tvHandle1);
        tvStatus = findViewById(R.id.tvStatus1);


        tvHandle.setText(handle);
        tvStatus.setText(status);
    }
}
