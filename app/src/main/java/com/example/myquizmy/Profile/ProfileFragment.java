
package com.example.myquizmy.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.Login.Sign_in_Activity;
import com.example.myquizmy.R;
import com.example.myquizmy.SessionManager;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;

public class ProfileFragment extends Fragment {

    private ImageView arrow, arrow1, arrow2, arrow3, arrow4;
    private ImageView uploadImage;
    private Uri uri;
    private Bitmap bitmapImage;
    private EditText editTextName, editTextEmail, editTextPhone, editTextAge, editTextAddress;
    private TextView textBasicDetails;
    private ViewGroup basicDetailsLayout;
    private EditText editFac, editWhatsapp;
    private TextView textSocial;
    private ViewGroup socialLayout;
    private static final String KEY_USER_PHONE = "userPhone";
    private EditText referralEdit;
     public  String user_phone;
    private TextView textReferral;
    private ViewGroup referralLayout;
    private TextView textGameDetails;
    private LinearLayout gameDetailsLayout;
    private EditText editUpiId, editUpiNumber, editBankName, editAccountNumber, editIfscCode;
    private TextView textPaymentMethods; private TestDatabase testDatabase;

    private SessionManager sessionManager;
    private ViewGroup paymentMethodsLayout;
    private Button buttonSave,upisave,banksave,refferal,Social;


    private ShapeableImageView buttonShareFacebook, buttonShareInstagram, buttonShareWhatsApp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        testDatabase = new TestDatabase(getContext());
        sessionManager=new SessionManager(getActivity());
        user_phone= String.valueOf(sessionManager.getUserDetails().get(KEY_USER_PHONE));







        buttonShareFacebook = view.findViewById(R.id.buttonShareFacebook);
        buttonShareInstagram = view.findViewById(R.id.buttonShareInstagram);
        buttonShareWhatsApp = view.findViewById(R.id.buttonShareWhatsApp);

        arrow = view.findViewById(R.id.imageArrow);
        arrow1 = view.findViewById(R.id.imageArrow1);
        arrow2 = view.findViewById(R.id.imageArrow2);
        arrow3 = view.findViewById(R.id.imageArrow3);
        arrow4 = view.findViewById(R.id.imageArrow4);

        View basic_deatils = view.findViewById(R.id.basic_deatils);
        View social = view.findViewById(R.id.social);
        View Payment = view.findViewById(R.id.Payment);
        View Referral = view.findViewById(R.id.Referral);
        View gamedetails = view.findViewById(R.id.gamedetails);

        editTextName = view.findViewById(R.id.editTextName);
        uploadImage = view.findViewById(R.id.profileImage);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextAddress = view.findViewById(R.id.editTextAddress);

        textGameDetails = view.findViewById(R.id.textGameDetails);
        gameDetailsLayout = view.findViewById(R.id.gameDetailsLayout);

        textBasicDetails = view.findViewById(R.id.textBasicDetails);
        basicDetailsLayout = view.findViewById(R.id.basicDetailsLayout);

        textSocial = view.findViewById(R.id.textsocial);
        socialLayout = view.findViewById(R.id.socialLayout);

        textReferral = view.findViewById(R.id.textReferal);
        referralLayout = view.findViewById(R.id.referalLayout);

        textPaymentMethods = view.findViewById(R.id.textPaymentMethods);
        paymentMethodsLayout = view.findViewById(R.id.paymentMethodsLayout);

        editFac = view.findViewById(R.id.editFac);
        editWhatsapp = view.findViewById(R.id.whatsapp);

        referralEdit = view.findViewById(R.id.referaledit);

        editUpiId = view.findViewById(R.id.editUpiId);
        editUpiNumber = view.findViewById(R.id.editUpiNumber);
        editBankName = view.findViewById(R.id.editBankName);
        editAccountNumber = view.findViewById(R.id.editAccountNumber);
        editIfscCode = view.findViewById(R.id.editIfscCode);

        buttonSave = view.findViewById(R.id.buttonSave);
        upisave=view.findViewById(R.id.upibutton);
        banksave=view.findViewById(R.id.bankbutton);
        refferal=view.findViewById(R.id.Referralbutton);
        Social=view.findViewById(R.id.socialbutton);
        refferal=view.findViewById(R.id.Referralbutton);



        basic_deatils.setOnClickListener(v -> toggleSectionVisibility(basicDetailsLayout, arrow));
        social.setOnClickListener(v -> toggleSectionVisibility(socialLayout, arrow1));
        Referral.setOnClickListener(v -> toggleSectionVisibility(referralLayout, arrow2));
        Payment.setOnClickListener(v -> toggleSectionVisibility(paymentMethodsLayout, arrow3));
        gamedetails.setOnClickListener(v -> toggleSectionVisibility(gameDetailsLayout, arrow4));

        buttonSave.setOnClickListener(v -> saveProfileData());
        banksave.setOnClickListener(v-> saveOrUpdateBankPaymentData());
        upisave.setOnClickListener(v-> saveOrUpdateUpiData());
        Social.setOnClickListener(v-> saveOrUpdateSocial());
        refferal.setOnClickListener(v -> SaveReferal());




        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            uri = data.getData();
                            try {
                                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                uploadImage.setImageBitmap(bitmapImage);
                                testDatabase.updateImage(user_phone,bitmapImage);
                            } catch (IOException e) {
                                Toast.makeText(getActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        uploadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });

        TextView textLogOut = view.findViewById(R.id.textLogOut);
        textLogOut.setOnClickListener(v -> showLogoutConfirmationDialog());

        buttonShareFacebook.setOnClickListener(v -> shareOnFacebook());
        buttonShareInstagram.setOnClickListener(v -> shareOnInstagram());
        buttonShareWhatsApp.setOnClickListener(v -> shareOnWhatsApp());

        loadUserData();
        loadGameDetails(view);

        return view;
    }

    public void toggleSectionVisibility(ViewGroup section, ImageView arrow) {
        if (section.getVisibility() == View.VISIBLE) {
            section.setVisibility(View.GONE);
            arrow.setRotation(360);
        } else {
            section.setVisibility(View.VISIBLE);
            arrow.setRotation(180);
        }
    }


    private void saveProfileData() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        // Validate inputs
        if (name.isEmpty() || email.isEmpty() || age.isEmpty() || address.isEmpty()) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }
//
//        if (bitmapImage == null) {
//            Toast.makeText(getActivity(), "Please upload a profile image", Toast.LENGTH_SHORT).show();
//            return;
//        }

        boolean isInserted = testDatabase.updateUserProfile(user_phone, name, email, age, address);
        if (isInserted) {
            Toast.makeText(getActivity(), "Profile data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to save profile data", Toast.LENGTH_SHORT).show();
        }
    }



    public void saveOrUpdateSocial() {
        String facebookLink = editFac.getText().toString().trim();
        String whatsappNumber = editWhatsapp.getText().toString().trim();


        String facebookPattern = "^(https?://)?(www\\.)?facebook\\.com/[\\w\\.]+$";

        String whatsappPattern = "^[789][0-9]{9}$";

        if (facebookLink.isEmpty() || whatsappNumber.isEmpty()) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!facebookLink.matches(facebookPattern)) {
            Toast.makeText(getActivity(), "Invalid Facebook profile link", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!whatsappNumber.matches(whatsappPattern)) {
            Toast.makeText(getActivity(), "Invalid WhatsApp number", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInsertedSocial = testDatabase.updateSocailData(facebookLink, whatsappNumber, user_phone);
        if (isInsertedSocial) {
            Toast.makeText(getActivity(), "Data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to save data", Toast.LENGTH_SHORT).show();
        }
    }


    public void saveOrUpdateUpiData() {
        String upiCode = editUpiId.getText().toString().trim();
        String upiNumber = editUpiNumber.getText().toString().trim();


        String upiCodePattern = "^[\\w\\.]+@[\\w]+$";

        String upiNumberPattern = "^[0-9]{10}$";

        if (upiCode.isEmpty() || upiNumber.isEmpty()) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!upiCode.matches(upiCodePattern)) {
            Toast.makeText(getActivity(), "Invalid UPI ID format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!upiNumber.matches(upiNumberPattern)) {
            Toast.makeText(getActivity(), "Invalid UPI number", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInsertedUpi = testDatabase.updateUpiData(user_phone, upiCode, upiNumber);
        if (isInsertedUpi) {
            Toast.makeText(getActivity(), "UPI data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to save UPI data", Toast.LENGTH_SHORT).show();
        }
    }

    public void SaveReferal(){
        String referral=referralEdit.getText().toString().trim();
        if(referral.isEmpty()){
            Toast.makeText(getActivity(),"Fill THE Referal Code",Toast.LENGTH_SHORT).show();
        }
        boolean isInsertedReferaal=testDatabase.updateReferralData(user_phone,referral);
        if (isInsertedReferaal) {
            Toast.makeText(getActivity(), "saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to save ", Toast.LENGTH_SHORT).show();
        }


    }
    private void saveOrUpdateBankPaymentData() {
        String phone = editTextPhone.getText().toString().trim();
        String bankName = editBankName.getText().toString().trim();
        String bankAcc = editAccountNumber.getText().toString().trim();
        String bankIfc = editIfscCode.getText().toString().trim();


        String accountNumberPattern = "^[0-9]{9,18}$";

        String ifscPattern = "^[A-Za-z]{4}[0-9]{1}[A-Za-z0-9]{6}$";

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(bankName) || TextUtils.isEmpty(bankAcc) || TextUtils.isEmpty(bankIfc)) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bankAcc.matches(accountNumberPattern)) {
            Toast.makeText(getActivity(), "Invalid bank account number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bankIfc.matches(ifscPattern)) {
            Toast.makeText(getActivity(), "Invalid IFSC code", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = false;
        if (testDatabase.checkPhone(phone)) {
            success = testDatabase.updateBankPaymentData(phone, bankName, bankAcc, bankIfc);
        }

        if (success) {
            Toast.makeText(getActivity(), "Bank Data Saved Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Bank Data Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadUserData() {

        UserProfile userDetails = testDatabase.getUserDetails(user_phone);
        if (userDetails != null) {
            editTextName.setText(userDetails.getName());
            editTextEmail.setText(userDetails.getEmail());
            editTextPhone.setText(userDetails.getPhone());
            editTextAge.setText(userDetails.getAge());
            editTextAddress.setText(userDetails.getAddress());
            editBankName.setText(userDetails.getBank());
            editAccountNumber.setText(userDetails.getAcc());
            editIfscCode.setText(userDetails.getIfsc());
            editFac.setText(userDetails.getFac());
            editWhatsapp.setText(userDetails.getWhats());
            editUpiId.setText(userDetails.getUpiid());
            editUpiNumber.setText(userDetails.getUpino());
            referralEdit.setText(userDetails.getPhone());
            Log.d("imagesss", "loadUserData: "+userDetails.getImage());
            if((userDetails.getImage())==null){
                uploadImage.setImageDrawable(getActivity().getDrawable(R.drawable.name));
            }
            else {
                uploadImage.setImageBitmap(userDetails.getImage());
            }
        }
    }
    public String checkNull(String s) {
        String str = "";
        if (s == null || s.isEmpty ( ) || s.equalsIgnoreCase ("null")) {
            str = "";
        } else {
            str = s;
        }
        return str;
    }
    public void loadGameDetails(View view) {

//        double totalMoneyWon = testDatabase.getTotalMoneyWon(user_phone);
        double totalMoneyWon = testDatabase.getuSERTotalMoneyWon(user_phone);
        double totalMoneySpent = testDatabase.getuSERTotalMoneySpent(user_phone);
        int totalGamesPlayed = testDatabase.getTotalGamesPlayed(user_phone);


        TextView textTotalMoneyWon = view.findViewById(R.id.textTotalMoneyWonValue);
        TextView textTotalMoneySpent = view.findViewById(R.id.textTotalMoneySpentValue);
        TextView textTotalGamePlay = view.findViewById(R.id.textTotalGamePlayValue);
        Log.d("khguytgyuk", "getuSERTotalMoneySpent: "+String.valueOf(totalMoneySpent));

        textTotalMoneyWon.setText(" Rs " + String.valueOf(totalMoneyWon));
        textTotalMoneySpent.setText("Rs " + String.valueOf(totalMoneySpent));
        textTotalGamePlay.setText("" + totalGamesPlayed);
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireActivity())
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(requireActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                     sessionManager.logout(getContext());

                    Intent intent = new Intent(requireActivity(), Sign_in_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    requireActivity().finish();

                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void shareOnFacebook() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my profile! Download the app: https://www.binplus.in/");
        shareIntent.setPackage("com.facebook.katana");

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {

            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=https://www.binplus.in/";
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(webIntent);
        }
    }

    private void shareOnInstagram() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my profile! Download the app: https://www.binplus.in/");
        shareIntent.setPackage("com.instagram.android");

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            String sharerUrl = "https://www.instagram.com/?url=https://www.binplus.in/";
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(webIntent);
        }
    }

    private void shareOnWhatsApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my profile! Download the app: https://www.binplus.in/");
        shareIntent.setPackage("com.whatsapp");

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {

            String sharerUrl = "https://api.whatsapp.com/send?text=Check%20out%20my%20profile!%20Download%20the%20app:%20https://www.binplus.in/";
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(webIntent);
        }
    }


}
