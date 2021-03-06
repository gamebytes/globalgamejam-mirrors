package com.w131.globalgamejam.mirrors.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.w131.globalgamejam.mirrors.SoundController;

public class CreditsScreen implements Screen {
	SpriteBatch batch;
	Texture texture;

	public CreditsScreen(Game g) {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 0, 0);
		batch.end();

		if (Gdx.input.isKeyPressed(Keys.ESCAPE) && Gdx.app.getType() != ApplicationType.WebGL) {
			SoundController.stopBGMusic();
			SoundController.dispose();
			Gdx.app.exit();
		}
		if (Gdx.input.isKeyPressed(Keys.M)) {
			SoundController.toggleBGMusic();
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("img/credits.png"));
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}
}
