package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.AccountFragmentMVP;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.AccountFragment;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.PostPicture;

import java.util.ArrayList;
import java.util.List;

public class AccountPresenter implements AccountFragmentMVP.Presenter {
    private AccountFragment view;
    private List<FollowInfo> info;

    private class UpdatePictureAsync extends AsyncTask<Void, Void, Message> {
        private PostPicture rootUser;

        public UpdatePictureAsync(PostPicture root) {
            this.rootUser = root;
        }

        @Override
        protected Message doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            Message message = proxy.updateUserProfile(rootUser);

            System.out.println("Lets see if I get here");

            return message;
        }

        @Override
        protected void onPostExecute(Message s) {
            view.updatePicSuccessful(s.getMessage());
        }
    }

    public AccountPresenter(AccountFragment view) {
        this.view = view;
        this.info = new ArrayList<>();
    }

    @Override
    public void createDummyFollowers() {
        info.clear();

        FollowInfo follower = new FollowInfo("https://www.famousbirthdays.com/faces/hunt-director-justin-image.jpg","Justin");
        info.add(follower);

        follower = new FollowInfo("http://i.imgur.com/bIRGzVO.jpg", "Rocky");
        info.add(follower);

    }

    @Override
    public void createDummyFollowees() {
        info.clear();

        FollowInfo followee = new FollowInfo("http://i.imgur.com/bIRGzVO.jpg", "God");
        info.add(followee);

    }

    public void updatePicture(PostPicture newPic) {
        new UpdatePictureAsync(newPic).execute();
    }
}
