package com.example.clutter.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.clutter.R;

public class PopUpActivity extends AppCompatActivity {
    private Button mPhoto;
    private Button mVideo;
    private Button mAttach;
    private EditText mURL;
    private boolean isPhoto;
    private static final int RESULT_PHOTO = 0;
    private static final int RESULT_VIDEO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pop_up);

        mPhoto = findViewById(R.id.button);
        mVideo = findViewById(R.id.button2);
        mAttach = findViewById(R.id.button4);
        mURL = findViewById(R.id.etURL);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.6));

        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mURL.setVisibility(View.VISIBLE);
                mVideo.setVisibility(View.GONE);
                isPhoto = true;
            }
        });

        mVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mURL.setVisibility(View.VISIBLE);
                mPhoto.setVisibility(View.GONE);
                isPhoto = false;
            }
        });

        mAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                if (isPhoto) {
                    intent.putExtra("imageAttachment", mURL.getText().toString());
                    setResult(RESULT_PHOTO, intent);
                }
                else {
                    intent.putExtra("videoAttachment", mURL.getText().toString());
                    setResult(RESULT_VIDEO, intent);
                }
                finish();
            }
        });
    }
}
