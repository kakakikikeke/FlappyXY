package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.blogspot.kakakikikeke.sensortest.utils.ConfigPropUtil;
import com.nifty.cloud.mb.NCMB;
import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBInstallation;
import com.nifty.cloud.mb.NCMBPush;
import com.nifty.cloud.mb.RegistrationCallback;

public class StartActivity extends AppCompatActivity {

    private ConfigPropUtil config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        config = new ConfigPropUtil();
        initNCMB();
        registDeviceTokenToNCMB();
    }

    public void startGame(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void showRanking(View view) {
        Intent i = new Intent(this, RankingActivity.class);
        startActivity(i);
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent e) {
        return e.getKeyCode() == KeyEvent.KEYCODE_BACK || super.dispatchKeyEvent(e);
    }

    private void initNCMB() {
        NCMB.initialize(this, (String) config.get("ncmb.apikey"), (String) config.get("ncmb.clientkey"));
    }

    private void registDeviceTokenToNCMB() {
        final NCMBInstallation installation = NCMBInstallation.getCurrentInstallation();
        installation.getRegistrationIdInBackground((String) config.get("gcm.senderId"), new RegistrationCallback() {
            @Override
            public void done(NCMBException e) {
                if (e != null) {
                    // fail
                } else {
                    // success
                    try {
                        installation.save();
                    } catch (NCMBException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
        NCMBPush.setDefaultPushCallback(this, MainActivity.class);
    }
}
