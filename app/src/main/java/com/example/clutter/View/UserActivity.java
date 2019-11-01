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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.clutter.InterfaceMVP.UserMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.Presenter.UserPresenter;
import com.example.clutter.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class UserActivity extends AppCompatActivity implements UserMVP.View {
    private TextView tvFollowers;
    private TextView tvHandle;
    private StatusAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private UserPresenter presenter;


    private class StatusResultHolder extends RecyclerView.ViewHolder {
        private ImageView profilePic;
        private TextView name;
        private TextView handle;
        private TextView time;
        private TextView status;
        private ImageView photoAttachment;
        private VideoView videoAttachment;

        public StatusResultHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.status_layout, parent, false));

            profilePic = itemView.findViewById(R.id.ivProfilePic);
            name = itemView.findViewById(R.id.tvStatusName);
            handle = itemView.findViewById(R.id.tvStatusHandle);
            time = itemView.findViewById(R.id.tvStatusTime);
            status = itemView.findViewById(R.id.tvStatusMessage);
            photoAttachment = itemView.findViewById(R.id.ivPhotoAttach);
            videoAttachment = itemView.findViewById(R.id.vvVideoAttach);
        }

        protected void bind(Status currentStatus) throws IOException {
            name.setText(currentStatus.getFirstName());
            handle.setText(currentStatus.getUserHandle());
            time.setText(currentStatus.getTime());
            status.setText(currentStatus.getStatus());
            photoAttachment.setVisibility(View.GONE);
            videoAttachment.setVisibility(View.GONE);
//
            if (currentStatus.getImageAttachment() != null) {
                photoAttachment.setVisibility(View.VISIBLE);
                videoAttachment.setVisibility(View.GONE);

                Picasso.get().load(currentStatus.getImageAttachment())
                        .centerCrop()
                        .fit()
                        .into(photoAttachment);

            }

            if (currentStatus.getVideoAttachment() != null) {
                //inflate photoAttachment
//                Picasso.get().setLoggingEnabled(true);
//                Picasso.get().load(currentStatus.getImageAttachment())
//                                                        .centerCrop()
//                                                        .fit()
//                                                        .into(photoAttachment);
//                Bitmap x = presenter.setImageAttachment();
                //Drawable pic = presenter.LoadImageFromWebOperations(currentStatus.getImageAttachment());
                photoAttachment.setVisibility(View.GONE);
//                photoAttachment.setImageDrawable(pic);
                videoAttachment.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(currentStatus.getVideoAttachment());
                videoAttachment.setVideoURI(uri);
                videoAttachment.start();
            }
//            photoAttachment.setText(currentStatus.getImageAttachment());
//            videoAttachment.setText(currentStatus.getImageAttachment());

//            //Handle attachments visibility
//            if (currentStatus != null) {
//                Drawable drawable1 = getResources().getDrawable(R.drawable.camera_logo);
//                photoAttachment.setImageDrawable(drawable1);
////                photoAttachment = currentStatus.getImageAttachment();
////                photoAttachment.setImageDrawable(currentStatus.getImageAttachment());
//                videoAttachment.setVisibility(View.VISIBLE);
//                photoAttachment.setVisibility(View.GONE);
////                videoAttachment.setVisibility(View.GONE);
//            }
//            else if (currentStatus.getVideoAttachment() != null) {
////                videoAttachment = currentStatus.getVideoAttachment();
//                videoAttachment.setVisibility(View.VISIBLE);
//                photoAttachment.setVisibility(View.GONE);
//            }
//            else {
//                photoAttachment.setVisibility(View.GONE);
//                videoAttachment.setVisibility(View.GONE);
//            }


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
                    intent.putExtra("STATUS", status.getText().toString());
                    intent.putExtra("NAME", name.getText().toString());
                    intent.putExtra("HANDLE", handle.getText().toString());
                    intent.putExtra("TIME", time.getText().toString());
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

        tvFollowers = findViewById(R.id.tvNumFollowers);
        tvHandle = findViewById(R.id.tvUser);
        mRecyclerView = findViewById(R.id.rvStory);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new UserPresenter(this);

        String userHandle = "";

        //Parse data to determine the userhandle clicked on
        Uri data = getIntent().getData();
        if (data!=null) {
            if (data.toString().contains("input.my.scheme://")){
                String[] strip_id = data.toString().split("//");
                userHandle = strip_id[1];
            }
        }
        tvHandle.setText(userHandle);


        presenter.createDummyData();
    }


    @Override
    public void displayStatuses(List<Status> statuses) {
        mAdapter = new StatusAdapter(statuses);
        mRecyclerView.setAdapter(mAdapter);
    }

}
