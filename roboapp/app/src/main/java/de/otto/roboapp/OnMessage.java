package de.otto.roboapp;

import org.json.JSONObject;

public interface OnMessage {
    public void messageReceived(JSONObject message);
}
