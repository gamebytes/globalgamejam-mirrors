package com.w131.globalgamejam.mirrors.screens;

import java.util.HashMap;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.w131.globalgamejam.mirrors.Controller;
import com.w131.globalgamejam.mirrors.KeyHandler;
import com.w131.globalgamejam.mirrors.MirrorsGame;
import com.w131.globalgamejam.mirrors.SoundController;

public class GameScreen implements Screen {

	private final static int START_LEVEL = 0;

	private int levelNum;

	private TiledMap map;
	private TiledMapTileLayer layer;
	private OrthogonalTiledMapRenderer renderer;
	public ShapeRenderer shapeRenderer = new ShapeRenderer();
	private OrthographicCamera camera;

	Controller controller;
	Game game;

	public HashMap<Color, Vector2> spawns;

	public GameScreen(Game g) {
		this(g, START_LEVEL);
	}

	public GameScreen(Game g, int lvlNum) {
		this.levelNum = lvlNum;
		game = g;

		// My Init
		Gdx.input.setInputProcessor(new KeyHandler());

		spawns = new HashMap<Color, Vector2>();
	}

	public void tick(float delta) {
		controller.tick(delta);
		if (KeyHandler.exit && Gdx.app.getType() != ApplicationType.WebGL) {
			SoundController.stopBGMusic();
			SoundController.dispose();
			Gdx.app.exit();
		}
		if (KeyHandler.reset && !KeyHandler.lastReset) resetLevel();
		if (KeyHandler.pauseMusic && !KeyHandler.lastPauseMusic) SoundController.toggleBGMusic();
		KeyHandler.lastReset = KeyHandler.reset;
		KeyHandler.lastPauseMusic = KeyHandler.pauseMusic;
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

		controller.render(delta);
		// END MY DRAWING

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void show() {
		loadLevel(levelNum);

		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();

		camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		Gdx.app.getType();
		if (Gdx.app.getType().equals(ApplicationType.Android)) {
			Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}

		// Put the players in their spawn points
		setSpawns();

		SoundController.playBGMusic();
	}

	private void setSpawns() {
		spawns.clear();
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x, y);
				if (cell.getTile().getProperties().get("spawn").equals("true")) {
					if (cell.getTile().getProperties().get("color").equals("000000ff")) {
						spawns.put(Color.WHITE, new Vector2(x * layer.getTileWidth(), y * layer.getTileHeight()));
					} else if (cell.getTile().getProperties().get("color").equals("ffffffff")) {
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
	}

	public void nextLevel() {
		levelNum++;
		SoundController.playSpawn();
		loadLevel(levelNum);
	}

	public void resetLevel() {
		loadLevel(levelNum);
	}

	private void loadLevel(int lvl) {
		String levelName = "maps/level";
		String number = ((Integer) lvl).toString();
		if (number.length() == 1) {
			levelName += "0";
		}
		if (levelNum > 99) {
			// They're done, show credits
			game.setScreen(MirrorsGame.creditsScreen);
		}
		try {
			map = new TmxMapLoader().load(levelName + number + ".tmx");
		} catch (Exception e) {
			// If we fail loading the map, we figure that they're done
			// Best/worst/most awful hack ever
			levelNum = 99;
			map = new TmxMapLoader().load("maps/level99.tmx");
		}
		layer = (TiledMapTileLayer) map.getLayers().get("a");
		setSpawns();

		controller = new Controller(this);
	}

	public void switchLayer(String layerName) {
		if (layerName.contains("a") || layerName.contains("b") || layerName.contains("c")) layer = (TiledMapTileLayer) map.getLayers().get(layerName);
	}

	@Override
	public void hide() {
		dispose();
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

	public TiledMapTileLayer getCurrentLayer() {
		return layer;
	}

	public String getLayerName() {
		return layer.getName();
	}

}
