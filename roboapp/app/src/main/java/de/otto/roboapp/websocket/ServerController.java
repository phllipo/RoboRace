package de.otto.roboapp.websocket;

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
import de.otto.roboapp.RoboAppController;
import de.otto.roboappOld.SpeedMessageProcessor;

/**
 * Created by luca on 09.01.15.
 */

public class ServerController {
    private RoboAppController controller;
    private String serverIp;
    private String serverPort;
    private String clientName;
    private WebSocketClient wsc;

    /* Konstruktor ServerController */
    public ServerController(String serverIp, String serverPort, String clientName, RoboAppController rap) {
        setServerIp(serverIp);
        setServerPort(serverPort);
        this.clientName = clientName;
        controller = rap;

    }
    /* Konstruktor ServerController ohne ClientName */
    public ServerController(String serverIp, String serverPort, RoboAppController rap) {
        setServerIp(serverIp);
        setServerPort(serverPort);
        controller = rap;

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

    private void sendJsonMessageToController(JSONObject json) {
        controller.handleJsonMessage(json);
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

                @Override
                public void onMessage(String message) {
                    JSONObject msgJSON = null;
                    try {

                        msgJSON = new JSONObject(message);
                    } catch (JSONException e) {
                        System.out.println("no valid JSON: " + message);
                    }
                    System.out.println("Received Message: " + message);
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