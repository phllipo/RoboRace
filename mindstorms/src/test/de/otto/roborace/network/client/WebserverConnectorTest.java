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

		webserverConnector.connect("ws://localhost:8887",
				new OpenWebSocketCallbackHandler() {

					@Override
					public void onOpen(ServerHandshake handshakedata) {
						webserverConnector.send("hallo");

						webserverConnector.disconnect();

					}

					@Override
					public void onMessage(String message) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(Exception ex) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onClose(int code, String reason, boolean remote) {
						// TODO Auto-generated method stub

					}
				});

	}
}
