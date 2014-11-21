package de.otto.roborace;

import java.io.IOException;
import java.io.InputStreamReader;
import jline.*;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;

public class RacerMain {

	public static void main(String[] args) throws IOException {
		LCD.clear();
		LCD.drawString("TOETEN!", 0, 5);
		// Motor.D.rotate(5000);
		// File soundFile = new File("excellent.wav");
		// Sound.playSample(soundFile, 1000);
		Sound.beepSequenceUp();

		int maxSpeed = 500;
		int speed = 100;

		ConsoleReader s = new ConsoleReader();
		while (true) {
			int k = s.readVirtualKey();
			System.out.println(k);
			System.out.println('w' + 'w' == k);
			// InputStreamReader sr = new InputStreamReader(System.in);
			// char s;

			// while ((s = (char) sr.read()) != -1) {

			Motor.D.getTachoCount();

			switch (k) {
			case 'w':
				Motor.D.backward();
				break;
			case 'o':
				Motor.D.setSpeed(maxSpeed);
				maxSpeed = maxSpeed + speed;
				System.out.println(Motor.D.getSpeed());
				break;
			case 'a':
				Sound.beep();
				Motor.C.rotate(25);
				break; 
			case 's':
				Sound.beep();
				Motor.D.forward();
				break; 
			case 'd':
				Sound.beep();
				Motor.C.rotate(-25);
				break; 

			case 'x':
				Motor.D.stop();
				break; 
			}

			// if(k == 'w'){
			// Motor.D.backward();
			// }
			//
			// if (k == 'o') {
			// Motor.D.setSpeed(maxSpeed);
			// maxSpeed = maxSpeed + speed;
			// System.out.println(Motor.D.getSpeed());
			// }
			//
			// if (k == 'a') {
			// Sound.beep();
			// Motor.C.rotate(25);
			// }
			//
			// if (k == 's') {
			// Sound.beep();
			// Motor.D.forward();
			// }
			//
			// if (k =='d') {
			// Sound.beep();
			// Motor.C.rotate(-25);
			// }
			//
			// if (k =='x') {
			// Motor.D.stop();
			// }
			//

		}

	}
	// geradeaus
	// rechts 90°
	// rechts 135°
	// rechts 135°
	// links 135°
	// rechts 105°
	// rechts 120°
	// rechts 30°
}