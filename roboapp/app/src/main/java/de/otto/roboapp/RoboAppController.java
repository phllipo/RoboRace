package de.otto.roboapp;
import android.app.Application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.otto.roboapp.activities.ActivityMontitor;
import de.otto.roboapp.activities.AbstractUpdatableActivity;
import de.otto.roboapp.activities.UpdatableActivity;
import de.otto.roboapp.model.DataModel;
import de.otto.roboapp.model.RacingData;
import de.otto.roboapp.model.SteeringDirection;
import de.otto.roboapp.util.OnFinishedCallback;
import de.otto.roboapp.websocket.ServerController;
import de.otto.roboapp.websocket.WebSocketListener;

public class RoboAppController extends Application implements ActivityMontitor {
    DataModel dataModel = new DataModel();
    ServerController serverController;
    private UpdatableActivity currentActiveActivity;

    public DataModel getDataModel() {

        return dataModel;
    }

    public void sendLocalPlayerToRoboAssignment(String roboName){

    }


    public void handleClientInformationFromJson(JSONArray jsonClientInfoArray) throws JSONException {
        String type;
        String name;
        dataModel.clearPlayerList();
        dataModel.clearRoboList();

        // For testing
        dataModel.createTestData();

        for (int i = 0; i < jsonClientInfoArray.length(); i++) {
            type = jsonClientInfoArray.getJSONObject(i).getJSONObject("clientObject").getString("type");
            name = jsonClientInfoArray.getJSONObject(i).getJSONObject("clientObject").getString("name");

            if (type.equals("app")) {
                dataModel.addPlayerToArray(name);
                if(jsonClientInfoArray.getJSONObject(i).getJSONObject("clientObject").getString("selectedRobo") != null){
                    JSONObject selectedRoboObject = jsonClientInfoArray.getJSONObject(i).getJSONObject("clientObject").getJSONObject("selectedRobo");
                    assignPlayerToRobo(name, selectedRoboObject.getString("name"));
                    System.out.println("IFAEOIFAOIFAIFAOINFWOIAON " +name+" " +selectedRoboObject.getString("name"));
                }
            } else if (type.equals("robo")) {
                dataModel.addRoboToArray(name);
            }
        }
    }

    private void handleSpeedFromJson(JSONObject data) throws JSONException {
        int speed = data.getInt("speed");
        RacingData racingData = dataModel.getRacingData();
        if (racingData != null) {
            racingData.setCurrentSpeed(speed);
        }

        currentActiveActivity.updateActivityFromBgThread();
    }



    /* Eingehende Nachrichten vom Server im JSON-Format auseinander nehmen und weiter verarbeiten */
    private void handleJsonMessage(JSONObject json) {
        try {
            String eventType = json.get("eventType").toString();
            System.out.println("EVENTTYPE: " + eventType);

            switch (eventType) {
                case "client":
                    handleClientInformationFromJson(json.getJSONArray("data"));
                    break;
                case "speed":
                    handleSpeedFromJson(json.getJSONObject("data"));
                    break;
                default:
                    System.out.println("no valid eventType: " + eventType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void playerNameEntered(final String playerName, final OnFinishedCallback onFinishedCallback) {
        serverController = new ServerController("10.0.2.1", "8888");
        dataModel.addPlayerToArray(playerName);

        serverController.connect(new WebSocketListener() {
            @Override
            public void connectionEstablished() {
                serverController.sendMsg("{\"eventType\": \"connect\", \"data\": {\"clientType\": \"app\", \"name\": \"" + playerName + "\", \"ready\": \"false\" }}");

                onFinishedCallback.onFinished();

            }

            @Override
            public void connectionEstablishmentFailed(String reason) {
                onFinishedCallback.onFailed(reason);
            }

            @Override
            public void messageReceived(JSONObject message) {
                handleJsonMessage(message);
            }

            @Override
            public void connectionClosed(String reason) {
                //show message and jump to first activity;
            }
        });


    }

    public void assignPlayerToRobo(String playerName, String roboName) {
        dataModel.assignPlayerToRobo(playerName, roboName);

    }

    public void steer(SteeringDirection steeringDirection) {
        serverController.sendMsg("{\"eventType\": \"move\", \"data\": {\"direction\": \"" + steeringDirection + "\"}}");
    }

    @Override
    public void setActiveActivity(AbstractUpdatableActivity activity) {
        currentActiveActivity = activity;
    }


}
