package de.otto.roborace.controller;

public enum CourseColor {
	GREY(0.5, 0.5, 0.5, 0.6, 0.6, 0.6),
	WHITE(0.9, 0.9f, 0.9f, 1.0f, 1.0f, 1.0),
	BLACK(0.0, 0.0f, 0.0f, 0.1f, 0.1f, 0.1),
	LIGHTYELLOW(0.1f, 0.11f,0.07f,0.2f,0.2f,0.15f),
	RED(0.1f, 0.01f,0.01f,0.15f,0.05f,0.05f),
	GREEN(0.02f,0.1f,0.05f,0.06f,0.15f,0.1f),
	TEST(0.05f,0.05f,0.05f,0.3f,0.3f,0.3f);

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
