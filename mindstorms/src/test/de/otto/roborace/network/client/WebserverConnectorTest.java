package de.otto.roborace.network.client;

import java.net.URISyntaxException;

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
				new OpenWebSocketCallbackHandler() {

					@Override
					public void onOpen(ServerHandshake handshakedata) {
						System.out.println("onOpen message");
					}

					@Override
					public void onMessage(String message) {
						System.out.println("message: " + message);
					}

					@Override
					public void onError(Exception ex) {
						System.out.println("Error: " + ex.getMessage());

					}

					@Override
					public void onClose(int code, String reason, boolean remote) {
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
