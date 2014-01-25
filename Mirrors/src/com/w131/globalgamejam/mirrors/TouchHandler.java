package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class TouchHandler extends InputAdapter {
	
	public static boolean up = false;
	public static boolean down = false;
	public static boolean left = false;
	public static boolean right = false;
	public static boolean reset = false;
	public static boolean lastReset = false;
	public static boolean exit = false;
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (Gdx.app.getType().equals(Gdx.app.getType().Android)) {
			if (x < Gdx.graphics.getWidth() / 2 && y < Gdx.graphics.getHeight() / 2) {
				up = true;
			} else if (x > Gdx.graphics.getWidth() / 2 && y < Gdx.graphics.getHeight() / 2) {
				down = true;
			}
			if (x < Gdx.graphics.getWidth() / 2 && y > Gdx.graphics.getHeight() / 2) {
				left = true;
			} else if (x > Gdx.graphics.getWidth() / 2 && y > Gdx.graphics.getHeight() / 2) {
				right = true;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (Gdx.app.getType().equals(Gdx.app.getType().Android)) {
			if (x < Gdx.graphics.getWidth() / 2 && y < Gdx.graphics.getHeight() / 2) {
				up = false;
			} else if (x > Gdx.graphics.getWidth() / 2 && y < Gdx.graphics.getHeight() / 2) {
				down = false;
			}
			if (x < Gdx.graphics.getWidth() / 2 && y > Gdx.graphics.getHeight() / 2) {
				left = false;
			} else if (x > Gdx.graphics.getWidth() / 2 && y > Gdx.graphics.getHeight() / 2) {
				right = false;
			}
		}
		return false;
	}
}
