package de.otto.roborace.samples;

import de.otto.roborace.util.ColorId;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;

public class ColorSample {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("Running...");

		Port port = LocalEV3.get().getPort("S3");
		EV3ColorSensor ev3ColorSensor = new EV3ColorSensor(port);

		ColorId oldColor = ColorId.NONE;
		while (true) {
			ColorId newColor = ColorId.getInstance(ev3ColorSensor.getColorID());
			if (newColor != oldColor) {
				LCD.clear();
				LCD.drawString(newColor.toString(), 0, 0);
				oldColor = newColor;
			}

			Delay.msDelay(500);
		}

	}

}
