package de.otto.roborace.controller;

public enum CourseColor {
	WHITE(0.9, 0.9f, 0.9f, 1.0f, 1.0f, 1.0),
	BLACK(0.0, 0.0f, 0.0f, 0.1f, 0.1f, 0.1);
	
	private double rMin;
	private double gMin;
	private double bMin;
	private double rMax;
	private double gMax;
	private double bMax;

	private CourseColor(double rMin, double gMin, double bMin, double rMax, double gMax, double bMax) {
		this.rMin = rMin;
		this.gMin = gMin;
		this.bMin = bMin;
		this.rMax = rMax;
		this.gMax = gMax;
		this.bMax = bMax;		
	}
	
	public boolean matches(float[] rgbValue) {
		return rMin <= rgbValue[0] && rgbValue[0] <= rMax
		    && gMin <= rgbValue[1] && rgbValue[1] <= gMax
		    && bMin <= rgbValue[2] && rgbValue[2] <= bMax;
	}
	
	
	public static CourseColor getMatchingColor(float[] rgbValue) {
		for(CourseColor color: CourseColor.values()) {
			if(color.matches(rgbValue)) {
				return color;
			}
		}
		return null;
		
	}
	
	
}
