package com.example.clutter.View;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clutter.InterfaceMVP.ComposeMessageMVP;
import com.example.clutter.Model.ModelSingleton;
import com.example.clutter.Presenter.ComposeMessagePresenter;
import com.example.clutter.R;
import com.example.clutter.sdk.model.SendStatusRequest;

import java.util.Calendar;
import java.util.Date;

public class ComposeMessageActivity extends AppCompatActivity implements ComposeMessageMVP.View {
    private ComposeMessagePresenter presenter;
    private TextInputEditText mInputText;
    private ImageView mAddAttachment;
    private ImageButton mSendMessage;
    private TextView mCharCount;
    private String status;
    private String handle;
    private String profilePic;
    private String imageAttachment;
    private String videoAttachment;
    private String time;
    private String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        presenter = new ComposeMessagePresenter(this);
        mInputText = findViewById(R.id.textInputEditText);
        mAddAttachment = findViewById(R.id.ivAttach);
        mSendMessage = findViewById(R.id.ibSend);
        mCharCount = findViewById(R.id.tvCharacterCount);
        handle = ModelSingleton.getmUser().getUserHandle();
        firstName = ModelSingleton.getmUser().getFirstName();
        profilePic = ModelSingleton.getmUser().getProfilePic();

        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Presenter character count
                status = s.toString();
                presenter.checkCharacterCount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hours = calendar.get(Calendar.HOUR);
                int minutes = calendar.get(Calendar.MINUTE);
                time = Integer.toString(hours) + ":" + Integer.toString(minutes);

                /* Make SDK Status object */
                SendStatusRequest statusRequest = new SendStatusRequest();
                statusRequest.setUserHandle(handle);
                statusRequest.setFirstName(firstName);
                statusRequest.setProfilePic(profilePic);
                statusRequest.setTime(time);
                statusRequest.setStatus(status);
                statusRequest.setImageAttachment(imageAttachment);
                statusRequest.setVideoAttachment(videoAttachment);

                presenter.checkValidInput(statusRequest);
            }
        });


    }

    @Override
    public void displayMessage(String message) {

        if (message.equals("Successfully posted status!")) {
            Toast.makeText(this, "Successfully posted status", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void displayCharacterCount(String countMessage) {
        mCharCount.setText(countMessage);
    }

}

