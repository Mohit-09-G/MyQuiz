package com.example.myquizmy.Internet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.myquizmy.R;

public class Connection_Internet extends BroadcastReceiver {

    private Dialog dialog;
    private Handler handler;
    private Runnable checkConnectionRunnable;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isConnected(context)) {
            showBlockingDialog(context);
        } else {
            dismissDialog();
        }
    }

    public boolean isConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                return (networkInfo != null && networkInfo.isConnected());
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showBlockingDialog(final Context context) {
        if (dialog == null || !dialog.isShowing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.internet_dailog, null);
            builder.setView(view);
            builder.setCancelable(false);

            dialog = builder.create();


            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
                checkConnectionRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (isConnected(context)) {
                            dismissDialog();
                        } else {
                            handler.postDelayed(this, 1000);
                        }
                    }
                };
                handler.post(checkConnectionRunnable);
            }

            dialog.show();
        }
    }

    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
