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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clutter.InterfaceMVP.FollowMvp;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.Presenter.FollowingPresenter;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment implements FollowMvp.View {
    private FollowingPresenter presenter;
    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    private FollowingAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String handle;

    private class FollowingHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;

        public FollowingHolder(LayoutInflater inflater, ViewGroup parent) {
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

    private class FollowingAdapter extends RecyclerView.Adapter<FollowingHolder> {
        private List<FollowInfo> follows;

        public FollowingAdapter(List<FollowInfo> follows) {
            this.follows = follows;
        }

        @NonNull
        @Override
        public FollowingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FollowingHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowingHolder followingHolder, int position) {
            FollowInfo followInfo = follows.get(position);
            followingHolder.bind(followInfo);
        }

        @Override
        public int getItemCount() {
            return follows.size();
        }
    }

    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_following, container, false);
        handle = getArguments().getString("handle");

        mRecyclerView = v.findViewById(R.id.rvFollowing);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        presenter = new FollowingPresenter(this);
        presenter.getFollowees(handle);
        return v;
    }

    @Override
    public void displayFollowInfo(List<FollowInfo> following) {
        //RecyclerView
        mAdapter = new FollowingAdapter(following);
        mRecyclerView.setAdapter(mAdapter);
    }
}
