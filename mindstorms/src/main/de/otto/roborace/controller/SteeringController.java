package de.otto.roborace.controller;

import de.otto.roborace.model.Steering;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * Created by luca on 27.03.15.
 */
public class SteeringController {
    private NXTRegulatedMotor motor;
    private Steering steering = Steering.NONE;
    private long timeOfLastSteering = 0;

    public SteeringController(NXTRegulatedMotor motor) {
        this.motor = motor;
        motor.setSpeed(75);
    }

    public void startSteering() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Long now = System.currentTimeMillis();
                    if(now - timeOfLastSteering > 2000) {
                        motor.flt();
                    } else {
                        System.out.println("Steering change detected: " + steering);
                       switch(steering) {
                           case LEFT:
                               motor.forward();
                               break;
                           case RIGHT:
                               motor.backward();
                               break;
                           case NONE:
                               motor.stop();
                               break;
                           default:
                               motor.stop();
                       }
                    }
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void turn(Steering steering) {
        this.steering = steering;
        timeOfLastSteering = System.currentTimeMillis();
    }
}
