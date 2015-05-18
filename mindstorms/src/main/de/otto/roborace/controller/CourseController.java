package de.otto.roborace.controller;

import de.otto.roborace.controller.EventLoop.EventLoopListener;
import de.otto.roborace.model.DataModel;
import de.otto.roborace.model.RacingState;
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
        brightnessSensorMode = sensor.getRGBMode();
        lastSample = new float[brightnessSensorMode.sampleSize()];

    }


    private boolean isCourseBoundary(float[] sample) {
        float r = sample[0];
        float g = sample[1];
        float b = sample[2];

        return r > 1.;
    }

    private boolean isFinishingLine(float[] sample) {
        float r = sample[0];
        float g = sample[1];
        float b = sample[2];

        return r < 0.06 && g < 0.06 && b < 0.06;
    }

	@Override
	public void process() {
		brightnessSensorMode.fetchSample(lastSample, 0);

        if(dataModel.getRacingState().equals(RacingState.RACE)) {
            if (isCourseBoundary(lastSample) && !dataModel.wasBoundaryReachedRecently()) {
                dataModel.courseBoundaryReached();
                controller.courseBoundaryReached();
            } else if (isFinishingLine(lastSample)) {
                System.out.println("Finish line reached | r:" + lastSample[0] + " g:" + lastSample[1] + " b:" + lastSample[2]);
                controller.finishLineReached();
            }
        }
	}
}
