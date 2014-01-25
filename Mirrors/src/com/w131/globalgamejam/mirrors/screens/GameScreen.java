package com.w131.globalgamejam.mirrors.screens;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.w131.globalgamejam.mirrors.Controller;
import com.w131.globalgamejam.mirrors.KeyHandler;

public class GameScreen implements Screen {

	private int lvlNum;

	private TiledMap map;
	private TiledMapTileLayer layer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	Controller player;

	public HashMap<Color, Vector2> spawns;

	public GameScreen(int lvlNum) {
		this.lvlNum = lvlNum;

		// My Init
		Gdx.input.setInputProcessor(new KeyHandler());

		spawns = new HashMap<Color, Vector2>();
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

		// Put the players in their spawn points
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x, y);
				if (cell.getTile().getProperties().get("spawn").equals("true")) {
					System.out.println("Spawn cell found at " + x + "," + y);
					if (cell.getTile().getProperties().get("color").equals("000000ff")) {
						System.out.println(x + "," + y + " white");
						System.out.println(cell.getTile().getProperties().toString());
						spawns.put(Color.WHITE, new Vector2(x * layer.getTileWidth(), y * layer.getTileHeight()));
					} else if (cell.getTile().getProperties().get("color").equals("ffffffff")) {
						System.out.println(x + "," + y + " black");
						System.out.println(cell.getTile().getProperties().toString());
						spawns.put(Color.BLACK, new Vector2(x * layer.getTileWidth(), y * layer.getTileHeight()));
					}
				}
				if (spawns.size() >= 2) {
					break;
				}
			}
			if (spawns.size() >= 2) {
				break;
			}

		}

		player = new Controller(this);
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
