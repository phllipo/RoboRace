package de.otto.roborace.controller;

import de.otto.roborace.model.DataModel;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

import java.util.Arrays;

/**
 * Created by luca on 27.03.15.
 */
public class CourseController {
    private EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S1);
    private DataModel dataModel;
    private Controller controller;
    private final SensorMode brightnessSensorMode;
    private final float[] lastSample;

    public CourseController(DataModel dataModel, Controller controller) {
        this.dataModel = dataModel;
        this.controller = controller;
        brightnessSensorMode = sensor.getMode("Red");
        lastSample = new float[brightnessSensorMode.sampleSize()];

        startCourseTracking();
    }

    private void startCourseTracking() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    brightnessSensorMode.fetchSample(lastSample, 0);


                    if(isCourseBoundary(lastSample[0])) {
                        System.out.println(Arrays.toString(lastSample));
                        dataModel.courseBoundaryReached();
                        controller.courseBoundaryReached();
                    }
                }
            }
        }).start();

    }

    private boolean isCourseBoundary(float brightness) {
        return brightness < 0.2;
    }
}
