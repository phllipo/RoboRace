package de.otto.roboapp;

import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import static android.os.SystemClock.sleep;


public class MainActivity extends ActionBarActivity {

    private String playerName;
    private String serverIP = "10.90.170.33";
    private String serverPort = "8888";
    private WebSocketClient wsc;

    public ServerController sc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView t_playerName = (TextView) findViewById(R.id.playerName);
        t_playerName.setText("Luca");
        Button submit = (Button) findViewById(R.id.btn_submit_playerName);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerName = t_playerName.getText().toString();

                sc = new ServerController(serverIP, serverPort, playerName);

            }
        });
    }
}
