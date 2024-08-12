package com.example.myquizmy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.Game.GameFragment;
import com.example.myquizmy.Profile.UserProfile;

import java.util.ArrayList;

public class PlayFragment extends Fragment {

    Button maxfill, currentfill;
    Button joinow, play;
    TestDatabase testDatabase;
    RecyclerView recyclerView1, recyclerView2;

    private SessionManager sessionManager;
    private String user_phone;
    private static final String KEY_USER_PHONE = "userPhone";

    private static final String ARG_QUIZ_ITEM = "quizItem";
    private HomeItems quizItem;

    private static final int PLAY_AMOUNT = 10;

    public static PlayFragment newInstance(HomeItems quizItem) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUIZ_ITEM, quizItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizItem = (HomeItems) getArguments().getSerializable(ARG_QUIZ_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        recyclerView1 = view.findViewById(R.id.recycler_view1);
        recyclerView2 = view.findViewById(R.id.recycler_view2);
        maxfill = view.findViewById(R.id.maxfill);
        joinow = view.findViewById(R.id.joinButton);
        play = view.findViewById(R.id.playNowButton);
        currentfill = view.findViewById(R.id.currentfill);

        testDatabase = new TestDatabase(getContext());
        sessionManager = new SessionManager(getActivity());
        user_phone = sessionManager.getUserDetails().get(KEY_USER_PHONE);

        ArrayList<ModelRank> modelRankList = Ranking.getRankPrice();
        PriceDistributionAdapter priceDistributionAdapter = new PriceDistributionAdapter(modelRankList);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(priceDistributionAdapter);

        maxfill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView2.setVisibility(View.GONE);
                recyclerView1.setVisibility(View.VISIBLE);
            }
        });

        currentfill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);
            }
        });

        joinow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfile userProfile = testDatabase.getUserDetails(user_phone);

                if (userProfile != null && userProfile.getBankBalance() >= PLAY_AMOUNT) {
                    int newBalance = (int) (userProfile.getBankBalance() - PLAY_AMOUNT);
                    testDatabase.updateBankBalance(user_phone, newBalance);

                    play.setVisibility(View.VISIBLE);
                    joinow.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Balance updated. You can now play the game!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Insufficient balance to join the game!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                GameFragment newFragment = new GameFragment();
                fragmentTransaction.replace(R.id.container, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
