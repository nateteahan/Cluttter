package com.example.clutter.View;


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
import com.example.clutter.Presenter.FollowerPresenter;
import com.example.clutter.R;
import com.squareup.picasso.Picasso;

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

    private class FollowerHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;

        public FollowerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.follow_list_layout, parent, false));

            image = itemView.findViewById(R.id.ivFollower);
            name = itemView.findViewById(R.id.tvFollowName);
        }

        protected void bind (FollowInfo info) {
//            Drawable drawable = getResources().getDrawable(R.drawable.ic_user);
//            image.setImageDrawable(drawable);
            Picasso.get().load(info.profilePic).centerCrop()
                    .transform(new RoundedTransformation(24, 24))
                    .fit()
                    .into(image);
            ;
            name.setText(info.getName());

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name.getText().toString());
                    bundle.putInt("picture", R.drawable.ic_user);

                    AccountFragment fragment = new AccountFragment();
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment);
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
        mRecyclerView = v.findViewById(R.id.rvFollowInfo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        presenter = new FollowerPresenter(this);
        presenter.createDummyFollowers();

        return v;
    }

    @Override
    public void displayFollowInfo(List<FollowInfo> followers) {
        //RecyclerView
        mAdapter = new FollowAdapter(followers);
        mRecyclerView.setAdapter(mAdapter);
    }

}
