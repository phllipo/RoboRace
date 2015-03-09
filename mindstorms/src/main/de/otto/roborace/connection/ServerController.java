package de.otto.roborace.connection;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerController {
	private String serverIp;
	private String serverPort;
	private WebSocketClient wsc;
	private ConnectionState connectionState = ConnectionState.NOT_CONNECTED;


	public ServerController(String serverIP, String serverPort) {
		this.serverIp = serverIP;
		this.serverPort = serverPort;
	}

	public void connect(final WebSocketListener listener) {
		URI serverURI = createUri();
		System.out.println("Try to connect");

		if(connectionState != ConnectionState.NOT_CONNECTED) {
			throw new IllegalStateException("Connection cant be opened. Connection is currently in state: " + connectionState);
		}
		connectionState = ConnectionState.CONNECTING;

		System.out.println("ServerController: Trying to connect to server");

		wsc = new WebSocketClient(serverURI) {
			@Override
			public void onOpen(ServerHandshake serverHandshake) {
				listener.connectionEstablished();
			}

			@Override
			public void onMessage(String s) {
				try {
					listener.messageReceived(new JSONObject(s));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onClose(int i, String s, boolean b) {
				listener.connectionClosed(s);
			}

			@Override
			public void onError(Exception e) {
				listener.connectionEstablishmentFailed(e.toString());
			}
		};
		wsc.connect();


	}

	public void sendMsg(String msg) throws NotYetConnectedException {
		wsc.send(msg);
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

	public String getServerIp() {
		return serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}
	/*public void sendCurrentSpeed(int tachoCount) {
		if ( webserverConnector.isConnected()) {
			webserverConnector.send("{\"eventType\": \"speed\", \"data\": {\"speed\": \""+tachoCount+"\"}}");
		}
*/

}