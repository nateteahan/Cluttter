package com.example.clutter.View;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clutter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerTab extends Fragment {


    public FollowerTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower_tab, container, false);
    }

}
