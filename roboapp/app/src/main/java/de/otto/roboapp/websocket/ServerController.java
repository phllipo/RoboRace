package de.otto.roboapp.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import de.otto.roboapp.OnConnectionEstablished;
import de.otto.roboapp.OnMessage;

public class ServerController {
    private String serverIp;
    private String serverPort;
    private WebSocketClient wsc;

    /* Konstruktor ServerController ohne ClientName */
    public ServerController(String serverIp, String serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;

    }

    /* Getter ServerPort */
    public String getServerPort() {
        return serverPort;
    }
        /* Getter ServerIP */
    public String getServerIp() {
        return serverIp;
    }

    public void sendMsg(String msg) throws NotYetConnectedException {
        wsc.send(msg);
    }
    
    public void startWebserverConnector(final OnConnectionEstablished onConnectionEstablished,
                                        final OnMessage onMessage) {
        try {
            URI serverURI = new URI("ws://" + getServerIp() + ":" + getServerPort());
            System.out.println("connect to " + serverURI.toString());
            wsc = new WebSocketClient(serverURI) {
                @Override
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