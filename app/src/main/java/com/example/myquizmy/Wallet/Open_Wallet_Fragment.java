package com.example.myquizmy.Wallet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.Profile.UserProfile;
import com.example.myquizmy.R;
import com.example.myquizmy.SessionManager;
import com.example.myquizmy.Transaction;
import com.example.myquizmy.TransactionAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Open_Wallet_Fragment extends Fragment {

    private TextView text1, text2, text3, text4, text5, totalmoney;
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactions = new ArrayList<>();
    private TestDatabase testDatabase;
    private SessionManager sessionManager;
    private String user_phone;
    private LinearLayout myreferrlay;
    private Button myrefbtn;
    private EditText myrefedit;
    private static final String KEY_USER_PHONE = "userPhone";

    public Open_Wallet_Fragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open__wallet_, container, false);

        // Initialize views
        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);
        text4 = view.findViewById(R.id.text4);
        text5 = view.findViewById(R.id.text5);
        totalmoney = view.findViewById(R.id.total_money);
        recyclerView = view.findViewById(R.id.recycler_view_transactions);
        myreferrlay = view.findViewById(R.id.MyreferralLayout);
        myrefedit = view.findViewById(R.id.editTextReferral);
        myrefbtn = view.findViewById(R.id.myreferalbtn);

        testDatabase = new TestDatabase(getContext());
        sessionManager = new SessionManager(getActivity());
        user_phone = sessionManager.getUserDetails().get(KEY_USER_PHONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadTransactions("All");
        loadTransactions("Add Money");
        loadTransactions("Referral");
        loadTransactions("Winning");

        updateBalanceText();
        setupClickListeners();

        return view;
    }

    private void setupClickListeners() {
        text1.setOnClickListener(v -> {
            loadTransactions("All");
            myreferrlay.setVisibility(View.GONE);
        });
        text2.setOnClickListener(v -> {
            loadTransactions("Add Money");
            myreferrlay.setVisibility(View.GONE);
        });
        text3.setOnClickListener(v -> {
            loadTransactions("Withdrawal");
            myreferrlay.setVisibility(View.GONE);
        });
        text4.setOnClickListener(v -> {
            loadTransactions("Referral");
            myreferrlay.setVisibility(View.VISIBLE);
        });
        text5.setOnClickListener(v -> {
            loadTransactions("Winning");
            myreferrlay.setVisibility(View.GONE);
        });
    }

    private void loadTransactions(String section) {
        transactions.clear();
        switch (section) {
            case "Add Money":

                transactions.addAll(testDatabase.getTransactions(user_phone, "Add Money"));
                break;
            case "All":

                transactions.addAll(testDatabase.getTransactions(user_phone, "Add Money"));
                transactions.addAll(testDatabase.getTransactions(user_phone, "Withdrawal"));
                transactions.addAll(testDatabase.getTransactions(user_phone, "Referral"));
                transactions.addAll(testDatabase.getTransactionsWithGameDetails(user_phone, "Winning"));
                break;
            case "Withdrawal":

                transactions.addAll(testDatabase.getTransactions(user_phone, "Withdrawal"));
                break;
            case "Referral":

                transactions.addAll(testDatabase.getTransactions(user_phone, "Referral"));
                break;
            case "Winning":

                transactions.addAll(testDatabase.getTransactionsWithGameDetails(user_phone, "Winning"));
                break;
            default:

                transactions.addAll(testDatabase.getTransactions(user_phone, section));
                break;
        }


        if (adapter == null) {
            adapter = new TransactionAdapter(transactions);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void updateBalanceText() {
        UserProfile userProfile = testDatabase.getUserDetails(user_phone);
        if (userProfile != null) {
            double balance = userProfile.getBankBalance();
            totalmoney.setText("Rs. " + balance);
        } else {
            totalmoney.setText("Rs. 0.00");
        }
    }
}
