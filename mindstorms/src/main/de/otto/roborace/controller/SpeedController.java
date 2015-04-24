package de.otto.roborace.controller;

import de.otto.roborace.controller.EventLoop.EventLoopListener;
import de.otto.roborace.model.DataModel;
import lejos.hardware.motor.Motor;
import static java.lang.Thread.sleep;

/**
 * Created by luca on 06.03.15.
 */
public class SpeedController implements EventLoopListener{
    private DataModel dataModel;
    private RacerController controller;

    private int lastSpeed;

    public SpeedController(DataModel dataModel, RacerController controller) {
        this.dataModel = dataModel;
        this.controller = controller;
        System.out.println("started speedController.");
        Motor.D.stop();//heat up motor code
        Motor.A.stop();//heat up motor code
    }
    
	@Override
	public void process() {
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

        if(lastSpeed != Motor.A.getSpeed()) {
            controller.speedChangeDetected();
        }
        lastSpeed = Motor.A.getSpeed();

        
		
	}
}
