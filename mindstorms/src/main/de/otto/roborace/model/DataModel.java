package de.otto.roborace.model;

/**
 * Created by luca on 06.03.15.
 */
public class DataModel {
	private static final int STEERING_MAX = 40;


	private int steeringChange;
	private int steeringTotal;
	private int targetSpeed;

	public DataModel() {
		steeringChange = 0;
		steeringTotal = 0;
	}


	public void addSteering(int s) {
		System.out.println("steeringTotal: " + steeringTotal + "s: " + s);
		steeringChange += s;
		steeringTotal += s;
		
	}


	public int getSteeringChange() {
		return steeringChange;
	}
	
	public int getTargetSpeed() {
		return targetSpeed;
	}


	public void resetSteeringChange() {
		steeringChange = 0;
	}

	public void setTargetSpeed(int targetSpeed) {
		this.targetSpeed = targetSpeed * 15;
		
	}
}
