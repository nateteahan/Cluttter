package com.example.clutter.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.clutter.InterfaceMVP.UserMVP;
import com.example.clutter.Model.ModelSingleton;
import com.example.clutter.Model.Status;
import com.example.clutter.Model.UserInfo;
import com.example.clutter.Presenter.UserPresenter;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.example.clutter.Transformations.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserActivity extends AppCompatActivity implements UserMVP.View {
    private TextView tvFollowers;
    private TextView tvHandle;
    private StatusAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private UserPresenter presenter;
    private ImageView ivPic;
    private TextView tvFirstName;
    private TextView tvLastName;
    private Button btnFollow;
    private Button btnLoad;
    private String lastKey;
    private String userHandle;
    private List<Status> statuses;


    private class StatusResultHolder extends RecyclerView.ViewHolder {
        private ImageView profilePic;
        private TextView name;
        private TextView handle;
        private TextView time;
        private TextView status;
        private ImageView photoAttachment;
//        private VideoView videoAttachment;

        public StatusResultHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.status_layout, parent, false));

            profilePic = itemView.findViewById(R.id.ivProfilePic);
            name = itemView.findViewById(R.id.tvStatusName);
            handle = itemView.findViewById(R.id.tvStatusHandle);
            time = itemView.findViewById(R.id.tvStatusTime);
            status = itemView.findViewById(R.id.tvStatusMessage);
            photoAttachment = itemView.findViewById(R.id.ivPhotoAttach);
//            videoAttachment = itemView.findViewById(R.id.vvVideoAttach);
        }

        protected void bind(final Status currentStatus) throws IOException {
            String profilePicPath = currentStatus.getProfilePic();
            Picasso.get().load(profilePicPath)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .fit()
                    .into(profilePic);
            name.setText(currentStatus.getFirstName());
            handle.setText(currentStatus.getUserHandle());
            time.setText(currentStatus.getTime());
            status.setText(currentStatus.getStatus());
            photoAttachment.setVisibility(View.GONE);
//            videoAttachment.setVisibility(View.GONE);
//
            if (currentStatus.getImageAttachment() != null) {
                photoAttachment.setVisibility(View.VISIBLE);
//                videoAttachment.setVisibility(View.GONE);

                Picasso.get().load(currentStatus.getImageAttachment())
                        .centerCrop()
                        .transform(new RoundedTransformation(12, 12))
                        .fit()
                        .into(photoAttachment);

            }

            if (currentStatus.getVideoAttachment() != null) {

                photoAttachment.setVisibility(View.VISIBLE);

                Glide.with(getApplicationContext())
                        .load(currentStatus.getVideoAttachment())
                        .into(photoAttachment);
            }

            Pattern usernamePattern = Pattern.compile("@+[a-zA-Z0-9]*");
            Linkify.addLinks(status, usernamePattern, "input.my.scheme://"); //Goto androidmanifest.xml and look at the scheme of the UserActivity

            Pattern hashtagPattern = Pattern.compile("#+[a-zA-Z0-9]*");
            Linkify.addLinks(status, hashtagPattern, "input.hashtag.scheme://");


            handle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AccountFragment accountFrag = new AccountFragment();
//                    FragmentManager fm = getFragmentManager();
//                    fm.beginTransaction().replace(R.id.fragment_container, accountFrag).addToBackStack(null).commit();
                }
            });

            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), StatusActivity.class);
                    intent.putExtra("PIC", currentStatus.getProfilePic());
                    intent.putExtra("STATUS", status.getText().toString());
                    intent.putExtra("NAME", name.getText().toString());
                    intent.putExtra("HANDLE", "@" + userHandle);
                    intent.putExtra("TIME", time.getText().toString());
                    intent.putExtra("IMAGE", currentStatus.getImageAttachment());
                    intent.putExtra("VIDEO", currentStatus.getVideoAttachment());
                    startActivity(intent);
                }
            });
        }
    }

    private class StatusAdapter extends RecyclerView.Adapter<StatusResultHolder> {
        private List<Status> results;

        public StatusAdapter(List<Status> feedResults) {
            results = feedResults;
        }

        @NonNull
        @Override
        public StatusResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

            return new StatusResultHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull StatusResultHolder feedResultHolder, int position) {
            try {
                Status status = results.get(position);
                feedResultHolder.bind(status);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {

            int size = results.size();

            return size;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Uri data = getIntent().getData();

        // UserActivity was launched from a status link click
        if (data != null) {
            String[] strip_scheme = data.toString().split("//");
            userHandle = strip_scheme[1].replace("@", "");
        }
        //UserActivity was launched from a handle click in a view/fragment
        else {
            userHandle = getIntent().getStringExtra("userHandle").replace("@", "");
        }

        statuses = new ArrayList<>();
        tvFollowers = findViewById(R.id.tvFollowers);
        tvHandle = findViewById(R.id.tvUser);
        ivPic = findViewById(R.id.ivUserAccount);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        btnFollow = findViewById(R.id.btnFollow);
        btnLoad = findViewById(R.id.button6);
        lastKey = null;
        mRecyclerView = findViewById(R.id.rvStory);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new UserPresenter(this, userHandle);

        // If the page we are on is the logged-in user's page, make the follow option go away
        if (userHandle.equals(ModelSingleton.getmUser().getUserHandle())) {
            btnFollow.setVisibility(View.GONE);
        }
        else {
            presenter.isFollowing(ModelSingleton.getmUser().getUserHandle(), userHandle);
        }

        presenter.createDummyData(statuses, userHandle, lastKey);
        presenter.getUserInfo();

        tvFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, FollowActivity.class);
                intent.putExtra("handle", userHandle);
                startActivity(intent);
            }
        });

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnFollow.getText().toString().equals("Follow")) {
                    presenter.followUser();
                }
                else {
                    presenter.unfollowUser();
                }
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call presenter
                presenter.createDummyData(statuses, userHandle, lastKey);
            }
        });
    }


    @Override
    public void displayStatuses(List<Status> statuses, String key) {
        this.lastKey = key;

        if (key == null) {
            //Done fetching statuses
            btnLoad.setVisibility(View.GONE);
        }

        mAdapter = new StatusAdapter(statuses);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void assignUserFields(UserInfo info) {
        String profilePicPath = info.getProfilePic();
        Picasso.get().load(profilePicPath)
                    .centerCrop()
                    .transform(new RoundedTransformation(24, 24))
                    .fit()
                    .into(ivPic);
        tvHandle.setText(info.getUserHandle());
        tvFirstName.setText(info.getFirstName());
        tvLastName.setText(info.getLastName());
    }

    public void followUser(String message) {
        btnFollow.setText(R.string.unfollow);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void unfollowUser(String message) {
        btnFollow.setText(R.string.follow);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setButtonState(String message) {
        switch (message) {
            default:
                btnFollow.setText(R.string.error);
                break;
            case "False":
                btnFollow.setText(R.string.followBtn);
                break;
            case "True":
                btnFollow.setText(R.string.unfollowBtn);
                break;
        }
    }
}
