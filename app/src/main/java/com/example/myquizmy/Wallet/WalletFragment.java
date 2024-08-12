package com.example.myquizmy.Wallet;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.Profile.UserProfile;
import com.example.myquizmy.R;
import com.example.myquizmy.Payment.Razor_Pay_Activity;
import com.example.myquizmy.SessionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class WalletFragment extends Fragment implements View.OnClickListener {

    private Button addMoney;
    private Button openWallet;
    private EditText editTextAmount;
    private RecyclerView recyclerView;
    private AmountAdapter amountItemAdapter;
    private TextView TotalMoneytextView;
    private TestDatabase testDatabase;
    private SessionManager sessionManager;
    public  String user_phone;
    private static final String KEY_USER_PHONE = "userPhone";
    private ArrayList<AmountItem> amountItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        addMoney = view.findViewById(R.id.button_add_money);
        openWallet = view.findViewById(R.id.button_open_wallet);
        editTextAmount = view.findViewById(R.id.edittext_amount);
        TotalMoneytextView=view.findViewById(R.id.text_total_money);
        recyclerView = view.findViewById(R.id.recycler_view_amounts);

        testDatabase = new TestDatabase(getContext());
        sessionManager=new SessionManager(getActivity());
        user_phone= String.valueOf(sessionManager.getUserDetails().get(KEY_USER_PHONE));



        initClick();
        initRecyclerView();
        getList();
        updateBalanceText();

        return view;
    }

    public void initClick() {
        addMoney.setOnClickListener(this);
        openWallet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add_money) {
            handleAddMoney();
        } else if (v.getId() == R.id.button_open_wallet) {
            openWalletFragment();
        }
    }
    private void handleAddMoney() {
        String amountString = editTextAmount.getText().toString();
        if (amountString.isEmpty() || amountString.equals("0")) {
            Toast.makeText(getActivity(), "Please enter a valid amount ", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            testDatabase.insertTransaction(user_phone,"Add","UPI",date,amountString,getRandomKey(4));
            Intent intent = new Intent(getActivity(), Razor_Pay_Activity.class);
            intent.putExtra("amount", amountString);
            startActivity(intent);
        }
    }
    public String getRandomKey(int i) {
        final String characters = "0123456789";
        StringBuilder stringBuilder = new StringBuilder ( );
        while (i > 0) {
            Random ran = new Random ( );
            stringBuilder.append (characters.charAt (ran.nextInt (characters.length ( ))));
            i--;
        }
        return stringBuilder.toString ( );
    }
    private void openWalletFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Open_Wallet_Fragment openWalletFragment = new Open_Wallet_Fragment();
        fragmentTransaction.replace(R.id.container, openWalletFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        amountItemList = new ArrayList<>();
        amountItemAdapter = new AmountAdapter(getActivity(), amountItemList, new AmountAdapter.OnAmountClickListener() {
            @Override
            public void onAmountClick(String amount) {
                editTextAmount.setText(amount);
            }
        });
        recyclerView.setAdapter(amountItemAdapter);
    }

    public void getList() {
        amountItemList.clear();
        amountItemList.add(new AmountItem(100, "1", "RS"));
        amountItemList.add(new AmountItem(200, "2", "RS"));
        amountItemList.add(new AmountItem(300, "3", "RS"));
        amountItemAdapter.notifyDataSetChanged();
    }
    private void updateBalanceText() {
        UserProfile userProfile = testDatabase.getUserDetails(user_phone);
        if (userProfile != null) {
            double balance = userProfile.getBankBalance();
            TotalMoneytextView.setText("Rs. " + balance);
        } else {
            TotalMoneytextView.setText("Rs. 0.00");
        }
    }



}
