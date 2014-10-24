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
		// File soundFile = new File("excellent.wav");
		// Sound.playSample(soundFile, 1000);
		Sound.beepSequenceUp();
		
		int maxSpeed = 500;
		int speed = 100;
		
		 ConsoleReader s = new ConsoleReader();
	        while (true) {
	            int k = s.readVirtualKey();
	            System.out.println(k);
        // InputStreamReader sr = new InputStreamReader(System.in);
       // char s;

        // while ((s = (char) sr.read()) != -1) {
      
        	
        	Motor.D.getTachoCount();

            if (s.equals("w")) {
            	Motor.D.backward();            	
            }
            
            if (s.equals("o")) {
            	Motor.D.setSpeed(maxSpeed);
            	maxSpeed = maxSpeed + speed;
            	System.out.println(Motor.D.getSpeed());
            }
            
            if (s.equals("a")) {
            	Sound.beep();
            	Motor.C.rotate(25);
            }
            
            if (s.equals("s")) {
            	Sound.beep();
            	Motor.D.forward();
            }
            
            if (s.equals("d")) {
            	Sound.beep();
            	Motor.C.rotate(-25);
            }
            
            if (s.equals("x")) {
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