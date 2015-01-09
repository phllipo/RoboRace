package de.otto.roboapp;

import org.java_websocket.handshake.ServerHandshake;

/**
 * Created by luca on 19.12.14.
 */
public interface OpenWebSocketCallbackHandler {
    public void onOpen(ServerHandshake handshakedata);

    public void onMessage(String message);

    public void onError(Exception ex);

    public void onClose(int code, String reason, boolean remote);
}
