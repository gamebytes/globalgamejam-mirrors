package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Mirrors";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 600;
		cfg.resizable = false;
		cfg.addIcon("img/icons/icon128.png", FileType.Internal);
		cfg.addIcon("img/icons/icon64.png", FileType.Internal);
		cfg.addIcon("img/icons/icon32.png", FileType.Internal);
		cfg.addIcon("img/icons/icon16.png", FileType.Internal);

		new LwjglApplication(new MirrorsGame(), cfg);
	}
}
