package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class KeyHandler extends InputAdapter {
	public static boolean up = false;
	public static boolean down = false;
	public static boolean left = false;
	public static boolean right = false;
	
	@Override
	public boolean keyDown(int key) {
		switch(key) {
		case Keys.LEFT:
		case Keys.D:
			left = true;
			break;
		case Keys.DOWN:
		case Keys.S:
			down = true;
			break;
		case Keys.UP:
		case Keys.W:
			up = true;
			break;
		case Keys.RIGHT:
		case Keys.A:
			right = true;
			break;
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int key) {
		switch(key) {
		case Keys.LEFT:
		case Keys.D:
			left = false;
			break;
		case Keys.DOWN:
		case Keys.S:
			down = false;
			break;
		case Keys.UP:
		case Keys.W:
			up = false;
			break;
		case Keys.RIGHT:
		case Keys.A:
			right = false;
			break;
		}
		return true;
	}
}