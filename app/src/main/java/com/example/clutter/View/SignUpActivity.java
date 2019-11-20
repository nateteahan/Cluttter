package com.example.clutter.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clutter.InterfaceMVP.SignUpMVP;
import com.example.clutter.Model.ModelSingleton;
import com.example.clutter.Model.User;
import com.example.clutter.Presenter.SignUpPresenter;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.squareup.picasso.Picasso;

public class SignUpActivity extends AppCompatActivity implements SignUpMVP.View {
    private static final int RESULT_LOAD_IMAGE = 1;

    private ImageView userImage;
    private TextView mAddPhoto;
    private Button btnSignUp;
    private EditText mNewHandle;
    private EditText mNewUserPassword;
    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final String firstName = getIntent().getStringExtra("First name");
        final String lastName = getIntent().getStringExtra("Last name");
        final String email = getIntent().getStringExtra("Email");

        presenter = new SignUpPresenter(this);
        btnSignUp = (Button)findViewById(R.id.btnSignup);
        btnSignUp.setEnabled(false);
        mNewHandle = (EditText)findViewById(R.id.etNewHandle);
        mNewUserPassword = (EditText)findViewById(R.id.etNewPassword);
        userImage = findViewById(R.id.imageView3);
        mAddPhoto = findViewById(R.id.tvAddPhoto);

        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

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
                User rootUser = new User(mNewHandle.getText().toString(), firstName, lastName, email);
                ModelSingleton.setmUser(rootUser);

                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Picasso.get().load(selectedImage)
                        .fit()
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(userImage);
        }
    }

    public void displayButton(boolean setClickable) {
        btnSignUp.setEnabled(setClickable);
    }


    @Override
    public void displayError(String message) {

    }
}
