package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.w131.globalgamejam.mirrors.screens.CreditsScreen;
import com.w131.globalgamejam.mirrors.screens.GameScreen;
import com.w131.globalgamejam.mirrors.screens.SplashScreen;

public class MirrorsGame extends Game {

	public static Screen splashScreen;
	public static Screen gameScreen;
	public static Screen creditsScreen;

	@Override
	public void create() {
		splashScreen = new SplashScreen(this);
		gameScreen = new GameScreen(this);
		creditsScreen = new CreditsScreen(this);
		setScreen(splashScreen);
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
