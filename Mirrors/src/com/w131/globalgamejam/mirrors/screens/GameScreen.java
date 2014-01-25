package com.w131.globalgamejam.mirrors.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {

	private int lvlNum;

	private TiledMap map;
	private TiledMapTileLayer layer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	public GameScreen(int lvlNum) {
		this.lvlNum = lvlNum;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.setView(camera);
		renderer.getSpriteBatch().begin();
		renderer.renderTileLayer(layer);
		renderer.getSpriteBatch().end();
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
		
		System.out.println(layer.getCell(8, 0).getTile().getProperties().get("color").equals("black"));
		
		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();

		camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}

	public void switchLayer(String layerName) {
		if (layerName.contains("a") || layerName.contains("b") || layerName.contains("c")) layer = (TiledMapTileLayer) map.getLayers().get(Integer.parseInt(layerName));
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

}
