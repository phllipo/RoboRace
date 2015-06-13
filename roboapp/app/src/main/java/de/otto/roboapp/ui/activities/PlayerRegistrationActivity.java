package de.otto.roboapp.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.ui.activities.base.AbstractUpdatableActivity;
import de.otto.roboapp.util.OnFinishedCallback;

import static de.otto.roboapp.util.ThreadStarter.processInNewThread;


public class PlayerRegistrationActivity extends AbstractUpdatableActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_registration);
        final RoboAppController roboAppController = (RoboAppController) getApplicationContext();

        final TextView t_playerName = (EditText) findViewById(R.id.playerName);
        final TextView t_ipAddress = (EditText) findViewById(R.id.ipAddress);
        final TextView t_playerNameTitle = (TextView) findViewById(R.id.label_playerName);
        final TextView t_ipAddressTitle = (TextView) findViewById(R.id.label_ipAddress);
        Button submit = (Button) findViewById(R.id.btn_submit_playerName);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Munro.ttf");

        t_playerName.setTypeface(tf);
        t_ipAddress.setTypeface(tf);
        t_playerNameTitle.setTypeface(tf);
        t_ipAddressTitle.setTypeface(tf);
        submit.setTypeface(tf);

        loadLoginData(t_playerName, t_ipAddress);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String playerName = t_playerName.getText().toString();
                final String ipAddress = t_ipAddress.getText().toString();

                if (validateInput(playerName, ipAddress)) {
                    saveLoginData(playerName, ipAddress);

                    final ProgressDialog dialog = ProgressDialog.show(PlayerRegistrationActivity.this, "Connecting", "Connecting to Server...", true);
                    processInNewThread(new Runnable() {
                        @Override
                        public void run() {
                            roboAppController.playerNameEntered(playerName, ipAddress, new OnFinishedCallback() {
                                @Override
                                public void onFinished() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(PlayerRegistrationActivity.this, RoboRegistrationActivity.class);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    });
                                }

                                @Override
                                public void onFailed(final String reason) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            new AlertDialog.Builder(PlayerRegistrationActivity.this)
                                                    .setTitle("Connection establishment failed")
                                                    .setMessage("Connection establishment failed. " + reason)
                                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void loadLoginData(TextView t_playerName, TextView t_ipAddress) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String loginName = sharedPreferences.getString("loginName", null);
        if( loginName != null) {
            t_playerName.setText(loginName);
        }
        String ipAddress = sharedPreferences.getString("ipAddress", null);
        if( ipAddress != null) {
            t_ipAddress.setText(ipAddress);
        }
    }

    private void saveLoginData(String playerName, String ipAddress) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loginName", playerName);
        editor.putString("ipAddress", ipAddress);
        editor.apply();
    }

    private boolean validateInput(String playerName, String ipAddress) {
        if (playerName.isEmpty()) {
            Toast.makeText(this, "Playername must not be empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!ipAddress.matches("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) {
            Toast.makeText(this, "ip address is not valid",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void updateActivity() {

    }
}
