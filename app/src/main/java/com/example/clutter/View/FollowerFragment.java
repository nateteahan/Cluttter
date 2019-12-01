package com.example.clutter.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clutter.InterfaceMVP.FollowMvp;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.Presenter.FollowerPresenter;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerFragment extends Fragment implements FollowMvp.View {
        private FollowerPresenter presenter;
        private FragmentManager fragmentManager;
        private RecyclerView recyclerView;
        private FollowAdapter mAdapter;
        private RecyclerView mRecyclerView;
        private String userHandle;
        private String lastKey;
        private Button mLoadButton;
        private List<FollowInfo> followers;

    private class FollowerHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;

        public FollowerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.follow_list_layout, parent, false));

            image = itemView.findViewById(R.id.ivFollower);
            name = itemView.findViewById(R.id.tvFollowName);
        }

        protected void bind (FollowInfo info) {
            Picasso.get().load(info.profilePic).centerCrop()
                    .transform(new CircleTransform())
                    .fit()
                    .into(image);
            ;
            name.setText(info.getName());

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    intent.putExtra("userHandle", name.getText().toString());
                    startActivity(intent);
                }
            });
        }
    }

    private class FollowAdapter extends RecyclerView.Adapter<FollowerHolder> {
        private List<FollowInfo> follows;

        public FollowAdapter(List<FollowInfo> follows) {
            this.follows = follows;
        }

        @NonNull
        @Override
        public FollowerFragment.FollowerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FollowerHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowerFragment.FollowerHolder followHolder, int position) {
            FollowInfo followInfo = follows.get(position);
            followHolder.bind(followInfo);
        }

        @Override
        public int getItemCount() {
            return follows.size();
        }
    }


    public FollowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_follow, container, false);
        userHandle = getArguments().getString("handle");
        userHandle = userHandle.replaceAll("@", "");
        lastKey = null;
        mLoadButton = v.findViewById(R.id.button8);
        mLoadButton.setVisibility(View.VISIBLE);
        followers = new ArrayList<>();

        mRecyclerView = v.findViewById(R.id.rvFollowInfo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        presenter = new FollowerPresenter(this);
        presenter.getFollowers(followers, userHandle, lastKey);


        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getFollowers(followers, userHandle, lastKey);
            }
        });

        return v;
    }

    @Override
    public void displayFollowInfo(List<FollowInfo> followers, String lastKey) {
        //RecyclerView
        this.lastKey = lastKey;
        this.followers = followers;

        if (this.lastKey == null) {
            mLoadButton.setVisibility(View.GONE);
        }

        mAdapter = new FollowAdapter(followers);
        mRecyclerView.setAdapter(mAdapter);
    }

}
