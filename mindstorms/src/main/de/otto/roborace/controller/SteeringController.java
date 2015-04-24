package de.otto.roborace.controller;

import de.otto.roborace.controller.EventLoop.EventLoopListener;
import de.otto.roborace.model.DataModel;
import de.otto.roborace.model.Steering;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * Created by luca on 27.03.15.
 */
public class SteeringController implements EventLoopListener {
    private NXTRegulatedMotor motor;
    private Steering steering = Steering.NONE;
    private long timeOfLastSteering = 0;
	private DataModel dataModel;

    public SteeringController(NXTRegulatedMotor motor, DataModel dataModel) {
        this.motor = motor;
		this.dataModel = dataModel;
        motor.setSpeed(75);
    }
    
    @Override
	public void process() {
    	// detect steering change
        if(dataModel.getDesiredSteeringDirection() != null) {
            //System.out.println("process steering direction: " + dataModel.getDesiredSteeringDirection());
            turn(dataModel.getDesiredSteeringDirection());
            dataModel.resetSteering();
        }
        
        Long now = System.currentTimeMillis();
        if(now - timeOfLastSteering > 2000) {
            motor.flt();
        } else {
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
    }


    public void turn(Steering steering) {
        this.steering = steering;
        timeOfLastSteering = System.currentTimeMillis();
    }
}
