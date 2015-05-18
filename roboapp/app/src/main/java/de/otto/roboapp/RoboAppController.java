package de.otto.roboapp;

import android.app.Application;
import android.content.Context;
import android.os.Vibrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.otto.roboapp.model.DataModel;
import de.otto.roboapp.model.Player;
import de.otto.roboapp.model.RacingData;
import de.otto.roboapp.model.RacingState;
import de.otto.roboapp.model.SteeringDirection;
import de.otto.roboapp.ui.activities.ResultsActivity;
import de.otto.roboapp.ui.activities.SteeringActivity;
import de.otto.roboapp.ui.activities.base.ActivityMontitor;
import de.otto.roboapp.ui.activities.base.UpdatableActivity;
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

    @Override
    public void setActiveActivity(UpdatableActivity activity) {
        currentActiveActivity = activity;
    }

    @Override
    public UpdatableActivity getActiveActivity() {
        return currentActiveActivity;
    }


    //-----------------------  Methods for processing events from backend -------------------//

    public void handleClientInformationFromJson(JSONArray jsonClientInfoArray) throws JSONException {
        String type;
        String name;
        String stringReady;
        boolean ready;
        dataModel.clearRoboList();
        dataModel.clearPlayerList();
        dataModel.clearPlayerToRoboAssignmentMap();

        for (int i = 0; i < jsonClientInfoArray.length(); i++) {
            JSONObject clientObject = jsonClientInfoArray.getJSONObject(i).getJSONObject("clientObject");
            type = clientObject.getString("type");
            name = clientObject.getString("name");
            stringReady = clientObject.getString("ready");
            ready = Boolean.valueOf(stringReady);

            if (type.equals("app")) {
                dataModel.addPlayerToArray(name, ready);

            } else if (type.equals("robo")) {
                    dataModel.addRoboToArray(name);
            }
        }

        for (int i = 0; i < jsonClientInfoArray.length(); i++) {
            JSONObject clientObject = jsonClientInfoArray.getJSONObject(i).getJSONObject("clientObject");
            type = clientObject.getString("type");
            name = clientObject.getString("name");
            if (type.equals("app")) {

                if(clientObject.has("selectedRobo") && !"null".equals(clientObject.getString("selectedRobo"))){
                    JSONObject selectedRoboObject = clientObject.getJSONObject("selectedRobo");
                    dataModel.assignPlayerToRobo(name, selectedRoboObject.getString("name"));

                }

            }
        }

        if (currentActiveActivity != null) {
            currentActiveActivity.updateActivityFromBgThread();
        }
    }

    private void handleSpeedFromJson(JSONObject data) throws JSONException {
        int speed = data.getInt("speed");


        RacingData racingData = dataModel.getRacingData();
        if (racingData != null) {
            racingData.setCurrentSpeed(speed/6);
        }

        if (currentActiveActivity != null) {
            currentActiveActivity.updateActivityFromBgThread();
        }
    }

    private void handleCountdownStart() throws JSONException {
        dataModel.getRacingData().initiatedCountdown();
        currentActiveActivity.switchActivity(SteeringActivity.class);
    }

    private void handleResultsFromJson(JSONArray data) throws JSONException {
        for(int i = 0; i < data.length(); i++) {
            dataModel.getRacingData().setRacingState(RacingState.FINISHED);

            // ergebnisse in dataModel speichern
            dataModel.clearPlayerToResultMap();

            JSONObject result = data.getJSONObject(i).getJSONObject("resultObject");
            Player player = dataModel.getPlayerByName(result.getString("name"));
            Integer timeInSeconds = result.getInt("time");

            dataModel.addPlayerToResultMap(player, timeInSeconds);

        }
        UpdatableActivity activeActivity = getActiveActivity();
        System.out.println("onResults: current activity: " + activeActivity.toString() + " " + activeActivity + " " + ResultsActivity.class);
        if(!activeActivity.equals(ResultsActivity.class)) {
            activeActivity.switchActivity(ResultsActivity.class);
        }
        getActiveActivity().updateActivityFromBgThread();
    }

    private void handleStartRace() {
        dataModel.getRacingData().initiatRaceStart();
        currentActiveActivity.updateActivityFromBgThread();
    }

    private void leftTrackVibration() {
        Vibrator v = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1000);
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
                case "countdownStart":
                    handleCountdownStart();
                    break;
                case "startRace":
                    handleStartRace();
                    break;
                case "leftTrack":
                    leftTrackVibration();
                    break;
                case "results":
                    handleResultsFromJson(json.getJSONArray("data"));
                    break;
                default:
                    System.out.println("no valid eventType: " + eventType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-----------------------  Methods for processing events from user -------------------//

    public void playerNameEntered(final String playerName, String ipAddress, final OnFinishedCallback onFinishedCallback) {
        serverController = new ServerController(ipAddress, "8888");
        dataModel.setCurrentPlayerName(playerName);

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

    public void roboSelected(String roboName) {
        serverController.sendMsg("{ \"eventType\": \"selectRobo\", \"data\": { \"robo\": \"" + roboName + "\" }}");

        //testing:
        //dataModel.assignPlayerToRobo(dataModel.currentPlayerName, roboName);
    }

    public void steer(SteeringDirection steeringDirection) {
        serverController.sendMsg("{\"eventType\": \"move\", \"data\": {\"direction\": \"" + steeringDirection + "\"}}");
    }


    public void steer(long progress) {
        serverController.sendMsg("{\"eventType\": \"move\", \"data\": {\"speed\": \"" + progress + "\"}}");
    }

    public void deselect() {
        serverController.sendMsg("{\"eventType\": \"deselectRobo\"}");
    }

    public void readyStateChange(boolean readyState) {
        serverController.sendMsg("{\"eventType\": \"ready\", \"data\": { \"ready\": \"" + readyState + "\" }}");
    }

      }

