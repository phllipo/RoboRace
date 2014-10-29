package de.otto.roborace.network.client;

import org.java_websocket.handshake.ServerHandshake;

public interface OpenWebSocketCallbackHandler {
	
	public void onOpen(ServerHandshake handshakedata);

	public void onMessage(String message);

	public void onError(Exception ex) ;

	public void onClose(int code, String reason, boolean remote);
}
