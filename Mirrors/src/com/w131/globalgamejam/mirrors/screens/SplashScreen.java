package com.w131.globalgamejam.mirrors.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.w131.globalgamejam.mirrors.MirrorsGame;

public class SplashScreen implements Screen {
	
	// In milliseconds
	private final static int SPLASH_LENGTH = 2000;
	
	Game game;
	SpriteBatch batch;
	Texture texture;
	long startTime;
	
	public SplashScreen(Game g) {
		game = g;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
        
        if(Gdx.input.justTouched() || TimeUtils.millis() > startTime + SPLASH_LENGTH
        		|| Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.ENTER)){
        	game.setScreen(MirrorsGame.gameScreen);
        }
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("img/splashlogo.png"));
		startTime = TimeUtils.millis();
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
		
	}

}
