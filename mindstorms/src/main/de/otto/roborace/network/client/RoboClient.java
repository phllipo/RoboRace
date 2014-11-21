package de.otto.roborace.network.client;

import java.net.URISyntaxException;

import org.java_websocket.handshake.ServerHandshake;

public class RoboClient {
	private WebserverConnector webserverConnector; 
	
	public void createConnectionToServer() throws URISyntaxException {
		webserverConnector = new WebserverConnector();
		webserverConnector.connect("ws://10.90.159.197:8887",
				new OpenWebSocketCallbackHandler()

				{

					@Override
					public void onOpen(ServerHandshake handshakedata) {
						System.out.println("Verbunden");
					}

					@Override
					public void onMessage(String message) {
						System.out.println("OnMessage: " + message);

					}

					@Override
					public void onError(Exception ex) {
						System.out.println("Error");

					}

					@Override
					public void onClose(int code, String reason, boolean remote) {
						System.out.println("OnClose :" + "Code "+ code+"reason :"+ reason+ "remote: "+ remote);

					}
				});

	}

	public void sendCurrentSpeed(int tachoCount) {
		if ( webserverConnector.isConnected()) {
			webserverConnector.send("{eventType: 'Speed', data: {speed: "+tachoCount+"}}");
		}
		
	}

}