package com.example.clutter.InterfaceMVP;

import com.example.clutter.Model.Status;

import java.util.List;

public interface UserMVP {

    interface Model {

    }

    interface View {
        void displayStatuses(List<Status> statuses);
    }

    interface Presenter {
        void createDummyData();
    }
}
