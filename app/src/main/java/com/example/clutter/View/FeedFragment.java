package com.example.clutter.View;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.clutter.InterfaceMVP.FeedFragmentMVP;
import com.example.clutter.Presenter.FeedPresenter;
import com.example.clutter.R;

import java.util.List;
import java.util.regex.Pattern;

import com.example.clutter.Model.Status;

public class FeedFragment extends Fragment implements FeedFragmentMVP.View {
    private RecyclerView mRecyclerView;
    private FeedPresenter presenter;
    private FeedAdapter mAdapter;

    private class FeedResultHolder extends RecyclerView.ViewHolder {
        private ImageView profilePic;
        private TextView name;
        private TextView handle;
        private TextView time;
        private TextView status;
        private ImageView photoAttachment;
        private VideoView videoAttachment;

        public FeedResultHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.status_layout, parent, false));

            profilePic = itemView.findViewById(R.id.ivProfilePic);
            name = itemView.findViewById(R.id.tvStatusName);
            handle = itemView.findViewById(R.id.tvStatusHandle);
            time = itemView.findViewById(R.id.tvStatusTime);
            status = itemView.findViewById(R.id.tvStatusMessage);
            photoAttachment = itemView.findViewById(R.id.photoAttach);
            videoAttachment = itemView.findViewById(R.id.videoAttach);
        }

        protected void bind(Status currentStatus) {
            Drawable drawable = getResources().getDrawable(R.drawable.me);
            profilePic.setImageDrawable(drawable);
            name.setText(currentStatus.getUserFirstName());
            handle.setText(currentStatus.getUserHandle());
            time.setText(currentStatus.getTime());
            status.setText(currentStatus.getStatus());

            //Handle attachments visibility
            if (currentStatus.getImageAttachment() != null) {
                Drawable drawable1 = getResources().getDrawable(R.drawable.camera_logo);
                photoAttachment.setImageDrawable(drawable1);
//                photoAttachment = currentStatus.getImageAttachment();
//                photoAttachment.setImageDrawable(currentStatus.getImageAttachment());
                videoAttachment.setVisibility(View.VISIBLE);
                photoAttachment.setVisibility(View.GONE);
//                videoAttachment.setVisibility(View.GONE);
            }
            else if (currentStatus.getVideoAttachemnt() != null) {
                videoAttachment = currentStatus.getVideoAttachemnt();
                videoAttachment.setVisibility(View.VISIBLE);
                photoAttachment.setVisibility(View.GONE);
            }
            else {
                photoAttachment.setVisibility(View.GONE);
                videoAttachment.setVisibility(View.GONE);
            }


            Pattern usernamePattern = Pattern.compile("@+[a-zA-Z0-9]*");
            Linkify.addLinks(status, usernamePattern, "https://www.google.com/search?q=cute+dogs&sxsrf=ACYBGNRUBLAZdG893VLA9PYXtmv_ihmvqw:1571980349732&source=lnms&tbm=isch&sa=X&ved=0ahUKEwj2utuS07blAhWVvZ4KHfK4CSgQ_AUIEigB&biw=1440&bih=789");

            Pattern hashtagPattern = Pattern.compile("#+[a-zA-Z0-9]*");
            Linkify.addLinks(status, hashtagPattern, "https://www.google.com/search?q=cute+dogs&sxsrf=ACYBGNRUBLAZdG893VLA9PYXtmv_ihmvqw:1571980349732&source=lnms&tbm=isch&sa=X&ved=0ahUKEwj2utuS07blAhWVvZ4KHfK4CSgQ_AUIEigB&biw=1440&bih=789");


            handle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountFragment accountFrag = new AccountFragment();
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.fragment_container, accountFrag).addToBackStack(null).commit();
                }
            });

            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // FIXME need to pass a bundle with the current status info to the status activity
                    Intent intent = new Intent(getActivity(), StatusActivity.class);
                    intent.putExtra("STATUS", status.getText().toString());
                    intent.putExtra("NAME", name.getText().toString());
                    intent.putExtra("HANDLE", handle.getText().toString());
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
            Status status = results.get(position);
            feedResultHolder.bind(status);
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
        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter = new FeedPresenter(this);
        presenter.createDummyData();

        return v;
    }

    public void displayStatus(List<Status> statuses) {
        mAdapter = new FeedAdapter(statuses);
        mRecyclerView.setAdapter(mAdapter);
    }

}
