//Author: Sean Benson 
package com.angrydonkeykong.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.PlayScreen;

public class AngryDonkeyKongLibGDX extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 608;
	public static final int V_HEIGHT = 800;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
