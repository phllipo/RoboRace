package de.otto.roborace.network.client;

import java.net.URISyntaxException;

import de.otto.roborace.connection.WebSocketListener;
import de.otto.roborace.connection.WebserverConnector;
import org.java_websocket.handshake.ServerHandshake;
import org.testng.annotations.*;

public class WebserverConnectorTest {

	@BeforeClass
	public void setUp() {
		// code that will be invoked when this test is instantiated
	}

	@Test
	public void createConnectionToServer() throws URISyntaxException {
		final WebserverConnector webserverConnector = new WebserverConnector();
		
		webserverConnector.connect("ws://localhost:8000",
				new WebSocketListener() {

					@Override
					public void connectionEstablished(ServerHandshake handshakedata) {
						System.out.println("connectionEstablished message");
					}

					@Override
					public void messageReceived(String message) {
						System.out.println("message: " + message);
					}

					@Override
					public void connectionEstablishmentFailed(Exception ex) {
						System.out.println("Error: " + ex.getMessage());

					}

					@Override
					public void connectionClosed(int code, String reason, boolean remote) {
						System.out.println("OnClose :" + "Code "+ code+"reason :"+ reason+ "remote: "+ remote);

					}
				});
		while(true) {
			try {
				Thread.sleep(1000);
				if(webserverConnector.isConnected()) {
					webserverConnector.send("{\"eventtype\": \"speed\", \"data\": {\"speed\": \"800\"}}");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
