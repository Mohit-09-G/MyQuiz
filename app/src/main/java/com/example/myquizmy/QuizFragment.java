package com.example.myquizmy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeQuizAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<HomeItems> quizList;
    private Button buttonOne, buttonTwo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);


        buttonOne = view.findViewById(R.id.button_one);
        buttonTwo = view.findViewById(R.id.button_two);


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

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);


                View lineOne = view.findViewById(R.id.line_one);
                lineOne.setVisibility(View.VISIBLE);
                lineOne.setBackgroundColor(getResources().getColor(R.color.Medium_green));


                View lineTwo = view.findViewById(R.id.line_two);
                lineTwo.setVisibility(View.GONE);
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);


                View lineTwo = view.findViewById(R.id.line_two);
                lineTwo.setVisibility(View.VISIBLE);
                lineTwo.setBackgroundColor(getResources().getColor(R.color.Medium_green));


                View lineOne = view.findViewById(R.id.line_one);
                lineOne.setVisibility(View.GONE);
            }
        });


        return view;
    }

    private void initQuizList() {
        quizList = new ArrayList<>();
        // Example date-time format: "yyyy-MM-dd HH:mm:ss"
        String currentDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        quizList.add(new HomeItems(R.drawable.slide2, "Quiz 1", currentDateTime, 10, 1, 0, 8));
    }
}
