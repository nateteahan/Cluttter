package com.example.clutter.View;


import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import com.example.clutter.InterfaceMVP.AccountFragmentMVP;
import com.example.clutter.Presenter.AccountPresenter;
import com.example.clutter.Presenter.StoryPresenter;
import com.example.clutter.R;

import java.util.List;
import com.example.clutter.Model.Status;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements AccountFragmentMVP.View {
    private StoryAdapter mAdapter;
    private StoryPresenter storyPresenter;
    private ImageView imageView;
    private TextView mFollowers;
    private TextView mFollowees;
    private RecyclerView mRecyclerView;
    private Button btnSignOut;
    private TextView tvChangePic;
//    private FollowAdapter mAdapter;
    private AccountPresenter presenter;
    private FragmentManager fragmentManager;

//    private class FollowHolder extends RecyclerView.ViewHolder {
//        private ImageView image;
//        private TextView name;
//
//        public FollowHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.follow_list_layout, parent, false));
//
//            image = itemView.findViewById(R.id.ivFollower);
//            name = itemView.findViewById(R.id.tvFollowName);
//        }
//
//        protected void bind (FollowInfo info) {
//            Drawable drawable = getResources().getDrawable(R.drawable.ic_user);
//            image.setImageDrawable(drawable);
//            name.setText(info.getName());
//
//            name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("name", name.getText().toString());
//                    bundle.putInt("picture", R.drawable.ic_user);
//
//                    AccountFragment fragment = new AccountFragment();
//                    fragment.setArguments(bundle);
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment);
//                }
//            });
//        }
//    }
//
//    private class FollowAdapter extends RecyclerView.Adapter<FollowHolder> {
//        private List<FollowInfo> follows;
//
//        public FollowAdapter(List<FollowInfo> follows) {
//            this.follows = follows;
//        }
//
//        @NonNull
//        @Override
//        public FollowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            return new FollowHolder(layoutInflater, viewGroup);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull FollowHolder followHolder, int position) {
//            FollowInfo followInfo = follows.get(position);
//            followHolder.bind(followInfo);
//        }
//
//        @Override
//        public int getItemCount() {
//            return follows.size();
//        }
//    }

    private class StoryResultHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView handle;
        private TextView time;
        private TextView status;

        public StoryResultHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.story_layout, parent, false));

            imageView = itemView.findViewById(R.id.ivStory);
            name = itemView.findViewById(R.id.tvStoryName);
            handle = itemView.findViewById(R.id.tvStoryHandle);
            time = itemView.findViewById(R.id.tvStoryTime);
            status = itemView.findViewById(R.id.tvStory_Status);
        }

        protected void bind(Status currentStatus) {
            Drawable drawable = getResources().getDrawable(R.drawable.me);
            imageView.setImageDrawable(drawable);
            name.setText(currentStatus.getUserFirstName());
            handle.setText(currentStatus.getUserHandle());
            time.setText(currentStatus.getTime());
            status.setText(currentStatus.getStatus());

        }
    }

    private class StoryAdapter extends RecyclerView.Adapter<StoryResultHolder> {
        private List<Status> results;

        public StoryAdapter(List<Status> storyResults) {
            this.results = storyResults;
        }

        @NonNull
        @Override
        public StoryResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new StoryResultHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull StoryResultHolder storyResultHolder, int position) {
            Status status = results.get(position);
            storyResultHolder.bind(status);
        }

        @Override
        public int getItemCount() {
            return results.size();
        }
    }

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        imageView = v.findViewById(R.id.ivUserAccount);
        mFollowers = v.findViewById(R.id.tvNumFollowers);
        mFollowees = v.findViewById(R.id.tvNumFollowing);
//        mRecyclerView= v.findViewById(R.id.rvFollows);
        btnSignOut = v.findViewById(R.id.button3);
        tvChangePic = v.findViewById(R.id.textView6);
        mRecyclerView= v.findViewById(R.id.rvStory);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        presenter = new AccountPresenter(this);

        storyPresenter = new StoryPresenter(this);
        storyPresenter.createDummyData();

        Bundle bundle = getArguments();
        if (bundle != null) {
            int image = bundle.getInt("picture");
            Drawable drawable = getResources().getDrawable(image);
            imageView.setImageDrawable(drawable);

        }

        mFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display followers
//                presenter.createDummyFollowers();
                /* LAUNCH TO NEW ACTIVITY */
                Intent intent = new Intent(getActivity(), FollowActivity.class);
                startActivity(intent);
            }
        });

        mFollowees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display followees
//                presenter.createDummyFollowees();
                /* LAUNCH TO NEW ACTIVITY */
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }

    public void displayStories(List<Status> stories) {
        //RecyclerView
        mAdapter = new StoryAdapter(stories);
        mRecyclerView.setAdapter(mAdapter);
    }

}
