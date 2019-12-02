package com.example.clutter.InterfaceMVP;

import com.example.clutter.Model.Status;

import java.util.List;

public interface HashtagMVP {
    interface Model {

    }

    interface View {
        void displayHashtagStatuses(List<Status> statuses, String key);
    }

    interface Presenter {
        void createDummyData(List<Status> statuses, String hashtag, String key);
    }
}
