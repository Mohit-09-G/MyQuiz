/*{
  package com.example.myquizmy;

import android.view.View;

import com.example.myquizmy.Game.GameFragment;
import com.example.myquizmy.Notification.NotificationsFragment;
import com.example.myquizmy.Profile.ProfileFragment;
import com.example.myquizmy.Referral.RefralFragment;
import com.example.myquizmy.Wallet.WalletFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;

public class UIManager {

    private View toolbar;
    private View toolbarHome;
    private WalletFragment walletFragment = new WalletFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private QuizFragment quizFragment = new QuizFragment();
    private GameFragment gameFragment = new GameFragment();
    private NotificationsFragment notificationsFragment = new NotificationsFragment();
    private Terms_and_condition_Fragment termsAndConditionFragment = new Terms_and_condition_Fragment();
    private PrivacyFragment privacyFragment = new PrivacyFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();
    private PlayFragment playFragment = new PlayFragment();
    private RefralFragment referralFragment = new RefralFragment();
    private BottomNavigationView bottomNavigationView;

    public UIManager(View toolbar, View toolbarHome, BottomNavigationView bottomNavigationView) {
        this.toolbar = toolbar;
        this.toolbarHome = toolbarHome;
        this.bottomNavigationView = bottomNavigationView;
    }

    public void updateUI(Fragment fragment) {
        if (fragment instanceof GameFragment) {
            toolbar.setVisibility(View.GONE);
            toolbarHome.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            toolbarHome.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }
}



}*/