package com.example.myquizmy.Wallet;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizmy.R;

import java.util.ArrayList;

public class AddMoneyFragment extends Fragment {

    private RecyclerView recyclerView;
    private AmountAdapter amountItemAdapter;
    private ArrayList<AmountItem> amountItemList;
    EditText edittext_amount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        initView(view);
        getList();





        return view;
    }

   public void getList() {
        amountItemList.clear();
        amountItemList.add(new AmountItem(100, "1", "RS"));
        amountItemList.add(new AmountItem(200, "2", "RS"));
        amountItemList.add(new AmountItem(300, "3", "RS"));
        amountItemAdapter = new AmountAdapter(getActivity(), amountItemList, new AmountAdapter.OnAmountClickListener() {
            @Override
            public void onAmountClick(String amount) {
                edittext_amount.setText(amount);
            }
        });
        recyclerView.setAdapter(amountItemAdapter);
    }

    public void initView(View v) {
        recyclerView = v.findViewById(R.id.recycler_view_amounts);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        amountItemList = new ArrayList<>();
        edittext_amount=v.findViewById(R.id.edittext_amount);
    }
}
