package com.example.clutter.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clutter.InterfaceMVP.CreateAccountMVP;
import com.example.clutter.Presenter.CreateAccountPresenter;
import com.example.clutter.R;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountMVP.View {
    private TextView mAddPhoto;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private Button btnContinue;
    private TextView tvHasCluttter;
    private CreateAccountPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        presenter = new CreateAccountPresenter(this);
        mAddPhoto = findViewById(R.id.tvAddPhoto);
        mFirstName = findViewById(R.id.etFirstName);
        mLastName = findViewById(R.id.etLastName);
        mEmail = findViewById(R.id.etEmail);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setEnabled(false);
        tvHasCluttter = findViewById(R.id.tvHasCluttter);

        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkUserInfo(s.toString(), mLastName.getText().toString(), mEmail.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkUserInfo(mFirstName.getText().toString(), s.toString(), mEmail.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkUserInfo(mFirstName.getText().toString(), mLastName.getText().toString(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        tvHasCluttter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void displayError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void displayButton(boolean setClickable) {
        btnContinue.setEnabled(setClickable);
    }
}


//create account presenter