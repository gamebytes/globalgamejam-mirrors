package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

public class KeyHandler extends InputAdapter {
	public static boolean up = false;
	public static boolean down = false;
	public static boolean left = false;
	public static boolean right = false;
	public static boolean exit = false;
	
	public static boolean reset = false;
	public static boolean lastReset = false;
	public static boolean pauseMusic = false;
	public static boolean lastPauseMusic = false;

	@Override
	public boolean keyDown(int key) {
		switch (key) {
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
		case Keys.R:
		case Keys.SPACE:
		case Keys.ENTER:
			reset = true;
			break;
		case Keys.ESCAPE:
			exit = true;
			break;
		case Keys.M:
			pauseMusic = true;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int key) {
		switch (key) {
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
		case Keys.R:
		case Keys.SPACE:
		case Keys.ENTER:
			reset = false;
			break;
		case Keys.ESCAPE:
			exit = false;
			break;
		case Keys.M:
			pauseMusic = false;
			break;
		}
		return true;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if(Gdx.app.getType() == ApplicationType.Android) {
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
		if(Gdx.app.getType() == ApplicationType.Android) {
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
