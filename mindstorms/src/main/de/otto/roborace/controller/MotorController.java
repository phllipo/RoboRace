package de.otto.roborace.controller;

import de.otto.roborace.model.DataModel;
import lejos.hardware.motor.Motor;

import static java.lang.Thread.sleep;

/**
 * Created by luca on 06.03.15.
 */
public class MotorController {
    DataModel dataModel;
    private final SteeringController steeringController;

    public MotorController(DataModel dataModel) {
        this.dataModel = dataModel;
        System.out.println("started motorController.");

        steeringController = new SteeringController(Motor.C);
        steeringController.startSteering();

        new Thread(new Runnable() {
            @Override
            public void run() {
                controlMotors();
            }
        }).start();
    }
    private void controlMotors() {
    	Motor.D.stop();//heat up motor code
        while (true) {
        	if(dataModel.getTargetSpeed() > 0 ) {
        		Motor.D.backward();
                Motor.A.backward();
        		Motor.D.setSpeed(dataModel.getTargetSpeed());
                Motor.A.setSpeed(dataModel.getTargetSpeed());
        	} else {
        		Motor.D.forward();
                Motor.A.forward();
        		Motor.D.setSpeed(-dataModel.getTargetSpeed());
                Motor.A.setSpeed(-dataModel.getTargetSpeed());
        	}

            // detect steering change
            if(dataModel.getDesiredSteeringDirection() != null) {
                //System.out.println("process steering direction: " + dataModel.getDesiredSteeringDirection());
                steeringController.turn(dataModel.getDesiredSteeringDirection());
                dataModel.resetSteering();

            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
