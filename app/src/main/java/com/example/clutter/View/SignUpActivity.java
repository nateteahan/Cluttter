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
import android.widget.Toast;

import com.example.clutter.InterfaceMVP.SignUpMVP;
import com.example.clutter.Model.ModelSingleton;
import com.example.clutter.Model.User;
import com.example.clutter.Presenter.SignUpPresenter;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.example.clutter.sdk.model.RegisterUser;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* AMPLIFY */

public class SignUpActivity extends AppCompatActivity implements SignUpMVP.View {
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String TAG = SignUpActivity.class.getName();

    private ImageView userImage;
    private TextView mAddPhoto;
    private Button btnSignUp;
    private EditText mNewHandle;
    private EditText mNewUserPassword;
    private SignUpPresenter presenter;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        firstName = getIntent().getStringExtra("First name");
        lastName = getIntent().getStringExtra("Last name");
        email = getIntent().getStringExtra("Email");

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
                RegisterUser user = new RegisterUser();
                user.setUserhandle(mNewHandle.getText().toString());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(mNewUserPassword.getText().toString());
                user.setProfilePic(profilePic);

                presenter.signUp(user);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            /* Using Picasso and URI to display in image view immediately */
            Uri selectedImage = data.getData();
            profilePic = selectedImage.toString();
            Picasso.get().load(selectedImage)
                        .fit()
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(userImage);

            /* Encode to Base64 */
//            profilePic = Base64.encodeToString(profilePic.getBytes(), Base64.DEFAULT);
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(selectedImage);
                byte[] inputData = getBytes(inputStream);
                profilePic = java.util.Base64.getEncoder().encodeToString(inputData);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                final InputStream is = getContentResolver().openInputStream(selectedImage);
//                Bitmap bit = BitmapFactory.decodeStream(is);
//                profilePic = BitMapToString(bit);
//            } catch (FileNotFoundException f) {
//                f.printStackTrace();
//            }
        }
    }

    public void displayButton(boolean setClickable) {
        btnSignUp.setEnabled(setClickable);
    }


    @Override
    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void signUpSuccessful(String s3Pic) {
        User rootUser = new User(mNewHandle.getText().toString(), firstName, lastName, email);
        rootUser.setProfilePic(s3Pic);
        ModelSingleton.setmUser(rootUser);

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, length);
        }

        return byteBuffer.toByteArray();
    }

//    public String BitMapToString(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
//        byte[] b = baos.toByteArray();
//        String temp = Base64.encodeToString(b, Base64.DEFAULT);
//        return temp;
//    }
}
