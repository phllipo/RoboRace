package de.otto.roborace.util;

public enum ColorId {
	RED(0), GREEN(1), BLUE(2), YELLOW(3), MAGENTA(4), ORANGE(5), WHITE(6), BLACK(7), PINK(8), GRAY(9), LIGHT_GRAY(
			10), DARK_GRAY(11), CYAN(12), BROWN(13), NONE(-1);

	private int id;

	private ColorId(int id) {
		this.id = id;
	}

	public static ColorId getInstance(int id) {
		for (ColorId colorId : ColorId.values()) {
			if (colorId.id == id) {
				return colorId;
			}
		}
		return NONE;
	}
}