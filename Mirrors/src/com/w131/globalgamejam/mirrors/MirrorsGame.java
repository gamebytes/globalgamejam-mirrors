package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.Game;
import com.w131.globalgamejam.mirrors.screens.GameScreen;

public class MirrorsGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(7));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
