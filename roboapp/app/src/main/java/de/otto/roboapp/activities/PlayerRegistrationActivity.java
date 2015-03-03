package de.otto.roboapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.util.OnFinishedCallback;

import static de.otto.roboapp.util.ThreadStarter.processInNewThread;


public class PlayerRegistrationActivity extends AbstractUpdatableActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_registration);
        final RoboAppController roboAppController = (RoboAppController) getApplicationContext();

        final TextView t_playerName = (EditText) findViewById(R.id.playerName);

        Button submit = (Button) findViewById(R.id.btn_submit_playerName);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String playerName = t_playerName.getText().toString();

                final ProgressDialog dialog = ProgressDialog.show(PlayerRegistrationActivity.this, "Connecting", "Connecting to Server...", true);
                processInNewThread(new Runnable() {
                    @Override
                    public void run() {
                        roboAppController.playerNameEntered(playerName, new OnFinishedCallback() {
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
        });
    }

    @Override
    public void updateActivity() {

    }
}
