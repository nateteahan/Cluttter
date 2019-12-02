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

import com.bumptech.glide.Glide;
import com.example.clutter.InterfaceMVP.HashtagMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.Presenter.HashtagPresenter;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.example.clutter.Transformations.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HashtagActivity extends AppCompatActivity implements HashtagMVP.View {
    private RecyclerView mRecyclerView;
    private TextView mHashtag;
    private HashtagPresenter presenter;
    private HashtagAdapter mAdapter;
    private String lastKey;
    private String Hashtag;
    private List<Status> statuses;
    private Button mLoadBtn;

    private class HashtagResultHolder extends RecyclerView.ViewHolder {
        private ImageView profilePic;
        private TextView name;
        private TextView handle;
        private TextView time;
        private TextView status;
        private ImageView photoAttachment;
//        private VideoView videoAttachment;

        public HashtagResultHolder(LayoutInflater inflater, ViewGroup parent) {
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
            handle.setText("@" + currentStatus.getUserHandle());
            time.setText(currentStatus.getTime());
            status.setText(currentStatus.getStatus());
            photoAttachment.setVisibility(View.GONE);
//            videoAttachment.setVisibility(View.GONE);

            if (currentStatus.getImageAttachment() != null) {
                photoAttachment.setVisibility(View.VISIBLE);
//                videoAttachment.setVisibility(View.GONE);

                Picasso.get().load(currentStatus.getImageAttachment())
                        .centerCrop()
                        .transform(new RoundedTransformation(12, 12))
                        .fit()
                        .into(photoAttachment);

            }
            else if (currentStatus.getVideoAttachment() != null) {
                photoAttachment.setVisibility(View.VISIBLE);

                Glide.with(getApplicationContext())
                        .load(currentStatus.getVideoAttachment())
                        .into(photoAttachment);
//                videoAttachment.setVisibility(View.VISIBLE);

                //MediaController
//                MediaController mediaController = new MediaController(getApplicationContext());
//                mediaController.setVisibility(View.GONE);
//                mediaController.setAnchorView(videoAttachment);
//                Uri video = Uri.parse(currentStatus.getVideoAttachment());
//                videoAttachment.setVideoURI(video);
//                videoAttachment.start();
            }

            Pattern usernamePattern = Pattern.compile("@+[a-zA-Z0-9]*");
            Linkify.addLinks(status, usernamePattern, "input.my.scheme://"); //Goto androidmanifest.xml and look at the scheme of the UserActivity

            Pattern hashtagPattern = Pattern.compile("#+[a-zA-Z0-9]*");
            Linkify.addLinks(status, hashtagPattern, "input.hashtag.scheme://");


            handle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Start UserActivity
                    Intent intent = new Intent(HashtagActivity.this, UserActivity.class);
                    intent.putExtra("userHandle", handle.getText().toString());
                    startActivity(intent);
                }
            });

            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HashtagActivity.this, StatusActivity.class);
                    intent.putExtra("PIC", currentStatus.getProfilePic());
                    intent.putExtra("STATUS", status.getText().toString());
                    intent.putExtra("NAME", name.getText().toString());
                    intent.putExtra("HANDLE", handle.getText().toString());
                    intent.putExtra("TIME", time.getText().toString());
                    intent.putExtra("IMAGE", currentStatus.getImageAttachment());
                    intent.putExtra("VIDEO", currentStatus.getVideoAttachment());
                    startActivity(intent);
                }
            });

        }
    }

    private class HashtagAdapter extends RecyclerView.Adapter<HashtagResultHolder> {
        private List<Status> results;

        public HashtagAdapter(List<Status> hashtagResults) {
            results = hashtagResults;
        }

        @NonNull
        @Override
        public HashtagResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

            return new HashtagResultHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull HashtagResultHolder hashtagResultHolder, int position) {
            try {
                Status status = results.get(position);
                hashtagResultHolder.bind(status);
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
        setContentView(R.layout.activity_hashtag);

        mHashtag = findViewById(R.id.tvHashtag);
        mLoadBtn = findViewById(R.id.button9);

        String hashtag = "";
        Uri data = getIntent().getData();
        if (data!=null) {
            if (data.toString().contains("input.hashtag.scheme://")){
                String[] strip_id = data.toString().split("//");
                hashtag = strip_id[1].replace("#", "");
            }
        }

        mHashtag.setText(hashtag);
        Hashtag = mHashtag.getText().toString();
        lastKey = null;
        statuses = new ArrayList<>();

        mRecyclerView = findViewById(R.id.rvHashtag);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new HashtagPresenter(this);
        presenter.createDummyData(statuses, Hashtag, lastKey);

        mLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createDummyData(statuses, Hashtag, lastKey);
            }
        });
    }

    @Override
    public void displayHashtagStatuses(List<Status> statuses, String key) {
        this.lastKey = key;
        this.statuses = statuses;

        if (key == null) {
            //Done fetching statuses
            mLoadBtn.setVisibility(View.GONE);
        }

        mAdapter = new HashtagAdapter(statuses);
        mRecyclerView.setAdapter(mAdapter);
    }
}
