package de.otto.roborace.controller;

import de.otto.roborace.controller.EventLoop.EventLoopListener;
import de.otto.roborace.model.DataModel;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * Created by luca on 27.03.15.
 */
public class CourseController implements EventLoopListener{
    private EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S1);
    private DataModel dataModel;
    private RacerController controller;
    private final SensorMode brightnessSensorMode;
    private final float[] lastSample;

    public CourseController(DataModel dataModel, RacerController controller) {
        this.dataModel = dataModel;
        this.controller = controller;
        brightnessSensorMode = sensor.getMode("Red");
        lastSample = new float[brightnessSensorMode.sampleSize()];

    }


    private boolean isCourseBoundary(float brightness) {
        return brightness > 0.8;
    }

	@Override
	public void process() {
		brightnessSensorMode.fetchSample(lastSample, 0);

        if(isCourseBoundary(lastSample[0]) && !dataModel.wasBoundaryReachedRecently()) {
            dataModel.courseBoundaryReached();
            controller.courseBoundaryReached();
        }
		
	}
}
