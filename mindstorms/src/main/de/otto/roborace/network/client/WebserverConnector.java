package de.otto.roborace.network.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.WebSocketImpl;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;

public class WebserverConnector{
	
	private WebSocketClient webSocketClient;
	private boolean isConnected = false;

	public void send( String text ) throws NotYetConnectedException {
		System.out.println("SpeedTest123");
		webSocketClient.send( text );
			System.out.println("Test123");
	}
	
	public boolean isConnected() {
		return isConnected;
	}

	public void connect(String url, final OpenWebSocketCallbackHandler callbackHandler) throws URISyntaxException {

		webSocketClient = new WebSocketClient(new URI(url)) {

			@Override
			public void onOpen(final ServerHandshake handshakedata) {
				System.out.println("You are connected to server: "
						+ getURI() + "\n");
				isConnected = true;
				callbackHandler.onOpen(handshakedata);	
			}

			@Override
			public void onMessage(String message) {
				callbackHandler.onMessage(message);
			}

			@Override
			public void onError(Exception ex) {
				callbackHandler.onError(ex);
			}

			@Override
			public void onClose(int code, String reason, boolean remote) {
				callbackHandler.onClose(code, reason, remote);

			}
		};
		webSocketClient.connect();
	}

	public void disconnect() {
		webSocketClient.close();
	}

}
