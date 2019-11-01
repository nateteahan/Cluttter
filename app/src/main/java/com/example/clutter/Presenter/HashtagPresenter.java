package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.Model.Status;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.HashtagActivity;
import com.example.clutter.sdk.model.StatusList;
import com.example.clutter.sdk.model.StatusListStatusesItem;

import java.util.ArrayList;
import java.util.List;

public class HashtagPresenter {
    private HashtagActivity view;

    private class GetHashtagAsync extends AsyncTask<Void, Void, List<Status>> {

        private GetHashtagAsync() {
            //Blank constructor
        }

        @Override
        protected List<com.example.clutter.Model.Status> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            StatusList listOfStatuses = proxy.getHashtagStatuses();

            List<StatusListStatusesItem> statusItems = listOfStatuses.getStatuses();
            List<com.example.clutter.Model.Status> statusesToPost = new ArrayList<>();

            // For each of the JSON status items returned from AWS, parse into model Status object
            for (int i = 0; i < statusItems.size() ; i++) {
                StatusListStatusesItem currentStatus = statusItems.get(i);

                String profilePic = currentStatus.getProfilePic();
                String firstName = currentStatus.getFirstName();
                String userHandle = currentStatus.getUserHandle();
                String time = currentStatus.getTime();
                String status = currentStatus.getStatus();
                String imageAttachment = currentStatus.getImageAttachment();
                String videoAttachment = currentStatus.getVideoAttachment();

                com.example.clutter.Model.Status clientStatus = new com.example.clutter.Model.Status(profilePic, firstName, userHandle,
                        time, status, imageAttachment, videoAttachment);
                statusesToPost.add(clientStatus);
            }

//            statuses = statusesToPost;
            return statusesToPost;
        }

        @Override
        protected void onPostExecute(List<com.example.clutter.Model.Status> statuses) {
            view.displayHashtagStatuses(statuses);
        }
    }

    public HashtagPresenter(HashtagActivity view) {
        this.view = view;
    }

    public void createDummyData() {
        new GetHashtagAsync().execute();

    }
}
