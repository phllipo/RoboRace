package de.otto.roboapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;

import static android.os.SystemClock.sleep;


public class MainActivity extends Controller {

    private String playerName;
    private String serverIP = "10.90.164.15";
    private String serverPort = "8887";
    private WebSocketClient wsc;



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


               final ProgressDialog dialog;
                dialog = ProgressDialog.show(MainActivity.this, "Loading", "Connecting to Server...", true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        sc.setServerIp(serverIP);
                        sc.setServerPort(serverPort);
                        sc.startWebserverConnector();
                        sleep(1000);
                        sc.sendMsg("{\"eventType\": \"connect\", \"data\": {\"clientType\": \"app\", \"name\": \"" + playerName + "\", \"ready\": \"false\" }}\"");

                        //sc = new ServerController(serverIP, serverPort, playerName);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Objekte in nächste Activity übergeben
                                // Objekte, die übergeben werden sollen, müssen "Parcelable" implentieren
                                Intent intent = new Intent(MainActivity.this, SelectRobo.class);

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
