package com.w131.globalgamejam.mirrors;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
		Square square = new Square(this, Color.BLACK, new Vector2(Gdx.graphics.getWidth() - 20, 0));
		square.multiplier = mirror.getMult(square.pos);
		squares.add(square);
		square = new Square(this, Color.WHITE, new Vector2(0, 0));
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
	}

	public void render(float delta) {
		batch.begin();
		for (Square square : squares) {
			square.render(delta);
		}
		batch.end();
	}

	public void onCrossMirror(float delta, Square square) {
		// Change map layer, do fancy transition ??
		screen.switchLayer("b");
	}
}
