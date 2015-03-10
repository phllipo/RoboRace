package de.otto.roborace.controller;

import de.otto.roborace.connection.ServerController;
import de.otto.roborace.connection.WebSocketListener;
import de.otto.roborace.model.DataModel;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

/**
 * Created by luca on 06.03.15.
 */
public class Controller {
    DataModel dataModel;
    ServerController serverController;
    MotorController motorController;
    private String serverIp = "192.168.137.155";
    private String serverPort = "8888";
    private String roboName = "rob0ne";

    public Controller() {
        dataModel = new DataModel();
        establishConnection(roboName);
        // motorController = new MotorController(dataModel);
    }

    private void establishConnection(final String roboName) {
        serverController = new ServerController(serverIp, serverPort);

        serverController.connect(new WebSocketListener() {
            @Override
            public void connectionEstablished() {
                System.out.println("connected");
                String connectMessage = "{\"eventType\": \"connect\", \"data\": {\"clientType\": \"robo\", \"name\": \"" + roboName + "\", \"ready\": \"false\" }}";

                System.out.println("sending conntect message: " + connectMessage);
                serverController.sendMsg(connectMessage);

                System.out.println("trying to start motorController");
                motorController = new MotorController(dataModel);
            }

            @Override
            public void messageReceived(JSONObject message) {
                System.out.println("MESSAGE REVEIVED: " + message.toString());
                handleJsonMsg(message);
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

    private void handleJsonMsg(JSONObject json) {
        String eventType = null;
        try {
            eventType = json.get("eventType").toString();
            System.out.println("EVENTTYPE: " + eventType);

            if (eventType.equals("move")) {
                moveMessageReceived(json.getJSONObject("data"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void changeSteerDirection(int d) {
        // d < 1 --> left, d > 1 --> right
        if(d >= 1) {
            dataModel.addSteering(10);
        } else {
            dataModel.addSteering(-10);
        }

    }

    private void changeVelocity(int v) {
        // d < 1 --> slower, d > 1 --> faster
        if(v >= 1) {
            dataModel.addVelocity(-50);
        } else {
            dataModel.addVelocity(50);
        }
    }


    private void moveMessageReceived(JSONObject data) throws JSONException {
        String direction = data.getString("direction");

        switch (direction) {
            case "LEFT":
                changeSteerDirection(0);
                break;
            case "RIGHT":
                changeSteerDirection(1);
                break;
            case "FORWARD":
                changeVelocity(1);
                break;
            case "BACKWARD":
                changeVelocity(0);
                break;
            default:
                System.out.println("no viable direction detected");
        }
    }
}