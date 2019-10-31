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

    private class GetFollowingAsync extends AsyncTask<Void, Void, List<FollowInfo>> {

        private GetFollowingAsync() {
            //Blank constructor
        }

        @Override
        protected List<FollowInfo> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            FollowingList listOfFollowing = proxy.getFollowing();

            List<FollowingListFollowingItem> followingItem = listOfFollowing.getFollowing();
            List<FollowInfo> followingToDisplay = new ArrayList<>();

            // For each of the JSON status items returned from AWS, parse into model Status object
            for (int i = 0; i < followingItem.size() ; i++) {
                FollowingListFollowingItem currentFollowing = followingItem.get(i);

                    String userHandle = currentFollowing.getUserHandle();
                    FollowInfo followingObject = new FollowInfo(userHandle);
                    followingToDisplay.add(followingObject);
//                String firstName = currentStatus.getFirstName();
//                String userHandle = currentStatus.getUserHandle();
//                String time = currentStatus.getTime();
//                String status = currentStatus.getStatus();
//                String imageAttachment = currentStatus.getImageAttachment();
//                String videoAttachment = currentStatus.getVideoAttachment();

//                com.example.clutter.Model.Status clientStatus = new com.example.clutter.Model.Status(firstName, userHandle,
//                        time, status, imageAttachment, videoAttachment);
//                statusesToPost.add(clientStatus);
            }

//            statuses = statusesToPost;
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
    public void createDummyFollowers() {
        //
    }

    @Override
    public void createDummyFollowees() {
        new GetFollowingAsync().execute();
//                info.clear();
//
//        FollowInfo followee = new FollowInfo("God");
//        info.add(followee);
//
//        view.displayFollowInfo(info);
    }
}
