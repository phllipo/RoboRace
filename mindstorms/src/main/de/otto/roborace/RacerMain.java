package de.otto.roborace;

import java.io.IOException;
import java.io.InputStreamReader;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;

public class RacerMain {

	public static void main(String[] args) throws IOException {
		LCD.clear();
		LCD.drawString("TOETEN!", 0, 5);
		// File soundFile = new File("excellent.wav");
		// Sound.playSample(soundFile, 1000);
		Sound.beepSequenceUp();
		
        InputStreamReader sr = new InputStreamReader(System.in);
        char s;

        while ((s = (char) sr.read()) != -1) {

            if (s == 'w') {
            	Motor.C.setSpeed(770);
            	Motor.D.backward();            	
            }
            
            if (s == 'a') {
            	Sound.beep();
            	Motor.C.rotate(-20);
            }
            
            if (s == 's') {
            	Sound.beep();
            	Motor.D.forward();
            }
            
            if (s == 'd') {
            	Sound.beep();
            	Motor.C.rotate(20);
            }
            
            if (s == 'x') {
            	Motor.D.stop();
            }
            
            

        }	

	}
//	geradeaus
//	rechts 90°
//	rechts 135°
//	rechts 135°
//	links 135°
//	rechts 105°
//	rechts 120°
//	rechts 30°
}