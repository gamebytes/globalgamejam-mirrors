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
	 * Returns the distance that the given rectangle is from the mirror
	 * @param pos
	 * @param w
	 * @param h
	 * @return
	 */
	public float distFrom(Square square) {
		if(dir == Orientation.VERTICAL) {
			return Math.min(Math.abs(pos - square.pos.x), Math.abs(pos - (square.pos.x + square.WIDTH)));
		}
		else {
			return Math.min(Math.abs(pos - square.pos.y), Math.abs(pos - square.pos.y + square.HEIGHT));
		}
	}
	
	/**
	 * Returns true if the rect is on the top or left of the mirror line
	 * @param point
	 * @return
	 */
	public boolean onTopLeft(Square square) {
		if(dir == Orientation.VERTICAL) {
			return square.pos.x + square.WIDTH / 2 < pos;
		}
		else {
			return square.pos.y + square.HEIGHT / 2 > pos;
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
