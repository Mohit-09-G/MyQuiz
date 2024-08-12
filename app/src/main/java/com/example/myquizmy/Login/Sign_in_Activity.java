package com.example.myquizmy.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import in.aabhasjindal.otptextview.OtpTextView;

import java.util.Locale;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.MainActivity;
import com.example.myquizmy.R;
import com.example.myquizmy.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class Sign_in_Activity extends AppCompatActivity {

    long mTimeLeftInMillis =60000;
    CountDownTimer mcountDownTimer;
    public static  final String  OTP_REGEX ="[0-9]{3,6}";
    TextView tv_minutes,tv_seconds,tv_timer,btn_otpResend;
    LinearLayout lin_timer;
    public LinearLayout lin_otp;
    Boolean is_verify=false;

    String otpText="";
    OtpTextView otp_view;
    LinearLayout lin_resnd;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private TextView signup;
    private TestDatabase databaseHelper;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        signup = findViewById(R.id.signuplink);
        phoneEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.pass);
        signInButton = findViewById(R.id.sign_in_button);

        databaseHelper = new TestDatabase(this);

        sessionManager = new SessionManager(this);


        signInButton.setOnClickListener(v -> signIn());

        lin_otp=findViewById(R.id.lin_otp);
        lin_otp.setOnClickListener(v->openOtpDialog());


        signup.setOnClickListener(v -> navigateToSignUp());
    }

    private void signIn() {
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phone.matches("^[6-9]\\d{9}$")) {
            Toast.makeText(this, "Phone number must start with 6, 7, 8, or 9 and be 10 digits long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }


        if (databaseHelper.checkUserCredentials(phone, password)) {
            if(is_verify){
                sessionManager.createSession(phone);
                Intent intent = new Intent(Sign_in_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();}
            else{
                Toast.makeText(this, "OTP verification required", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Invalid phone number or password", Toast.LENGTH_SHORT).show();
        }
    }
    public void openOtpDialog(){
        String phone=phoneEditText.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please enter your Phone number ",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!phone.matches("^[6-9]\\d{9}$")) {
            Toast.makeText(this, "Phone number must start with 6, 7, 8, or 9 and be 10 digits long", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            openOtpPage();

        }

    }

    private void openOtpPage() {

        mcountDownTimer=null;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this); // Style here
        bottomSheetDialog.setContentView(R.layout.dialog_layout_bottom_sheet_otp);
//        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        Button btn_submit=bottomSheetDialog.findViewById(R.id.btn_submit);
        ImageView iv_close=bottomSheetDialog.findViewById (R.id.iv_close);
        otp_view=bottomSheetDialog.findViewById (R.id.otp_view);
        tv_minutes=bottomSheetDialog.findViewById (R.id.tv_minutes);
        tv_seconds=bottomSheetDialog.findViewById (R.id.tv_second);
        tv_timer=bottomSheetDialog.findViewById (R.id.tv_timer);
        lin_timer=bottomSheetDialog.findViewById (R.id.lin_timer);
        lin_resnd=bottomSheetDialog.findViewById (R.id.lin_resnd);
        btn_otpResend=bottomSheetDialog.findViewById (R.id.btn_OtpResend);
        iv_close=bottomSheetDialog.findViewById (R.id.iv_close);
        setCounterTimer();
        otpText= getRandomKey(6);
        otp_view.setOTP(otpText);

        iv_close.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                mcountDownTimer.cancel ( );
                bottomSheetDialog.dismiss ();
            }
        });
        btn_otpResend.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                is_verify=false;
                {
                    mcountDownTimer.cancel ( );
                    otpText = getRandomKey(6);
                    otp_view.setOTP (otpText);



                }
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                if (otp_view.getOTP ( ).toString ( ).equals ("")) {
                    Toast.makeText(Sign_in_Activity.this, "OTP required", Toast.LENGTH_SHORT).show();
                } else if (otp_view.getOTP ( ).toString ( ).length ( ) != 6) {
                    Toast.makeText(Sign_in_Activity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
                else if (!otp_view.getOTP ( ).equals (otpText)) {
                    Toast.makeText(Sign_in_Activity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();

                } else {
                    is_verify=true;
                    bottomSheetDialog.dismiss();

                }
            }
        });


        bottomSheetDialog.show();
        bottomSheetDialog.setCanceledOnTouchOutside (false);
        bottomSheetDialog.setCancelable (false);





    }
    public void  setCounterTimer() {

        mTimeLeftInMillis=60000;
        tv_timer.setVisibility (View.GONE);
        lin_timer.setVisibility (View.VISIBLE);
        if(mcountDownTimer!=null){
            mcountDownTimer.cancel ();
        }
        mcountDownTimer = new CountDownTimer (60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
                int  seconds = (int) (mTimeLeftInMillis / 1000) % 60;
                String timeLeftFormatted = String.format (Locale.getDefault ( ), "%02d:%02d", minutes, seconds);
                tv_minutes.setText (String.valueOf (String.format ("%02d", minutes)));
                tv_seconds.setText (String.valueOf (String.format ("%02d", seconds)));
                if(timeLeftFormatted.equalsIgnoreCase ("00:30")) {
                    btn_otpResend.setVisibility (View.VISIBLE);
                } else if (timeLeftFormatted.equalsIgnoreCase ("00:00")) {
                    btn_otpResend.setVisibility (View.VISIBLE);
                    mcountDownTimer.cancel ( );
                    otp_view.setOTP ("");
                    tv_timer.setVisibility (View.VISIBLE);
                    lin_timer.setVisibility (View.GONE);
                }
            }

            @Override
            public void onFinish() {
                otpText="";
                //otp=""
                tv_timer.setVisibility (View.VISIBLE);
                lin_timer.setVisibility (View.GONE);
                otp_view.setOTP ("");
                tv_timer.setText("Timeout");

            }
        }.start();
    }
    private void navigateToSignUp() {
        Intent intent = new Intent(Sign_in_Activity.this, Sign_up_Activity.class);
        startActivity(intent);
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
}
