package de.otto.roborace.model;

/**
 * Created by luca on 06.03.15.
 */
public class DataModel {
	private static final int STEERING_MAX = 40;

	private int targetSpeed;

	private Steering desiredSteeringDirection = Steering.NONE;

	public Steering getDesiredSteeringDirection() {
		return desiredSteeringDirection;
	}

	public void setDesiredSteeringDirection(Steering desiredSteeringDirection) {
		this.desiredSteeringDirection = desiredSteeringDirection;
	}

	public int getTargetSpeed() {
		return targetSpeed;
	}

	public void resetSteering() {
		desiredSteeringDirection = null;
	}

	public void setTargetSpeed(int targetSpeed) {
		this.targetSpeed = targetSpeed * 15;
		
	}
}
