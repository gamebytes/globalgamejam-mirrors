package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundController {

	private final static Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/MirrorsMaster_01.ogg"));
	private final static Sound spawn = Gdx.audio.newSound(Gdx.files.internal("sounds/Spawn.ogg"));

	public static void playBGMusic() {
		bgMusic.setVolume(0.25f);

		bgMusic.play();

		if (bgMusic.isLooping() == false) {
			bgMusic.setLooping(true);
		}
	}

	public static void pauseBGMusic() {
		bgMusic.pause();
	}

	public static void stopBGMusic() {
		bgMusic.stop();
	}

	public static void playSpawn() {
		spawn.play(0.25f);
	}

	public static void dispose() {
		bgMusic.dispose();
		spawn.dispose();
	}
}
