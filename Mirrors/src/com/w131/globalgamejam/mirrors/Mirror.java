package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.math.Vector2;

public class Mirror {
	// Position on either the x or y axis (depends on dir)
	float pos;
	Orientation dir;
	
	public Mirror() {
		
	}
	
	public Mirror(float p, Orientation d) {
		pos = p;
		dir = d;
	}
	
	/**
	 * Get the direction modifier for the given point (should point towards the mirror)
	 */
	public Vector2 getMult(Vector2 point) {
		Vector2 mult = new Vector2(1, 1);
		
		if(dir == Orientation.VERTICAL) {
			if(point.x < pos) {
				mult.x *= -1;
			}
			else {
				// We're good
			}
		}
		else {
			if(point.y < pos) {
				mult.y *= -1;
			}
			else {
				// We're good
			}
		}
		
		return mult;
	}
}
