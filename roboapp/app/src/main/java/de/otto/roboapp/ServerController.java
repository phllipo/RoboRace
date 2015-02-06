package de.otto.roboapp;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ListView;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by luca on 09.01.15.
 */

public class ServerController implements Serializable {
    private String serverIp;
    private String serverPort;
    private boolean isConnected = false;
    private String clientName;
    private WebSocketClient wsc;

    public ListView getSelectRoboList() {
        return selectRoboList;
    }

    public void setSelectRoboList(ListView selectRoboList) {
        this.selectRoboList = selectRoboList;
    }

    private ListView selectRoboList;

    public boolean isConnected() {
        return isConnected;
    }

    public WebSocketClient getWsc() {
        return wsc;
    }

    public Activity getUIActivity() {
        return UIActivity;
    }

    public void setUIActivity(Activity UIActivity) {
        this.UIActivity = UIActivity;
    }

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

    public ServerController(String serverIp, String serverPort, String clientName) {
        setServerIp(serverIp);
        setServerPort(serverPort);
        this.clientName = clientName;

        startWebserverConnector();
    }
    public ServerController(String serverIp, String serverPort) {
        setServerIp(serverIp);
        setServerPort(serverPort);

        startWebserverConnector();
    }
    public void sendMsg(String msg) {
        try {
            wsc.send(msg);
        } catch (Exception e) {

        }
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
                        try {
                            this.send("test");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //this.send("{\"eventType\": \"connect\", \"data\": {\"clientType\": \"app\", \"name\": \"" + clientName + "\", \"ready\": \"false\" }}\"");
                    }

                    @Override
                    public void onMessage(String message) {
                        try {
                            processMsg(message);
                        } catch (JSONException e) {
                            System.out.println("no valid JSON");
                        }
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        System.out.println("ONCLOSE " + reason);
                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                    }
                };
            wsc.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}