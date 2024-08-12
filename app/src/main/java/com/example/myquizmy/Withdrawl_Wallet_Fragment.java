package com.example.myquizmy;

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

import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.Profile.UserProfile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Withdrawl_Wallet_Fragment extends Fragment {
    private TestDatabase testDatabase;
    private SessionManager sessionManager;
    private String user_phone;
    private static final String KEY_USER_PHONE = "userPhone";
    private LinearLayout Withdrawmoney;
    private TextView totalmoney;
    private Button widrawlamount;
    private EditText widrwaledit;

    public Withdrawl_Wallet_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_withdrwal, container, false);

        testDatabase = new TestDatabase(getContext());
        sessionManager = new SessionManager(getActivity());
        user_phone = sessionManager.getUserDetails().get(SessionManager.KEY_USER_PHONE);

        totalmoney = view.findViewById(R.id.total_money);
        Withdrawmoney = view.findViewById(R.id.widralLayout);
        widrawlamount = view.findViewById(R.id.buttonWithdraw);
        widrwaledit = view.findViewById(R.id.editTextWithdrawalAmount);

        widrawlamount.setOnClickListener(v -> handleWithdrawal());

        //updateBalanceText();  // Update balance text on fragment load

        return view;
    }

    private void handleWithdrawal() {
        String amountString = widrwaledit.getText().toString();
        if (amountString.isEmpty() || amountString.equals("0")) {
            Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double withdrawalAmount;
        try {
            withdrawalAmount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid amount format", Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfile userProfile = testDatabase.getUserDetails(user_phone);
        if (userProfile == null) {
            Toast.makeText(getActivity(), "User details not found", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean hasUpiDetails = (userProfile.getUpiid() != null && !userProfile.getUpiid().isEmpty()) ||
                (userProfile.getUpino() != null && !userProfile.getUpino().isEmpty());
        boolean hasBankDetails = userProfile.getAcc() != null && !userProfile.getAcc().isEmpty() &&
                userProfile.getIfsc() != null && !userProfile.getIfsc().isEmpty();

        if (hasUpiDetails && hasBankDetails) {
            showPaymentMethodDialog(withdrawalAmount);
        } else if (hasUpiDetails) {
            processWithdrawal("UPI", withdrawalAmount);
        } else if (hasBankDetails) {
            processWithdrawal("Bank", withdrawalAmount);
        } else {
            Toast.makeText(getActivity(), "Please complete your payment details in your profile", Toast.LENGTH_LONG).show();
        }
    }

    private void showPaymentMethodDialog(final double withdrawalAmount) {
        new android.app.AlertDialog.Builder(getActivity())
                .setTitle("Select Payment Method")
                .setItems(new CharSequence[]{"UPI", "Bank"}, (dialog, which) -> {
                    switch (which) {
                        case 0: // UPI
                            processWithdrawal("UPI", withdrawalAmount);
                            break;
                        case 1: // Bank
                            processWithdrawal("Bank", withdrawalAmount);
                            break;
                    }
                })
                .show();
    }

    private void processWithdrawal(String paymentMethod, double amount) {
        boolean success = testDatabase.withdrawFromBankBalance(user_phone, amount);
        if (success) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            testDatabase.insertTransaction(user_phone, "Withdrawal", paymentMethod, date, String.valueOf(amount), getRandomKey(4));

            updateBalanceInDatabase();
            Toast.makeText(getActivity(), "Withdrawal successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Withdrawal failed. Check your balance or amount", Toast.LENGTH_SHORT).show();
        }
    }

    public String getRandomKey(int length) {
        final String characters = "0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        while (length > 0) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
            length--;
        }
        return stringBuilder.toString();
    }

    private void updateBalanceInDatabase() {
        UserProfile userProfile = testDatabase.getUserDetails(user_phone);
        if (userProfile != null) {
            double newBalance = userProfile.getBankBalance();
            // Perform any logic to calculate or update the balance here
            // For example, if you're adding or subtracting from the balance:

            // Assuming some operation on the balance, e.g., a deposit or withdrawal:
            // double updatedBalance = newBalance + someAmount;

            // Update the balance in the database
            testDatabase.updateBankBalance(user_phone, newBalance);
        }
    }

}
