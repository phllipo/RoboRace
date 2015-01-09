package com.roborace.otto.roboapp;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

/**
 * Created by luca on 19.12.14.
 */
public class WebserverConnector {
    private WebSocketClient webSocketClient;
    private boolean isConnected = false;

    public void send(String text) throws NotYetConnectedException {
        webSocketClient.send(text);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void connect(String url, final OpenWebSocketCallbackHandler callbackHandler) throws URISyntaxException {
        webSocketClient = new WebSocketClient(new URI(url)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("connected to chatserver " + getURI() + "\n");
                isConnected = true;
                callbackHandler.onOpen(handshakedata);
            }

            @Override
            public void onMessage(String message) {
                callbackHandler.onMessage(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                callbackHandler.onClose(code, reason, remote);
            }

            @Override
            public void onError(Exception ex) {
                callbackHandler.onError(ex);
            }
        };
        webSocketClient.connect();
    }
    public void disconnect() {
        webSocketClient.close();
    }
}

