package de.otto.roboappOld;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import de.otto.roboapp.OnConnectionEstablished;
import de.otto.roboapp.OnMessage;

/**
 * Created by luca on 09.01.15.
 */

public class ServerController implements Serializable {
    private String serverIp;
    private String serverPort;
    private String clientName;
    private WebSocketClient wsc;

    /* Konstruktor ServerController */
    public ServerController(String serverIp, String serverPort, String clientName) {
        setServerIp(serverIp);
        setServerPort(serverPort);
        this.clientName = clientName;

    }
    /* Konstruktor ServerController ohne ClientName */
    public ServerController(String serverIp, String serverPort) {
        setServerIp(serverIp);
        setServerPort(serverPort);

        //startWebserverConnector();
    }
        /* Initialisieren einer ListView für die Roboter-Liste,
         die an den Server gegebene wird
          */
    private ListView selectRoboList;

    /* Setter für RoboList
     */
    public void setSelectRoboList(ListView selectRoboList) {
        this.selectRoboList = selectRoboList;
    }

    private Activity UIActivity;

    public static TextView speed;

    /* Getter UIActivity */
    public Activity getUIActivity() {
        return UIActivity;
    }

    /* Setter UIActivity */
    public void setUIActivity(Activity UIActivity) {
        this.UIActivity = UIActivity;
    }


    /* Getter ServerPort */
    public String getServerPort() {
        return serverPort;
    }
    /* Setter ServerPort */
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

        /* Getter ServerIP */
    public String getServerIp() {
        return serverIp;
    }
        /* Setter ServerIP */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
        /* getter ClientName */
    public String getClientName() {
        return clientName;
    }
        /*Setter ClientName */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /* Wofür wird das benötigt ??? */

    public void sendMsg(String msg) {
        try {
            wsc.send(msg);
        } catch (Exception e) {

        }
    }

    /* Was macht diese Methode ? */
    public void processMsg(String msg) throws JSONException {
        JSONObject msgJSON = new JSONObject(msg);
        if (msgJSON.getString("eventType").equals("speed")) {
            new SpeedMessageProcessor(speed).process(msgJSON, getUIActivity());
        }
    }

    public void startWebserverConnector(final OnConnectionEstablished onConnectionEstablished,
                                        final OnMessage onMessage) {
        try {
            URI serverURI = new URI("ws://" + getServerIp() + ":" + getServerPort());
            System.out.println("connect to " + serverURI.toString());
            wsc = new WebSocketClient(serverURI) {
                @Override
                    /* App benutz onOpen nicht*/
                public void onOpen(ServerHandshake handshakedata) {
                    try {
                        System.out.println("connection established");
                        onConnectionEstablished.connectionEstablished();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                /* App benutz onMessage nicht*/
                @Override
                public void onMessage(String message) {
                    JSONObject msgJSON = null;
                    try {
                        //processMsg(message);
                        msgJSON = new JSONObject(message);
                    } catch (JSONException e) {
                        System.out.println("no valid JSON: " + message);
                    }
                    onMessage.messageReceived(msgJSON);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("onClose Reason: " + reason);
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