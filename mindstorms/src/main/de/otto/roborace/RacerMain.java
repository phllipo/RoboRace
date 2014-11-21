package de.otto.roborace;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import de.otto.roborace.network.client.RoboClient;
import de.otto.roborace.network.client.WebserverConnector;
import jline.*;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;

public class RacerMain {

	public static void main(String[] args) throws IOException,
			URISyntaxException {
		LCD.clear();
		LCD.drawString("TOETEN!", 0, 5);
		// Motor.D.rotate(5000);
		// File soundFile = new File("excellent.wav");
		// Sound.playSample(soundFile, 1000);
		Sound.beepSequenceUp();
		RoboClient roboClient = new RoboClient();
		roboClient.createConnectionToServer();

		int maxSpeed = 500;

		ConsoleReader s = new ConsoleReader();
		while (true) {
			int key = s.readVirtualKey();
			System.out.println(key);
			System.out.println('w' + 'w' == key);
			// InputStreamReader sr = new InputStreamReader(System.in);
			// char s;

			roboClient.sendCurrentSpeed(Motor.D.getSpeed());

			// forward/backward Target Ziel setzten Tasten von 1-7 setzten, 7
			// ist max.
			// Geschwindigkeit und 4 ist halbe. Definieren, dass die Tasten
			// immer hundert mehr Speed geben
			// rechts/links Grad angeben, um richtige Kurve zu definieren, z.B.
			// -300 bis 300

			switch (key) {
			case '4':
				Motor.D.backward();
				Motor.D.setSpeed(-200);
				break;
			case '5':
				Motor.D.backward();
				Motor.D.setSpeed(-300);
				break;
			case '6':
				Motor.D.backward();
				Motor.D.setSpeed(-400);
				break;
			case '7':
				Motor.D.backward();
				Motor.D.setSpeed(-500);
				break;
			case '8':
				Motor.D.backward();
				Motor.D.setSpeed(-600);
				break;
			case '9':
				Motor.D.backward();
				Motor.D.setSpeed(-700);
				break;
			case '0':
				Motor.D.backward();
				Motor.D.setSpeed(-800);
				System.out.println(Motor.D.getSpeed());
				break;

			case '1':
				Sound.beep();
				Motor.D.forward();
				Motor.D.setSpeed(200);
				break;
			case '2':
				Sound.beep();
				Motor.D.forward();
				Motor.D.setSpeed(300);
				break;
			case '3':
				Sound.beep();
				Motor.D.forward();
				Motor.D.setSpeed(700);
				break;
				
			case 'a':
				Sound.beep();
				Motor.C.rotate(100);
				break;
			case 's':
				Sound.beep();
				Motor.C.rotate(50);
				break;
			case 'd':
				Sound.beep();
				Motor.C.rotate(25);
				break;
			case 'f':
				Sound.beep();
				Motor.C.rotate(-25);
				break;
			case 'g':
				Sound.beep();
				Motor.C.rotate(-50);
				break;
			case 'h':
				Sound.beep();
				Motor.C.rotate(-100);
				break;

			case 'x':
				Motor.D.stop();
				break;
			}

			// switch (k) {
			// case 'w':
			// Motor.D.backward();
			// break;
			// case 'o':
			// Motor.D.setSpeed(maxSpeed);
			// if (maxSpeed >= 700) {
			// System.out.println("Maximalgeschwindigkeit erreicht");
			// } else {
			// maxSpeed = maxSpeed + speed;
			// }
			// System.out.println(Motor.D.getSpeed());
			// break;
			// case 'a':
			// Sound.beep();
			// Motor.C.rotate(25);
			// break;
			// case 's':
			// Sound.beep();
			// Motor.D.forward();
			// break;
			// case 'd':
			// Sound.beep();
			// Motor.C.rotate(-25);
			// break;
			//
			// case 'x':
			// Motor.D.stop();
			// break;
			// }

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