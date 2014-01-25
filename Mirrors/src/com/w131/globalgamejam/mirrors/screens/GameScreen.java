package com.w131.globalgamejam.mirrors.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.w131.globalgamejam.mirrors.Controller;
import com.w131.globalgamejam.mirrors.KeyHandler;

public class GameScreen implements Screen {

	private int lvlNum;

	private TiledMap map;
	private TiledMapTileLayer layer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	Controller player;

	public GameScreen(int lvlNum) {
		this.lvlNum = lvlNum;

		// My Init
		Gdx.input.setInputProcessor(new KeyHandler());
		player = new Controller(this);
	}

	public void tick(float delta) {
		player.tick(delta);
	}

	@Override
	public void render(float delta) {
		// I like having a tick function...
		tick(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.setView(camera);

		// BEGIN MY DRAWING
		renderer.getSpriteBatch().begin();
		renderer.renderTileLayer(layer);
		renderer.getSpriteBatch().end();
		// END MY DRAWING

		player.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/level" + String.format("%02d", lvlNum) + ".tmx");
		layer = (TiledMapTileLayer) map.getLayers().get("a");

		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();

		camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}

	public void switchLayer(String layerName) {
		if (layerName.contains("a") || layerName.contains("b") || layerName.contains("c")) layer = (TiledMapTileLayer) map.getLayers().get(layerName);
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
		renderer.dispose();
		map.dispose();
	}

	public TiledMapTileLayer getCurrentLayer() {
		return layer;
	}

	public String getLayerName() {
		return layer.getName();
	}

}
