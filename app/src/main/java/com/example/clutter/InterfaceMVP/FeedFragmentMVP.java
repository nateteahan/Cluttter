package com.example.clutter.InterfaceMVP;


import com.example.clutter.Model.Status;

import java.util.List;

public interface FeedFragmentMVP {
    interface Model {

    }

    interface View {
        void displayStatus(List<Status> s, String key);
    }

    interface Presenter {
        void createDummyData(List<Status> statuses, String handle, String lastKey);
    }
}
