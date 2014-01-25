package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.w131.globalgamejam.mirrors.screens.GameScreen;

public class MirrorsGame extends Game {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Controller player;
	
	@Override
	public void create() {
		// Default Init stuff
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		setScreen(new GameScreen(0));
		camera.setToOrtho(true);
		batch = new SpriteBatch();
		
		// My Init
		Gdx.input.setInputProcessor(new KeyHandler());
		player = new Controller();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
	
	public void tick(float delta) {
		player.tick(delta);
	}

	@Override
	public void render() {
		// I like having a tick function...
		tick(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		// BEGIN MY DRAWING
		player.render(batch);
		// END MY DRAWING
		
		batch.end();
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
