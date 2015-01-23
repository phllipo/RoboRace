package de.otto.roboapp;

import android.app.Activity;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by luca on 09.01.15.
 */

public class ServerController {
    private String serverIp;
    private String serverPort;
    private WebserverConnector wc;

    public Activity getUIActivity() {
        return UIActivity;
    }

    public void setUIActivity(Activity UIActivity) {
        this.UIActivity = UIActivity;
    }

    private WebSocketClient wsc;
    private Activity UIActivity;

    public static TextView speed;


    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public ServerController(String serverIp, String serverPort, TextView btn, Activity ui) {
        setServerIp(serverIp);
        setServerPort(serverPort);
        setUIActivity(ui);
        startWebserverConnector();
        speed = btn;
        // TODO
        speed.setText("testtesttest");
    }

    public void printSpeed(int speed) {


    }



    public void processMsg(String msg) throws JSONException {
        JSONObject msgJSON = new JSONObject(msg);
        if(msgJSON.getString("eventType").equals("speed")) {
           new SpeedMessageProcessor(speed).process(msgJSON, getUIActivity());
        }
    }



    public void startWebserverConnector(){
        try {
                wsc = new WebSocketClient(new URI("ws://" + getServerIp() + ":" + getServerPort())) {
                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
                        System.out.println("connected");
                        this.send("{\"eventType\": \"connect\", \"data\": {\"clientType\": \"app\", \"name\": \"TestUnit\", \"ready\": \"false\" }}");

                    }

                    @Override
                    public void onMessage(String message) {
                        try {
                            processMsg(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {

                    }

                    @Override
                    public void onError(Exception ex) {

                    }
                };
            wsc.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
