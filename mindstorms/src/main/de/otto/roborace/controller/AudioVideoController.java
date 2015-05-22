package de.otto.roborace.controller;

import de.otto.roborace.controller.EventLoop.EventLoopListener;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class AudioVideoController implements EventLoopListener{
	private static final long LIGHTSHOW_DURATION = 3000;
	private long startTimeOfLastLightshow = 0;
	
	
	public AudioVideoController() {
		Sound.setVolume(5);
	}
	
	
	public void raceStarting() {
		Button.LEDPattern(4);	
		Sound.beepSequence();
	}
	
	public void trackLost() {
		Button.LEDPattern(2);	
		Sound.buzz();
	}
	
	public void raceFinished() {
		Button.LEDPattern(4);	
		Sound.beepSequenceUp();
	}
	
	private void startLightShow(int pattern) {
		startTimeOfLastLightshow = System.currentTimeMillis();
		Button.LEDPattern(pattern);
	}

	@Override
	public void process() {
		if(startTimeOfLastLightshow + LIGHTSHOW_DURATION < System.currentTimeMillis()) {
			startLightShow(0);
		}
		
	}

}
