package de.otto.roborace.controller;

import de.otto.roborace.connection.ServerController;
import de.otto.roborace.connection.WebSocketListener;
import de.otto.roborace.model.DataModel;
import org.json.JSONObject;

/**
 * Created by luca on 06.03.15.
 */
public class Controller {
    DataModel dataModel;
    ServerController serverController;
    private String serverIp = "192.168.178.45";
    private String serverPort = "8888";
    private String roboName = "rob0ne";

    public Controller() {
        dataModel = new DataModel();
        establishConnection(roboName);
    }

    private void establishConnection(final String roboName) {
        serverController = new ServerController(serverIp, serverPort);

        serverController.connect(new WebSocketListener() {
            @Override
            public void connectionEstablished() {
                System.out.println("connected");
                serverController.sendMsg("{\"eventType\": \"connect\", \"data\": {\"clientType\": \"robo\", \"name\": \"" + roboName + "\", \"ready\": \"false\" }}");
            }

            @Override
            public void messageReceived(JSONObject message) {
                System.out.println("MESSAGE REVEIVED: " + message.toString());
            }

            @Override
            public void connectionEstablishmentFailed(String reason) {
                System.out.println("CONNECTION FAILED: " + reason);
            }

            @Override
            public void connectionClosed(String reason) {
                System.out.println("CONNECTION CLOSED: " + reason);
            }
        });
    }
}
