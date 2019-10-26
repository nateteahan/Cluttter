package com.example.clutter.InterfaceMVP;


import java.util.List;
import com.example.clutter.Model.Status;

public interface FeedFragmentMVP {
    interface Model {

    }

    interface View {
        void displayStatus(List<Status> s);
    }

    interface Presenter {
        void createDummyData();
    }
}
