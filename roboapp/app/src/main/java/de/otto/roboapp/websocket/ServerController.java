package de.otto.roboapp.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

public class ServerController {
    private String serverIp;
    private String serverPort;
    private WebSocketClient wsc;
    private ConnectionState connectionState = ConnectionState.NOT_CONNECTED;

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
    
    public void connect(final WebSocketListener listener) {
        URI serverURI = createUri();
        if(connectionState != ConnectionState.NOT_CONNECTED) {
            throw new IllegalStateException("Connection cant be opened. Connection is currently in state: " + connectionState);
        }
        connectionState = ConnectionState.CONNECTING;

        System.out.println("ServerController: Trying to connect to " + serverURI.toString());
        wsc = new WebSocketClient(serverURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                connectionState = ConnectionState.CONNECTED;
                System.out.println("ServerController: Connection established");
                listener.connectionEstablished();
            }

            @Override
            public void onMessage(String message) {
                JSONObject msgJSON;
                try {
                    msgJSON = new JSONObject(message);
                    System.out.println("ServerController: Message received: " + message);
                    listener.messageReceived(msgJSON);
                } catch (JSONException e) {
                    System.out.println("ServerController: Message received and discarded, because it contained no valid JSON: " + message);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                String closeReason = "Reason: " + reason + ", code: " + code + ", remote: " + remote;
                if (connectionState == ConnectionState.CONNECTING) {
                    listener.connectionEstablishmentFailed(closeReason);
                } else {
                   listener.connectionClosed(closeReason);
                }
                connectionState = ConnectionState.NOT_CONNECTED;
                wsc.close();
                wsc = null;
                System.out.println("ServerController: Connection closed. Reason: " + reason + ", code: " + code + ", remote: " + remote);
            }

            @Override
            public void onError(Exception ex) {
                System.out.println("ServerController: Error received: " + ex.getMessage());
                ex.printStackTrace();
            }
        };
        wsc.connect();
    }

    private URI createUri() {
        URI serverURI;
        String uriString = null;
        try {
            uriString = "ws://" + getServerIp() + ":" + getServerPort();
            serverURI = new URI(uriString);
        } catch (URISyntaxException e) {
            throw new RuntimeException("ServerController: Not a valid URI: " + uriString);
        }
        return serverURI;
    }


}