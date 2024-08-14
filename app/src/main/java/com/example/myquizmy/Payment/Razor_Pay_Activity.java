package com.example.myquizmy.Payment;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.Profile.UserProfile;
import com.example.myquizmy.R;
import com.example.myquizmy.SessionManager;
import com.example.myquizmy.Wallet.WalletFragment;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;

public class Razor_Pay_Activity extends AppCompatActivity implements PaymentResultListener {

    private TextView textTotalMoney;
    private TestDatabase testDatabase;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor_pay);

        textTotalMoney = findViewById(R.id.text_total_money);
        testDatabase = new TestDatabase(this);
        sessionManager = new SessionManager(this);

        Intent intent = getIntent();
        String amountString = intent.getStringExtra("amount");
        if (amountString == null || amountString.isEmpty()) {
            Toast.makeText(this, "Amount not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }else{

        int amountInPaise;
        try {
            amountInPaise = Integer.parseInt(amountString) * 100;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String formattedAmount = "Rs. " + amountString;
        textTotalMoney.setText(formattedAmount);

        startPayment(amountInPaise);}
    }

    private void startPayment(int amountInPaise) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_6sMQUCFwvr3UDv");

        JSONObject options = new JSONObject();
        try {
            options.put("name", "User");
            options.put("description", "Add Money to Wallet");
            options.put("currency", "INR");
            options.put("amount", amountInPaise);

            checkout.open(this, options);
        } catch (Exception e) {
            Toast.makeText(this, "Error in starting payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

        String userPhone = sessionManager.getUserDetails().get("userPhone");
        UserProfile userProfile = testDatabase.getUserDetails(userPhone);

//        if (userProfile != null) {
//            double addedAmount = Double.parseDouble(textTotalMoney.getText().toString().replace("Rs. ", ""));
//            double newBalance = userProfile.getBankBalance() + addedAmount;
//
//
//            boolean isUpdated = testDatabase.updateBankBalance(userPhone, newBalance);
//
//            if (isUpdated) {
//                Toast.makeText(this, "Payment Successful and Wallet Updated: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Payment Successful but Wallet Update Failed", Toast.LENGTH_SHORT).show();
//            }

            // Optionally navigate to WalletFragment and pass user details
            Intent intent = new Intent(this, WalletFragment.class);
            intent.putExtra("payment_id", razorpayPaymentID);
            intent.putExtra("user_name", userProfile.getName()); // Pass user name
            intent.putExtra("user_phone", userPhone); // Pass user phone
            startActivity(intent);
            finish();
//        } else {
//            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment failed: " + response, Toast.LENGTH_SHORT).show();
    }
}
