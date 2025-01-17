package com.example.clutter.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clutter.InterfaceMVP.SignInMVP;
import com.example.clutter.Model.ModelSingleton;
import com.example.clutter.Model.User;
import com.example.clutter.Presenter.SignInPresenter;
import com.example.clutter.R;
import com.example.clutter.sdk.model.SignInUser;

public class SignInActivity extends AppCompatActivity implements SignInMVP.View {

    private EditText mUserHandle;
    private EditText mPassword;
    private TextView mSignUp;
    private Button mSignIn;
    private SignInPresenter presenter;
    private static final String TAG = SignInActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        presenter = new SignInPresenter(this);
        mUserHandle = (EditText) findViewById(R.id.etNewHandle);
        mPassword = (EditText) findViewById(R.id.etNewPassword);
        mSignUp = (TextView) findViewById(R.id.tvSignUp);
        mSignIn = (Button) findViewById(R.id.btnSignup);
        mSignIn.setEnabled(false);

        mUserHandle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.enableLogin(s.toString(), mPassword.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.enableLogin(mUserHandle.getText().toString(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInUser user = new SignInUser();
                user.setUserhandle(mUserHandle.getText().toString());
                user.setPassword(mPassword.getText().toString());

                signIn(user);
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successfulLogin() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
//        finish();
    }

    @Override
    public void displayButton(boolean isClickable) {
        mSignIn.setEnabled(isClickable);
    }

    public void setSingletonUser(User user) {
        ModelSingleton.setmUser(user);
    }

    @Override
    public void signIn(SignInUser user) {
        presenter.signIn(user);
    }
}
