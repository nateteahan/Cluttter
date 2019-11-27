package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.FollowMvp;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.FollowingFragment;
import com.example.clutter.sdk.model.FollowingList;
import com.example.clutter.sdk.model.FollowingListFollowingItem;

import java.util.ArrayList;
import java.util.List;

public class FollowingPresenter implements FollowMvp.Presenter {
    private FollowingFragment view;
    private List<FollowInfo> info;
    private String userHandle;

    private class GetFollowingAsync extends AsyncTask<Void, Void, List<FollowInfo>> {

        private GetFollowingAsync(String handle) {
            userHandle = handle.replaceAll("@", "");
        }

        @Override
        protected List<FollowInfo> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            FollowingList listOfFollowing = proxy.getFollowing(userHandle);

            List<FollowingListFollowingItem> followingItem = listOfFollowing.getFollowing();
            List<FollowInfo> followingToDisplay = new ArrayList<>();

            // For each of the JSON follow items returned from AWS, parse into model FollowInfo object
            if (followingItem != null) {
                for (int i = 0; i < followingItem.size() ; i++) {
                    FollowingListFollowingItem currentFollowing = followingItem.get(i);

                    String profilePic = proxy.getUser(currentFollowing.getUserHandle()).getProfilePic();
                    String userHandle = "@" + currentFollowing.getUserHandle();
                    FollowInfo followingObject = new FollowInfo(profilePic, userHandle);
                    followingToDisplay.add(followingObject);
                }
            }
            return followingToDisplay;
        }

        @Override
        protected void onPostExecute(List<FollowInfo> following) {
            view.displayFollowInfo(following);
        }
    }

    public FollowingPresenter(FollowingFragment followingFragment) {
        this.view = followingFragment;
        this.info = new ArrayList<>();
    }

    @Override
    public void getFollowers(String handle) {
        //
    }

    @Override
    public void getFollowees(String handle) {
        new GetFollowingAsync(handle).execute();
    }
}
