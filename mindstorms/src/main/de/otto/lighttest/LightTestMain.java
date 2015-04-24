package de.otto.lighttest;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * Created by luca on 24.04.15.
 */
public class LightTestMain {

    private static EV3ColorSensor sensor;
    private static SensorMode brightnessSensorMode;

    public static void main(String[] args) {
        sensor = new EV3ColorSensor(SensorPort.S1);
        sensor.setFloodlight(false);
        LCD.drawString("Init", 2, 2);
        LCD.setAutoRefresh(false);
        brightnessSensorMode = sensor.getRGBMode();
        float[] sample = new float[brightnessSensorMode.sampleSize()];

        while(true) {
            brightnessSensorMode.fetchSample(sample, 0);
            LCD.refresh();
            LCD.clear();
            System.out.println("R: " + sample[0] + " G: " + sample[1] + " B: " + sample[2]);
            LCD.drawString("R: " + sample[0], 1, 1);
            LCD.drawString("G: " + sample[1], 1, 2);
            LCD.drawString("B: " + sample[2], 1, 3);

        }

    }
}
