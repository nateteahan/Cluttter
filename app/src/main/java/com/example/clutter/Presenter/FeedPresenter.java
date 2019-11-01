package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.FeedFragmentMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.FeedFragment;
import com.example.clutter.sdk.model.StatusList;
import com.example.clutter.sdk.model.StatusListStatusesItem;

import java.util.ArrayList;
import java.util.List;

public class FeedPresenter implements FeedFragmentMVP.Presenter {
    private FeedFragment feedView;
    private List<Status> statuses;

    private class GetFeedAsync extends AsyncTask<Void, Void, List<Status>> {

        private GetFeedAsync() {
            //Blank constructor
        }

        @Override
        protected List<com.example.clutter.Model.Status> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            StatusList listOfStatuses = proxy.getFeed();

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
            feedView.displayStatus(statuses);
        }
    }

    public FeedPresenter(FeedFragment view) {
        this.feedView = view;
//        proxy = new ServerProxy();
    }

    @Override
    public void createDummyData() {
//        List<Status> statuses = new ArrayList<>();
        Status status;

//        Drawable userPic = feedView.getResources().getDrawable(R.drawable.me);
//        ImageView icon =

//        Drawable drawable = feedView.getResources().getDrawable(R.drawable.camera_logo);
//        Drawable drawable = ResourcesCompat.getDrawable(feedView.getResources(), R.drawable.camera_logo, null);
//        ImageView imageView = new ImageView(feedView.getActivity());
//        imageView.setImageDrawable(drawable);

//        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake", imageView, null);
//        statuses.add(status);
//
//        status = new Status("Rocky", "@rockyevans", "6:53 p.m.", "My #CS 312 project ruined me today", null, null);
//        statuses.add(status);
//
//        status = new Status("Justin", "@justinj_hunt", "8:00 p.m.", "I am going to go to bed early tonight,", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake",null, null);
//        statuses.add(status);
//
//        status = new Status("Cayla", "@caylarob", "6:53 p.m.", "Yesterday, @cayla and I went to the grocery store and decided" +
//                " that I needed some raspberries. I went to the fruit aisle and it turned out they had all gone bad already. Thanks @smiths", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "6:53 p.m.", "My CS 312 project ruined me today",null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight",null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake",null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "6:53 p.m.", "My CS 312 project ruined me today",null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight",null, null);
//        statuses.add(status);

//        proxy.getFeed();

        new GetFeedAsync().execute();
    }

//    public Bitmap setImageAttachment(String url) {
//        try {
//            Bitmap x = new DownloadImageTask(url).execute().get();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException f) {
//            f.printStackTrace();
//        }
//
//        return x
//    }
}