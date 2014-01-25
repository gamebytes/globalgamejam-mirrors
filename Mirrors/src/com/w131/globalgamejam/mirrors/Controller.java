package com.w131.globalgamejam.mirrors;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.w131.globalgamejam.mirrors.screens.GameScreen;

public class Controller {
	LinkedList<Square> squares;
	Mirror mirror;

	private SpriteBatch batch;

	GameScreen screen;

	public Controller(GameScreen gs) {
		screen = gs;

		batch = new SpriteBatch();

		mirror = new Mirror(Gdx.graphics.getWidth() / 2, Orientation.VERTICAL);

		// Add squares
		squares = new LinkedList<Square>();
		Square square = new Square(this, Color.BLACK, screen.spawns.get(Color.BLACK));
		square.multiplier = mirror.getMult(square.pos);
		squares.add(square);
		square = new Square(this, Color.WHITE, screen.spawns.get(Color.WHITE));
		square.multiplier = mirror.getMult(square.pos);
		squares.add(square);
	}

	public void tick(float delta) {
		for (Square square : squares) {
			square.tick(delta);
			square.handleInput(delta);
			if (square.justCrossedMirror) {
				onCrossMirror(delta, square);
			}
		}
		switchLayer();
		if(squares.get(0).onExit && squares.get(1).onExit) {
			screen.nextLevel();
			squares.get(0).pos = screen.spawns.get(squares.get(0).color);
			squares.get(1).pos = screen.spawns.get(squares.get(1).color);
		}
	}

	public void render(float delta) {
		batch.begin();
		for (Square square : squares) {
			square.render(delta);
		}
		batch.end();
	}

	public void onCrossMirror(float delta, Square square) {
		if (square.crossed == false) {
			square.crossed = true;
		} else {
			square.crossed = false;
		}
	}

	public void switchLayer() {
		if (squares.get(0).crossed == true && squares.get(1).crossed == true) {
			if (!screen.getLayerName().contains("b")) screen.switchLayer("b");
		} else if (squares.get(0).crossed == false && squares.get(1).crossed == false) {
			if (!screen.getLayerName().contains("a")) screen.switchLayer("a");
		} else {
			if (!screen.getLayerName().contains("c")) screen.switchLayer("c");
		}
	}
}
