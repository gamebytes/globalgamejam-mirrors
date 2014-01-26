package com.w131.globalgamejam.mirrors;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.w131.globalgamejam.mirrors.screens.GameScreen;

public class Controller {
	private static final float CROSS_THRESHOLD = 10;
	public LinkedList<Square> squares;
	public Mirror mirror;

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
		squares.get(0).tick(squares.get(1), delta);
		squares.get(0).handleInput(squares.get(1), delta);
		squares.get(1).tick(squares.get(0), delta);
		squares.get(1).handleInput(squares.get(0), delta);

		doCrossMirror(delta);

		if (squares.get(0).justCrossedMirror) {
			onCrossMirror(squares.get(0));
		}
		if (squares.get(1).justCrossedMirror) {
			onCrossMirror(squares.get(1));
		}

		switchLayer();
		if (squares.get(0).onExit && squares.get(1).onExit) {
			screen.nextLevel();
			squares.get(0).onExit = false;
			squares.get(1).onExit = false;
		}
	}

	public void render(float delta) {
		batch.begin();
		for (Square square : squares) {
			square.render(delta);
		}
		batch.end();
	}

	public void onCrossMirror(Square square) {
		square.crossed = !square.crossed;
	}

	public void doCrossMirror(float delta) {
		if (mirror.dir == Orientation.VERTICAL) {
			if ((KeyHandler.left || KeyHandler.right) && !(squares.get(0).crossingMirror || squares.get(1).crossingMirror)) {
				if (mirror.distFrom(squares.get(0)) < CROSS_THRESHOLD && mirror.distFrom(squares.get(1)) < CROSS_THRESHOLD) {
					// Initiate a cross for both squares if they both are in
					// range
					if (squares.get(0).del.x > 0 && mirror.onTopLeft(squares.get(0)) && squares.get(1).del.x < 0 && !mirror.onTopLeft(squares.get(1))) {
						squares.get(0).crossingMirror = true;
						squares.get(0).justCrossedMirror = true;
						squares.get(0).crossingDir = squares.get(0).del.cpy().scl(1 / delta);
						squares.get(1).crossingMirror = true;
						squares.get(1).justCrossedMirror = true;
						squares.get(1).crossingDir = squares.get(1).del.cpy().scl(1 / delta);
					} else if (squares.get(1).del.x > 0 && mirror.onTopLeft(squares.get(1)) && squares.get(0).del.x < 0 && !mirror.onTopLeft(squares.get(0))) {
						squares.get(0).crossingMirror = true;
						squares.get(0).justCrossedMirror = true;
						squares.get(0).crossingDir = squares.get(0).del.cpy().scl(1 / delta);
						squares.get(1).crossingMirror = true;
						squares.get(1).justCrossedMirror = true;
						squares.get(1).crossingDir = squares.get(1).del.cpy().scl(1 / delta);
					}
				}
			}
		} else {

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

	public void dispose() {
		for (Square square : squares) {
			square.dispose();
		}
		batch.dispose();
	}
}
