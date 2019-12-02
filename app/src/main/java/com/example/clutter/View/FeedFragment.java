package com.example.clutter.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
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
import com.example.clutter.InterfaceMVP.FeedFragmentMVP;
import com.example.clutter.Model.ModelSingleton;
import com.example.clutter.Model.Status;
import com.example.clutter.Presenter.FeedPresenter;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.example.clutter.Transformations.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FeedFragment extends Fragment implements FeedFragmentMVP.View {
    private RecyclerView mRecyclerView;
    private FeedPresenter presenter;
    private FeedAdapter mAdapter;
    private ConstraintLayout mNoFeedLayout;
    private ConstraintLayout mFeedLayout;
    private Button mLoadButton;
    private String lastkey;
    private List<Status> statuses;

    private class FeedResultHolder extends RecyclerView.ViewHolder {
        private ImageView profilePic;
        private TextView name;
        private TextView handle;
        private TextView time;
        private TextView status;
        private ImageView photoAttachment;

        public FeedResultHolder(LayoutInflater inflater, ViewGroup parent) {
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
            final String profilePicture = currentStatus.getProfilePic();
            Picasso.get().load(profilePicture)
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

            // Checks for image and photo attachments
            if (currentStatus.getImageAttachment() != null) {
                photoAttachment.setVisibility(View.VISIBLE);
//                videoAttachment.setVisibility(View.GONE);

                Picasso.get().load(currentStatus.getImageAttachment())
                        .centerCrop()
                        .transform(new RoundedTransformation(24, 24))
                        .fit()
                        .into(photoAttachment);

            }

            if (currentStatus.getVideoAttachment() != null) {
                photoAttachment.setVisibility(View.VISIBLE);
//                videoAttachment.setVisibility(View.GONE);

                Glide.with(getContext())
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
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    intent.putExtra("userHandle", handle.getText().toString());
                    startActivity(intent);
                }
            });

            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), StatusActivity.class);
                    intent.putExtra("PIC", profilePicture);
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

    private class FeedAdapter extends RecyclerView.Adapter<FeedResultHolder> {
        private List<Status> results;

        public FeedAdapter(List<Status> feedResults) {
            results = feedResults;
        }

        @NonNull
        @Override
        public FeedResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new FeedResultHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedResultHolder feedResultHolder, int position) {
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

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        mNoFeedLayout = v.findViewById(R.id.feed_layout);
        mFeedLayout = v.findViewById(R.id.boss);
        mLoadButton = v.findViewById(R.id.button7);
        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        statuses = new ArrayList<>();
        lastkey = null;

        List<Status> list = new ArrayList<>();
        mAdapter = new FeedAdapter(list);
        mRecyclerView.setAdapter(mAdapter);

        presenter = new FeedPresenter(this);
        presenter.createDummyData(statuses, ModelSingleton.getmUser().getUserHandle(), lastkey);

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createDummyData(statuses, ModelSingleton.getmUser().getUserHandle(), lastkey);
            }
        });
        return v;
    }

    public void displayStatus(List<Status> statuses, String lastkey) {
        this.lastkey = lastkey;
        if (lastkey == null) {
            //Done fetching statuses
            mLoadButton.setVisibility(View.GONE);
        }

        this.statuses = statuses;

        mFeedLayout.setVisibility(View.VISIBLE);
        mNoFeedLayout.setVisibility(View.GONE);

        mAdapter = new FeedAdapter(statuses);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void emptyFeed() {
        mFeedLayout.setVisibility(View.GONE);
        mNoFeedLayout.setVisibility(View.VISIBLE);
    }
}