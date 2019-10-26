package com.example.clutter.View;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clutter.InterfaceMVP.ComposeMessageMVP;
import com.example.clutter.Presenter.ComposeMessagePresenter;
import com.example.clutter.R;

public class ComposeMessageActivity extends AppCompatActivity implements ComposeMessageMVP.View {
    private ComposeMessagePresenter presenter;
    private TextInputEditText mInputText;
    private ImageView mAddAttachment;
    private ImageButton mSendMessage;
    private TextView mCharCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        presenter = new ComposeMessagePresenter(this);
        mInputText = findViewById(R.id.textInputEditText);
        mAddAttachment = findViewById(R.id.ivAttach);
        mSendMessage = findViewById(R.id.ibSend);
        mCharCount = findViewById(R.id.tvCharacterCount);

        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Presenter character count
                presenter.checkCharacterCount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkValidInput(mInputText.getText().toString());
            }
        });


    }

    @Override
    public void displayMessage(String message) {

        if (message.equals("Successful Upload")) {
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

