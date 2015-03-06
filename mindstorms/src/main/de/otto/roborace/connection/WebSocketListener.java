package de.otto.roborace.connection;

import org.json.JSONObject;

public interface WebSocketListener {
	
	public void connectionEstablished();

	public void messageReceived(JSONObject message);

	public void connectionEstablishmentFailed(String reason) ;

	public void connectionClosed(String reason);
}
