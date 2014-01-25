package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.math.Vector2;

public class Mirror {
	// Position on either the x or y axis (depends on dir)
	public float pos = 0;
	public Orientation dir = Orientation.VERTICAL;
	
	public Mirror() {
		
	}
	
	public Mirror(float p, Orientation d) {
		pos = p;
		dir = d;
	}
	
	/**
	 * Returns true if the point is on the top or left of the mirror line
	 * @param point
	 * @return
	 */
	public boolean onTopLeft(Vector2 point) {
		if(dir == Orientation.VERTICAL) {
			return point.x < pos;
		}
		else {
			return point.y < pos;
		}
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
