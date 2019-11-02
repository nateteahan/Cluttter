package com.example.clutter.InterfaceMVP;

import com.example.clutter.Model.Status;

import java.util.List;

public interface HashtagMVP {
    interface Model {

    }

    interface View {
        void displayHashtagStatuses(List<Status> statuses);
    }

    interface Presenter {
        void createDummyData();
    }
}
