package de.otto.roboapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.otto.roboapp.RoboAppController;
import de.otto.roboapp.util.OnFinishedCallback;
import de.otto.roboapp.R;


public class PlayerRegistrationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_registration);
        final RoboAppController roboAppController = (RoboAppController)getApplicationContext();

        final TextView t_playerName = (EditText) findViewById(R.id.playerName);

        Button submit = (Button) findViewById(R.id.btn_submit_playerName);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String playerName = t_playerName.getText().toString();

                final ProgressDialog dialog = ProgressDialog.show(PlayerRegistrationActivity.this, "Loading", "Connecting to Server...", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roboAppController.playerNameEntered(playerName, new OnFinishedCallback() {
                            @Override
                            public void onFinished() {
                                Intent intent = new Intent(PlayerRegistrationActivity.this, RoboRegistrationActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
