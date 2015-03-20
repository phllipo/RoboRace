package de.otto.roborace.model;

/**
 * Created by luca on 06.03.15.
 */
public class DataModel {
	private static final int STEERING_MAX = 40;
	private static final int VELOCITY_MIN = -300;
	private static final int VELOCITY_MAX = 1500;

	private int velocityChange;

	public int getVelocityTotal() {
		return velocityTotal;
	}

	private int velocityTotal;

	private int steeringChange;
	private int steeringTotal;

	public DataModel() {
		velocityChange = 0;
		velocityTotal = 0;
		steeringChange = 0;
		steeringTotal = 0;
	}

	public void addVelocity(int v) {
		if (velocityTotal + v >= VELOCITY_MIN && velocityTotal + v <= VELOCITY_MAX) {
			velocityChange += v;
			velocityTotal += v;
		}
		System.out.println("vel total: " + velocityTotal);
	}

	public void addSteering(int s) {
		System.out.println("steeringTotal: " + steeringTotal + "s: " + s);
		steeringChange += s;
		steeringTotal += s;
		
	}

	public int getVelocityChange() {
		return velocityChange;
	}

	public int getSteeringChange() {
		return steeringChange;
	}

	public void resetVelocityChange() {
		velocityChange = 0;
	}

	public void resetSteeringChange() {
		steeringChange = 0;
	}
}
