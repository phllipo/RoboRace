package de.otto.roborace.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import de.otto.roborace.model.RacingState;
import lejos.hardware.motor.Motor;
import de.otto.roborace.connection.ServerController;
import de.otto.roborace.connection.WebSocketListener;
import de.otto.roborace.model.DataModel;
import de.otto.roborace.model.Steering;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by luca on 06.03.15.
 */
public class RacerController {
    DataModel dataModel;
    ServerController serverController;
    SpeedController motorController;
    CourseController courseController;
	private Properties properties;
	private EventLoop eventLoop = new EventLoop();
	private AudioVideoController audioVideoController;

    public RacerController() {
    	properties = loadProperties();
        dataModel = new DataModel();
        
        System.out.println("start event loop");
        audioVideoController = new AudioVideoController();
        eventLoop.register(new SpeedController(dataModel, RacerController.this));
        eventLoop.register(new SteeringController(Motor.C, dataModel));
        eventLoop.register(new CourseController(dataModel, RacerController.this));        
		eventLoop.register(audioVideoController);
        eventLoop.start();
        
        establishConnection();
        // motorController = new MotorController(dataModel);
        // courseController = new CourseController(dataModel);
    }

    private Properties loadProperties() {
    	Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("racerRobo.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return properties;
	}

	private void establishConnection() {
        serverController = new ServerController(properties.getProperty("serverIp"), properties.getProperty("serverPort", "8888"));

        serverController.connect(new WebSocketListener() {
            @Override
            public void connectionEstablished() {
                System.out.println("connected");
                String connectMessage = "{\"eventType\": \"connect\", \"data\": {\"clientType\": \"robo\", \"name\": \"" + properties.getProperty("roboName") + "\", \"ready\": \"false\" }}";

                System.out.println("sending conntect message: " + connectMessage);
                serverController.sendMsg(connectMessage);
                
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

            switch (eventType) {
                case "move":
                    moveMessageReceived(json.getJSONObject("data"));
                    break;
                case "startRace":
                    startMessageReceived();
                    break;
                case "results":
                    resultsMessageReceived();
                    break;
                default:
                    System.out.println("Invalid eventType: " + eventType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void resultsMessageReceived() {
        // when this message is received, the race is over for this roboter
        dataModel.setRacingState(RacingState.NORACE);
    }

    private void startMessageReceived() {
        dataModel.setRacingState(RacingState.RACE);
        audioVideoController.raceStarting();
    }

    public void courseBoundaryReached() {
        serverController.sendBoundaryReachedMessage();
        audioVideoController.trackLost();
    }

    public void finishLineReached() {
        serverController.sendFinishLineReachedMessage();
        audioVideoController.raceFinished();
    }

    public void speedChangeDetected() {
        serverController.sendSpeedMessage(dataModel.getTargetSpeed());
    }

    private void changeSteerDirection(Steering direction) {
        dataModel.setDesiredSteeringDirection(direction);
    }

    private void changeSpeed(int speed) {
    	dataModel.setTargetSpeed(speed);
		
	}

    private void moveMessageReceived(JSONObject data) throws JSONException {
    	if(data.has("speed")) {
    		String speedString = data.getString("speed");
    		changeSpeed(Integer.parseInt(speedString));
    	} else {
	        String direction = data.getString("direction");
	
	        switch (direction) {
	            case "LEFT":
	                changeSteerDirection(Steering.LEFT);
	                break;
	            case "RIGHT":
	                changeSteerDirection(Steering.RIGHT);
	                break;
                case "NONE":
                    changeSteerDirection(Steering.NONE);
                    break;
	            default:
	                System.out.println("no viable direction detected");
	        }
    	}
    }

	
}