package com.example.clutter.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.clutter.InterfaceMVP.SignUpMVP;
import com.example.clutter.Presenter.SignUpPresenter;
import com.example.clutter.R;

import com.example.clutter.Model.ModelSingleton;

public class SignUpActivity extends AppCompatActivity implements SignUpMVP.View {

    private Button btnSignUp;
    private EditText mNewHandle;
    private EditText mNewUserPassword;
    private SignUpPresenter presenter;
    private ModelSingleton singleton = ModelSingleton.getINSTANCE();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        presenter = new SignUpPresenter(this);
        btnSignUp = (Button)findViewById(R.id.btnSignup);
        btnSignUp.setEnabled(false);
        mNewHandle = (EditText)findViewById(R.id.etNewHandle);
        mNewUserPassword = (EditText)findViewById(R.id.etNewPassword);

        mNewHandle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.enableButton(s.toString(), mNewUserPassword.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mNewUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.enableButton(mNewHandle.getText().toString(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void displayButton(boolean setClickable) {
        btnSignUp.setEnabled(setClickable);
    }


    @Override
    public void displayError(String message) {

    }
}
