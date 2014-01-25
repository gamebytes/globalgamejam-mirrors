package com.w131.globalgamejam.mirrors;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Square {
	public final int WIDTH = 20;
	public final int HEIGHT = 20;
	
	public boolean crossed = false;

	public Vector2 pos;
	public float speed = 100;
	public Vector2 multiplier = new Vector2(1, 1);

	public ShapeRenderer shapeRenderer = new ShapeRenderer();
	public Color color = Color.BLACK;

	public boolean justCrossedMirror = false;
	public boolean crossingMirror = false;
	public Vector2 crossingDir = new Vector2();

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

	public void tick(float delta) {
		justCrossedMirror = false;
		checkCrossMirror(controller.mirror);
		if (crossingMirror) {
			move(crossingDir, delta);
		}
	}

	public void handleInput(float delta) {
		if (crossingMirror) return;

		if (KeyHandler.up) {
			move(new Vector2(0, speed).scl(multiplier), delta);
		}
		if (KeyHandler.down) {
			move(new Vector2(0, -speed).scl(multiplier), delta);
		}
		if (KeyHandler.left) {
			move(new Vector2(-speed, 0).scl(multiplier), delta);
		}
		if (KeyHandler.right) {
			move(new Vector2(speed, 0).scl(multiplier), delta);
		}
	}
	
	/**
	 * Move the square (ONLY MOVE ONE AXIS AT A TIME)
	 * @param vec
	 * @param delta
	 */
	public void move(Vector2 vec, float delta) {
		pos.add(vec.cpy().scl(delta));
		
		checkCrossMirror(controller.mirror);
		
		if(crossingMirror) return;
		
		// The cell at each of the corners
		LinkedList<TiledMapTile> corners = new LinkedList<TiledMapTile>();
		corners.add(controller.screen.getCurrentLayer().getCell((int)Math.floor(pos.x / WIDTH), (int)Math.floor(pos.y / HEIGHT)).getTile());
		corners.add(controller.screen.getCurrentLayer().getCell((int)Math.floor((pos.x + WIDTH - 1) / WIDTH), (int)Math.floor(pos.y / HEIGHT)).getTile());
		corners.add(controller.screen.getCurrentLayer().getCell((int)Math.floor(pos.x / WIDTH), (int)Math.floor((pos.y + HEIGHT - 1) / HEIGHT)).getTile());
		corners.add(controller.screen.getCurrentLayer().getCell((int)Math.floor((pos.x + WIDTH - 1) / WIDTH), (int)Math.floor((pos.y + HEIGHT - 1) / HEIGHT)).getTile());
		
		//Collisions go here
		for(TiledMapTile corner : corners) {
			if(corner.getProperties().get("color").equals(color.toString())) {
				pos.sub(vec.cpy().scl(delta));
				break;
			}
		}
	}

	public void render(float delta) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(pos.x, pos.y, WIDTH, HEIGHT);
		shapeRenderer.end();
	}

	public void checkCrossMirror(Mirror mirror) {
		if (mirror.dir == Orientation.HORIZONTAL) {
			if (pos.y <= mirror.pos && pos.y + HEIGHT >= mirror.pos) {
				if (!crossingMirror) {
					justCrossedMirror = true;
					if (KeyHandler.up)
						crossingDir = new Vector2(speed, 0).scl(multiplier);
					else if (KeyHandler.down) crossingDir = new Vector2(-speed, 0).scl(multiplier);
				}
				crossingMirror = true;
			} else {
				crossingMirror = false;
			}
		} else {
			if (pos.x <= (int) mirror.pos && pos.x + WIDTH >= (int) mirror.pos) {
				if (!crossingMirror) {
					justCrossedMirror = true;
					if (KeyHandler.left)
						crossingDir = new Vector2(-speed, 0).scl(multiplier);
					else if (KeyHandler.right) crossingDir = new Vector2(speed, 0).scl(multiplier);
				}
				crossingMirror = true;
			} else {
				crossingMirror = false;
			}
		}
	}
}
