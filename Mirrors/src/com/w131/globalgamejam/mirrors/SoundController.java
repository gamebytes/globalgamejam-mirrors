package com.w131.globalgamejam.mirrors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundController {

	private final static Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/MirrorsMaster_01.ogg"));

	public static void playBGMusic() {
		bgMusic.setVolume(0.5f);
		if (bgMusic.isLooping() == false) {
			bgMusic.setLooping(true);
		}

		bgMusic.play();
	}

	public static void pauseBGMusic() {
		bgMusic.pause();
	}

	public static void stopBGMusic() {
		bgMusic.stop();
	}

	public static void dispose() {
		bgMusic.dispose();
	}

}
