package com.example.myquizmy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.codebyashish.autoimageslider.AutoImageSlider;
import com.codebyashish.autoimageslider.Enums.ImageScaleType;
import com.codebyashish.autoimageslider.Models.ImageSlidesModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeQuizAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<HomeItems> quizList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the image slider
        AutoImageSlider autoImageSlider = view.findViewById(R.id.slider);
        ArrayList<ImageSlidesModel> autoImageList = new ArrayList<>();
        try {
            autoImageList.add(new ImageSlidesModel("https://i.pinimg.com/originals/e8/71/c5/e871c5ac4615f3e701026708008f3955.jpg"));
            autoImageList.add(new ImageSlidesModel(R.drawable.mainlogo));
            autoImageList.add(new ImageSlidesModel(R.drawable.slide1));
            autoImageList.add(new ImageSlidesModel(R.drawable.slide2));
            autoImageSlider.setImageList(autoImageList, ImageScaleType.FIT);
            autoImageSlider.setDefaultAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }


        initQuizList();
        recyclerView = view.findViewById(R.id.home_recycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new HomeQuizAdapter(quizList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new HomeQuizAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HomeItems clickedItem = quizList.get(position);
                PlayFragment playFragment = PlayFragment.newInstance(clickedItem);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, playFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private void initQuizList() {
        quizList = new ArrayList<>();

        String currentDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        quizList.add(new HomeItems(R.drawable.slide2, "Quiz 1", currentDateTime, 10, 1, 0, 8));
    }
}
