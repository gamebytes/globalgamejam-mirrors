package com.w131.globalgamejam.mirrors;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class Square {
	public final int WIDTH = 20;
	public final int HEIGHT = 20;

	public Vector2 pos = new Vector2();
	public Vector2 del = new Vector2();
	public float speed = 200;
	public Vector2 multiplier = new Vector2(1, 1);

	public ShapeRenderer shapeRenderer = new ShapeRenderer();
	public Color color = Color.BLACK;

	public boolean crossed = false;
	public boolean canCrossMirror = false;
	public boolean justCrossedMirror = false;
	public boolean crossingMirror = false;
	public Vector2 crossingDir = new Vector2();

	public boolean onExit = false;

	Controller controller;

	public Square(Controller cont, Color col) {
		controller = cont;
		color = col;
		pos = new Vector2();
	}

	public Square(Controller cont, Color col, Vector2 npos) {
		controller = cont;
		color = col;
		pos = npos;
	}

	public void tick(Square other, float delta) {
		justCrossedMirror = false;
		if (crossingMirror) {
			move(crossingDir, other, delta);
		}
		checkOnExit();
	}

	public void handleInput(Square other, float delta) {
		if (crossingMirror) return;

		if (KeyHandler.up) {
			move(new Vector2(0, speed).scl(multiplier), other, delta);
		}
		if (KeyHandler.down) {
			move(new Vector2(0, -speed).scl(multiplier), other, delta);
		}
		if (KeyHandler.left) {
			move(new Vector2(-speed, 0).scl(multiplier), other, delta);
		}
		if (KeyHandler.right) {
			move(new Vector2(speed, 0).scl(multiplier), other, delta);
		}
	}

	/**
	 * Move the square (ONLY MOVE ONE AXIS AT A TIME)
	 * 
	 * @param vec
	 * @param delta
	 */
	public void move(Vector2 vec, Square other, float delta) {
		Vector2 startPos = pos.cpy();
		
		pos.add(vec.cpy().scl(delta));

		del = pos.cpy().sub(startPos);
		if(crossingMirror) {
			float newDist = controller.mirror.distFrom(this);
			if(newDist >= WIDTH) {
				pos = startPos.cpy();
				float oldDist = controller.mirror.distFrom(this);
				pos.add(vec.cpy().scl(delta));
				if(oldDist < newDist) {
					crossingDir = new Vector2();
					if(other.crossingDir.len() == 0) {
						crossingMirror = false;
						other.crossingMirror = false;
					}
				}
			}
			return;
		}

		Vector2 tpos = pos.cpy();
		tpos.x = (int) tpos.x;
		tpos.y = (int) tpos.y;

		// The cell at each of the corners
		LinkedList<TiledMapTile> corners = new LinkedList<TiledMapTile>();
		corners.add(controller.screen.getCurrentLayer().getCell((int) Math.min(Gdx.graphics.getWidth() - 1, Math.max(0, Math.floor(tpos.x / WIDTH))), (int) Math.min(Gdx.graphics.getHeight() - 1, Math.max(0, Math.floor(tpos.y / HEIGHT)))).getTile());
		corners.add(controller.screen.getCurrentLayer().getCell((int) Math.min(Gdx.graphics.getWidth() - 1, Math.max(0, Math.floor((tpos.x + WIDTH - 1) / WIDTH))), (int) Math.min(Gdx.graphics.getHeight() - 1, Math.max(0, Math.floor(tpos.y / HEIGHT)))).getTile());
		corners.add(controller.screen.getCurrentLayer().getCell((int) Math.min(Gdx.graphics.getWidth() - 1, Math.max(0, Math.floor(tpos.x / WIDTH))), (int) Math.min(Gdx.graphics.getHeight() - 1, Math.max(0, Math.floor((tpos.y + HEIGHT - 1) / HEIGHT)))).getTile());
		corners.add(controller.screen.getCurrentLayer().getCell((int) Math.min(Gdx.graphics.getWidth() - 1, Math.max(0, Math.floor((tpos.x + WIDTH - 1) / WIDTH))), (int) Math.min(Gdx.graphics.getHeight() - 1, Math.max(0, Math.floor((tpos.y + HEIGHT - 1) / HEIGHT)))).getTile());

		// Collisions go here
		for (TiledMapTile corner : corners) {
			if (corner.getProperties().get("color").equals(color.toString()) || corner.getProperties().get("color").equals("7f7f7fff")) {
				pos.sub(vec.cpy().scl(delta));
				if (vec.x != 0) {
					if (vec.x < 0) {
						pos.x = (float) (Math.floor(pos.x / WIDTH) * WIDTH);
					} else {
						pos.x = (float) (Math.floor((pos.x + WIDTH - 1) / WIDTH) * WIDTH);
					}
				}
				if (vec.y != 0) {
					if (vec.y < 0) {
						pos.y = (float) (Math.floor(pos.y / HEIGHT) * HEIGHT);
					} else {
						pos.y = (float) (Math.floor((pos.y + HEIGHT - 1) / HEIGHT) * HEIGHT);
					}
				}
				break;
			}
		}
	}
	
	public void checkOnExit() {

		LinkedList<TiledMapTile> corners = new LinkedList<TiledMapTile>();
		corners.add(controller.screen.getCurrentLayer().getCell((int) Math.floor(pos.x / WIDTH), (int) Math.floor(pos.y / HEIGHT)).getTile());
		corners.add(controller.screen.getCurrentLayer().getCell((int) Math.floor((pos.x + WIDTH - 1) / WIDTH), (int) Math.floor(pos.y / HEIGHT)).getTile());
		corners.add(controller.screen.getCurrentLayer().getCell((int) Math.floor(pos.x / WIDTH), (int) Math.floor((pos.y + HEIGHT - 1) / HEIGHT)).getTile());
		corners.add(controller.screen.getCurrentLayer().getCell((int) Math.floor((pos.x + WIDTH - 1) / WIDTH), (int) Math.floor((pos.y + HEIGHT - 1) / HEIGHT)).getTile());

		onExit = false;
		for (TiledMapTile corner : corners) {
			// Check to see if we're on an exit
			if (corner.getProperties().get("exit").equals("true")) {
				onExit = true;
			}
		}
	}

	public void render(float delta) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(pos.x, pos.y, WIDTH, HEIGHT);
		shapeRenderer.end();
	}

	public void dispose() {
		shapeRenderer.dispose();
	}
}
