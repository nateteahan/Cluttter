package com.example.clutter.View;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

import com.bumptech.glide.Glide;
import com.example.clutter.InterfaceMVP.StoryFragmentMVP;
import com.example.clutter.Model.ModelSingleton;
import com.example.clutter.Model.Status;
import com.example.clutter.Model.User;
import com.example.clutter.Presenter.AccountPresenter;
import com.example.clutter.Presenter.StoryPresenter;
import com.example.clutter.R;
import com.example.clutter.Transformations.CircleTransform;
import com.example.clutter.Transformations.RoundedTransformation;
import com.example.clutter.sdk.model.PostPicture;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements StoryFragmentMVP.View {
    private static final int REQUEST_UPLOAD_IMAGE = 1;
//    private static final int RESULT_LOAD_IMAGE = -1;
    private StoryAdapter mAdapter;
    private StoryPresenter storyPresenter;
    private ImageView userPic;
    private TextView mFollowers;
    private RecyclerView mRecyclerView;
    private Button btnSignOut;
    private TextView tvChangePic;
    private TextView tvHandle;
    private TextView tvName;
    private ConstraintLayout mConstraintLayout;
//    private FollowAdapter mAdapter;
    private AccountPresenter presenter;
    private FragmentManager fragmentManager;
    private String updatePic;

    private class StoryResultHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView handle;
        private TextView time;
        private TextView status;
        private ImageView imageAttachment;
//        private VideoView videoAttachment;

        public StoryResultHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.status_layout, parent, false));

            imageView = itemView.findViewById(R.id.ivProfilePic);
            name = itemView.findViewById(R.id.tvStatusName);
            handle = itemView.findViewById(R.id.tvStatusHandle);
            time = itemView.findViewById(R.id.tvStatusTime);
            status = itemView.findViewById(R.id.tvStatusMessage);
            imageAttachment = itemView.findViewById(R.id.ivPhotoAttach);
            imageAttachment.setVisibility(View.GONE);
        }

        protected void bind(final Status currentStatus) {
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

                Picasso.get().load(currentStatus.getImageAttachment())
                        .centerCrop()
                        .transform(new RoundedTransformation(24, 24))
                        .fit()
                        .into(imageAttachment);

            }

            if (currentStatus.getVideoAttachment() != null) {
                imageAttachment.setVisibility(View.VISIBLE);

                Glide.with(getContext())
                        .load(currentStatus.getVideoAttachment())
                        .into(imageAttachment);
            }

            Pattern usernamePattern = Pattern.compile("@+[a-zA-Z0-9]*");
            Linkify.addLinks(status, usernamePattern, "input.my.scheme://"); //Goto androidmanifest.xml and look at the scheme of the UserActivity

            Pattern hashtagPattern = Pattern.compile("#+[a-zA-Z0-9]*");
            Linkify.addLinks(status, hashtagPattern, "input.hashtag.scheme://");

            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), StatusActivity.class);
                    intent.putExtra("PIC", currentStatus.getProfilePic());
                    intent.putExtra("STATUS", status.getText().toString());
                    intent.putExtra("NAME", name.getText().toString());
                    intent.putExtra("HANDLE", "@" + ModelSingleton.getmUser().getUserHandle());
                    intent.putExtra("TIME", time.getText().toString());
                    intent.putExtra("IMAGE", currentStatus.getImageAttachment());
                    intent.putExtra("VIDEO", currentStatus.getVideoAttachment());
                    startActivity(intent);
                }
            });
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

        User user = ModelSingleton.getmUser();
        final String name = user.getFirstName() + " " + user.getLastName();
        final String handle = user.getUserHandle();
        final String profilePic = user.getProfilePic();

        tvHandle = v.findViewById(R.id.tvHandle);
        tvName = v.findViewById(R.id.tvName);
        tvHandle.setText("@" + handle);
        tvName.setText(name);
        userPic = v.findViewById(R.id.ivUserAccount);
        Picasso.get().load(profilePic)
                .centerCrop()
                .transform(new CircleTransform())
                .fit()
                .into(userPic);

        mFollowers = v.findViewById(R.id.tvFollowers);
        btnSignOut = v.findViewById(R.id.button3);
        tvChangePic = v.findViewById(R.id.textView6);
        mConstraintLayout = v.findViewById(R.id.constraintID);
        mRecyclerView= v.findViewById(R.id.rvStory);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        presenter = new AccountPresenter(this);

        storyPresenter = new StoryPresenter(this);
        storyPresenter.createDummyData(handle);


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
                intent.putExtra("handle", handle);
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

//        if (requestCode == REQUEST_UPLOAD_IMAGE && resultCode == RESULT_OK && data != null) {
//            Uri selectedImage = data.getData();
//            userPic.setImageURI(selectedImage);
//        }
        if (requestCode == REQUEST_UPLOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            /* Using Picasso and URI to display in image view immediately */
            Uri selectedImage = data.getData();
            updatePic = selectedImage.toString();
            Picasso.get().load(selectedImage)
                    .fit()
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(userPic);

            /* Encode to Base64 */
            InputStream inputStream;
            try {
                inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
                byte[] inputData = getBytes(inputStream);
                updatePic = java.util.Base64.getEncoder().encodeToString(inputData);
                System.out.println("Made it here");
            } catch (IOException e) {
                e.printStackTrace();
            }

            PostPicture picture = new PostPicture();
            picture.setUserhandle(ModelSingleton.getmUser().getUserHandle());
            picture.setProfilePic(updatePic);

            presenter.updatePicture(picture);
        }
    }

    public void displayStories(List<Status> stories) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mConstraintLayout.setVisibility(View.GONE);
        //RecyclerView
        mAdapter = new StoryAdapter(stories);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void emptyStories() {
        mRecyclerView.setVisibility(View.GONE);
        mConstraintLayout.setVisibility(View.VISIBLE);
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, length);
        }

        return byteBuffer.toByteArray();
    }

    public void updatePicSuccessful(String url) {
        ModelSingleton.getmUser().setProfilePic(url);
    }
}
