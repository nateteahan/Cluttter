package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.HashtagMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.HashtagActivity;
import com.example.clutter.sdk.model.NewStatusList;
import com.example.clutter.sdk.model.NewStatusListStatusesItem;

import java.util.List;

public class HashtagPresenter implements HashtagMVP.Presenter {
    private HashtagActivity view;
    private String hashtag;
    private String lastKey;
    private List<Status> statusesToPost;

    private class GetHashtagAsync extends AsyncTask<Void, Void, List<Status>> {

        private GetHashtagAsync(String Hashtag, String key) {
            hashtag = Hashtag;
            lastKey = key;
        }

        @Override
        protected List<com.example.clutter.Model.Status> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            if (lastKey == null) {
                lastKey = "";
            }

            NewStatusList listOfStatuses = proxy.getHashtagStatuses(hashtag, lastKey);
            lastKey = listOfStatuses.getLastKey();

            List<NewStatusListStatusesItem> statusItems = listOfStatuses.getStatuses();

            // For each of the JSON status items returned from AWS, parse into model Status object
            for (int i = 0; i < statusItems.size(); i++) {
                NewStatusListStatusesItem currentStatus = statusItems.get(i);

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

            return statusesToPost;
        }

        @Override
        protected void onPostExecute(List<com.example.clutter.Model.Status> statuses) {
            view.displayHashtagStatuses(statuses, lastKey);
        }
    }

    public HashtagPresenter(HashtagActivity view) {
        this.view = view;
    }

    @Override
    public void createDummyData(List<Status> statuses, String hashtag, String key) {
        this.hashtag = hashtag;
        this.statusesToPost = statuses;

        if (key != null) {
            key = parseLastKey(key);
        }

        new GetHashtagAsync(hashtag, key).execute();

    }

    private String parseLastKey(String key) {
        if (key != null) {
            //Remove special characters... Invalid in api gateway
            key = key.replaceAll(" ", "");
            key = key.replaceAll("/", "");
            key = key.replaceAll(":", "");
        }

        return key;
    }
}
