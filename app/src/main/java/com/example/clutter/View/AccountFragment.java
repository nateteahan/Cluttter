package com.example.clutter.View;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.clutter.InterfaceMVP.StoryFragmentMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.Presenter.AccountPresenter;
import com.example.clutter.Presenter.StoryPresenter;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.example.clutter.Transformations.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements StoryFragmentMVP.View {
    private static final int REQUEST_UPLOAD_IMAGE = 1;
    private StoryAdapter mAdapter;
    private StoryPresenter storyPresenter;
    private ImageView imageView;
    private TextView mFollowers;
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
        private ImageView imageAttachment;
        private VideoView videoAttachment;

        public StoryResultHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.status_layout, parent, false));

            imageView = itemView.findViewById(R.id.ivProfilePic);
            name = itemView.findViewById(R.id.tvStatusName);
            handle = itemView.findViewById(R.id.tvStatusHandle);
            time = itemView.findViewById(R.id.tvStatusTime);
            status = itemView.findViewById(R.id.tvStatusMessage);
            imageAttachment = itemView.findViewById(R.id.ivPhotoAttach);
            videoAttachment = itemView.findViewById(R.id.vvVideoAttach);
        }

        protected void bind(Status currentStatus) {
            String profilePicPath = currentStatus.getProfilePic();
            Picasso.get().load(profilePicPath)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .fit()
                    .into(imageView);
            name.setText(currentStatus.getFirstName());
            handle.setText(currentStatus.getUserHandle());
            time.setText(currentStatus.getTime());
            status.setText(currentStatus.getStatus());

            // Checks for image and photo attachments
            if (currentStatus.getImageAttachment() != null) {
                imageAttachment.setVisibility(View.VISIBLE);
                videoAttachment.setVisibility(View.GONE);

                Picasso.get().load(currentStatus.getImageAttachment())
                        .centerCrop()
                        .transform(new RoundedTransformation(24, 24))
                        .fit()
                        .into(imageAttachment);

            }

            if (currentStatus.getVideoAttachment() != null) {
                imageAttachment.setVisibility(View.GONE);
                videoAttachment.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(currentStatus.getVideoAttachment());
                videoAttachment.setVideoURI(uri);
                videoAttachment.start();
            }

            Pattern usernamePattern = Pattern.compile("@+[a-zA-Z0-9]*");
            Linkify.addLinks(status, usernamePattern, "input.my.scheme://"); //Goto androidmanifest.xml and look at the scheme of the UserActivity

            Pattern hashtagPattern = Pattern.compile("#+[a-zA-Z0-9]*");
            Linkify.addLinks(status, hashtagPattern, "input.hashtag.scheme://");
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
        mFollowers = v.findViewById(R.id.tvFollowers);
        btnSignOut = v.findViewById(R.id.button3);
        tvChangePic = v.findViewById(R.id.textView6);
        mRecyclerView= v.findViewById(R.id.rvStory);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        presenter = new AccountPresenter(this);

        storyPresenter = new StoryPresenter(this);
        storyPresenter.createDummyData();

        Bundle bundle = getArguments();
        if (bundle != null) {
//            int image = bundle.getInt("picture");
//            Drawable drawable = getResources().getDrawable(image);
//            imageView.setImageDrawable(drawable);

        }

        tvChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(uploadImageIntent, REQUEST_UPLOAD_IMAGE);

            }
        });

        mFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* LAUNCH TO NEW ACTIVITY */
                Intent intent = new Intent(getActivity(), FollowActivity.class);
                startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UPLOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
        }
    }

    public void displayStories(List<Status> stories) {
        //RecyclerView
        mAdapter = new StoryAdapter(stories);
        mRecyclerView.setAdapter(mAdapter);
    }

}
