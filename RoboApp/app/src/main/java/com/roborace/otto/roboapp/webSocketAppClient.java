package com.roborace.otto.roboapp;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URISyntaxException;

/**
 * Created by luca on 19.12.14.
 */
public class webSocketAppClient {
    private WebserverConnector webserverConnector;

    public void createConnectionToServer() throws URISyntaxException {
        webserverConnector = new WebserverConnector();
        webserverConnector.connect("ws://10.90.162.186:8887",
                new OpenWebSocketCallbackHandler() {
                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
                        System.out.println("verbunden");;
                    }

                    @Override
                    public void onMessage(String message) {
                        System.out.println("On message: " + message);
                    }

                    @Override
                    public void onError(Exception ex) {
                        System.out.println("Error");
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        System.out.println("OnClose: " + "Code " + code + " reason: " + reason + " remote: " + remote);
                    }
                });
    }
}
