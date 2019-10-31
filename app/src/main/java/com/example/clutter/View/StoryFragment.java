//package com.example.clutter.View;
//
//
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Adapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.clutter.InterfaceMVP.StoryFragmentMVP;
//import com.example.clutter.Presenter.StoryPresenter;
//import com.example.clutter.R;
//
//import java.util.List;
//
//import com.example.clutter.Model.Status;
//
//public class StoryFragment extends Fragment implements StoryFragmentMVP.View {
//    private RecyclerView mRecycler;
////    private StoryAdapter mAdapter;
//    private StoryPresenter presenter;
//
//    private class StoryResultHolder extends RecyclerView.ViewHolder {
//        private ImageView imageView;
//        private TextView name;
//        private TextView handle;
//        private TextView time;
//        private TextView status;
//
//        public StoryResultHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.story_layout, parent, false));
//
//            imageView = itemView.findViewById(R.id.ivStory);
//            name = itemView.findViewById(R.id.tvStoryName);
//            handle = itemView.findViewById(R.id.tvStoryHandle);
//            time = itemView.findViewById(R.id.tvStoryTime);
//            status = itemView.findViewById(R.id.tvStory_Status);
//        }
//
//        protected void bind(Status currentStatus) {
//            Drawable drawable = getResources().getDrawable(R.drawable.me);
//            imageView.setImageDrawable(drawable);
//            name.setText(currentStatus.getFirstName());
//            handle.setText(currentStatus.getUserHandle());
//            time.setText(currentStatus.getTime());
//            status.setText(currentStatus.getStatus());
//
//        }
//    }
//
////    private class StoryAdapter extends RecyclerView.Adapter<StoryResultHolder> {
////        private List<Status> results;
////
////        public StoryAdapter(List<Status> storyResults) {
////            this.results = storyResults;
////        }
////
////        @NonNull
////        @Override
////        public StoryResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
////            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
////            return new StoryResultHolder(layoutInflater, viewGroup);
////        }
////
////        @Override
////        public void onBindViewHolder(@NonNull StoryResultHolder storyResultHolder, int position) {
////            Status status = results.get(position);
////            storyResultHolder.bind(status);
////        }
////
////        @Override
////        public int getItemCount() {
////            return results.size();
////        }
////    }
//
//    public StoryFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_story, container, false);
//        mRecycler = v.findViewById(R.id.story_recyclerView);
//        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        presenter = new StoryPresenter(this);
//        presenter.createDummyData();
//
//        return v;
//    }
//
//    public void displayStories(List<Status> stories) {
//        mAdapter = new StoryAdapter(stories);
//        mRecycler.setAdapter(mAdapter);
//    }
//
//}
