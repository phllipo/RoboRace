package de.otto.roborace.model;

/**
 * Created by luca on 06.03.15.
 */
public class DataModel {
	private static final int STEERING_MAX = 40;

	private int targetSpeed;

	private Steering steeringDirection = Steering.NONE;

	public Steering getSteeringDirection() {
		return steeringDirection;
	}

	public void setSteeringDirection(Steering steeringDirection) {
		this.steeringDirection = steeringDirection;
	}

	public int getTargetSpeed() {
		return targetSpeed;
	}

	public void resetSteering() {
		steeringDirection = Steering.NONE;
	}

	public void setTargetSpeed(int targetSpeed) {
		this.targetSpeed = targetSpeed * 15;
		
	}
}
