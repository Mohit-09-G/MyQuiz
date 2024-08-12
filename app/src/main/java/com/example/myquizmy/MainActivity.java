package com.example.myquizmy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myquizmy.Database.TestDatabase;
import com.example.myquizmy.Profile.ProfileFragment;
import com.example.myquizmy.Profile.UserProfile;
import com.example.myquizmy.Referral.RefralFragment;
import com.example.myquizmy.Wallet.Open_Wallet_Fragment;
import com.example.myquizmy.Wallet.WalletFragment;
import com.example.myquizmy.Notification.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TestDatabase testDatabase;
    private SessionManager sessionManager;
    private String user_phone;
    private static final String KEY_USER_PHONE = "userPhone";

    private WalletFragment walletFragment = new WalletFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private QuizFragment quizFragment = new QuizFragment();
    private TextView nameTextView, phoneTextView;

    private PrivacyFragment privacyFragment = new PrivacyFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();
    private PlayFragment playFragment = new PlayFragment();
    private RefralFragment referralFragment = new RefralFragment();
    private Withdrawl_Wallet_Fragment Withdraw_walletFragment=new Withdrawl_Wallet_Fragment();
    private Open_Wallet_Fragment openWalletFragment =new Open_Wallet_Fragment();
   // private UIManager uiManager;
    private NotificationsFragment notificationsFragment = new NotificationsFragment();
    private Terms_and_condition_Fragment termsAndConditionFragment = new Terms_and_condition_Fragment();

    private ImageView leftIcon;
    private ImageView rightIcon, profileImageView;
    private View toolbar;
    private View toolbarHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testDatabase = new TestDatabase(this);
        sessionManager = new SessionManager(this);
        user_phone = sessionManager.getUserDetails().get(KEY_USER_PHONE);

        // Initialize Views
        bottomNavigationView = findViewById(R.id.bottom_nav);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        toolbarHome = findViewById(R.id.toolbarhoome);


        //uiManager = new UIManager(toolbar, toolbarHome, bottomNavigationView);

        // Set up ActionBar and Drawer Toggle
        setSupportActionBar(findViewById(R.id.toolbar));
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get references to the Toolbar icons
        leftIcon = findViewById(R.id.left_icon);
        rightIcon = findViewById(R.id.right_icon);

        // Set click listener for the right icon to open WalletFragment
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(walletFragment);
            }
        });

        // Set click listener for the left icon to open ProfileFragment
        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(profileFragment);
            }
        });

        // Load HomeFragment by default
        loadFragment(homeFragment);

        // Bottom Navigation View Listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("MainActivity", "Bottom Navigation Item Selected: " + item.getItemId());
                if (item.getItemId() == R.id.home) {
                    loadFragment(homeFragment);
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    loadFragment(profileFragment);
                    return true;
                } else if (item.getItemId() == R.id.quiz) {
                    loadFragment(quizFragment);
                    return true;
                } else if (item.getItemId() == R.id.wallet) {
                    loadFragment(walletFragment);
                    return true;
                }
                return false;
            }
        });

        // Drawer Navigation View Listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("MainActivity", "Drawer Navigation Item Selected: " + item.getItemId());
                if (item.getItemId() == R.id.nav_livegames) {
                    loadFragment(homeFragment);
                } else if (item.getItemId() == R.id.nav_addmoney) {
                    loadFragment(walletFragment);
                } else if (item.getItemId() == R.id.nav_notifications) {
                    loadFragment(notificationsFragment);
                } else if (item.getItemId() == R.id.nav_play) {
                    loadFragment(playFragment);
                } else if (item.getItemId() == R.id.nav_privacy || item.getItemId() == R.id.nav_support) {
                    loadFragment(privacyFragment);
                } else if (item.getItemId() == R.id.refer) {
                    loadFragment(profileFragment);
                } else if (item.getItemId() == R.id.nav_terms) {
                    loadFragment(termsAndConditionFragment);
                }else if (item.getItemId() == R.id.nav_widraw) {
                    loadFragment(Withdraw_walletFragment);
                }else if (item.getItemId() == R.id.nav_wallet) {
                    loadFragment(openWalletFragment);
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        ImageView homeIcon = findViewById(R.id.homeback);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(homeFragment);
            }
        });

        // Display user data
        displayUserData();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();

        //uiManager.updateUI(fragment);


        if (fragment instanceof ProfileFragment || fragment instanceof WalletFragment) {
            toolbar.setVisibility(View.GONE);
            toolbarHome.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            toolbarHome.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayUserData() {
        // Retrieve the phone number from the session
        String phone = sessionManager.getUserDetails().get(KEY_USER_PHONE);

        if (phone != null && !phone.isEmpty()) {
            UserProfile user = testDatabase.getUserDetails(phone);

            if (user != null) {
                View headerView = navigationView.getHeaderView(0);
                nameTextView = headerView.findViewById(R.id.nav_name);
                phoneTextView = headerView.findViewById(R.id.nav_phone);
                profileImageView = headerView.findViewById(R.id.nav_image);

                nameTextView.setText(user.getName());
                phoneTextView.setText(user.getPhone());


                if (user.getImage() != null) {
                    try {
                        // Assuming user.getImage() returns a URI or file path
                        Bitmap bitmap = BitmapFactory.decodeFile(user.getImage().toString());
                        profileImageView.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Set a default image if there's an error
                        profileImageView.setImageResource(R.drawable.mainlogo);
                    }
                } else {

                    profileImageView.setImageResource(R.drawable.mainlogo);
                }
            }
        }
    }

}
