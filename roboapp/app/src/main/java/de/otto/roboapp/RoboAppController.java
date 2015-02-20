package de.otto.roboapp;
import android.app.Application;

import org.json.JSONObject;

import de.otto.roboapp.model.DataModel;
import de.otto.roboapp.model.SteeringDirection;
import de.otto.roboapp.util.OnFinishedCallback;
import de.otto.roboapp.websocket.ServerController;

public class RoboAppController extends Application {
    DataModel dataModel = new DataModel();
    ServerController serverController;

    public DataModel getDataModel(){

        return dataModel;
    }

    public void playerNameEntered(final String playerName, final OnFinishedCallback onFinishedCallback) {
        serverController = new ServerController("10.0.2.2", "8888");
       dataModel.addPlayerToArray(playerName);

        serverController.startWebserverConnector(new OnConnectionEstablished() {
            @Override
            public void connectionEstablished() {
                serverController.sendMsg("{\"eventType\": \"connect\", \"data\": {\"clientType\": \"app\", \"name\": \"" + playerName + "\", \"ready\": \"false\" }}");

                onFinishedCallback.onFinished();

            }
        }, new  OnMessage() {
            @Override
            public void messageReceived(JSONObject message) {
                //entsprechen model aufrufen
            }
        }); //langlaufend


    }

    public void assignPlayerToRobo(String roboName) {
        dataModel.assignPlayerToRobo(roboName);

    }

    public void steer(SteeringDirection steeringDirection) {
        serverController.sendMsg("{\"eventType\": \"move\", \"data\": {\"direction\": \"" + steeringDirection + "\"}}");
    }
}
