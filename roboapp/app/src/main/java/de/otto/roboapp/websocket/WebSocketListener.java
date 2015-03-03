package de.otto.roboapp.websocket;

import org.json.JSONObject;

public interface WebSocketListener {

    public void connectionEstablished();

    public void connectionEstablishmentFailed(String reason);

    public void messageReceived(JSONObject message);

    public void connectionClosed(String reason);
}
