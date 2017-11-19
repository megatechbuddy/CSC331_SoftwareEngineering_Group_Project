//Author: Sean Benson , Minh Hua
//Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.

package com.angrydonkeykong.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.PlayScreen;

public class AngryDonkeyKongLibGDX extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 608;
	public static final int V_HEIGHT = 500;
	public static final float PPM = 10;
	
	//Box2D Collision Bits
	public static final short BARREL_BIT = 1;
	public static final short BRICK_BIT = 2;
	public static final short PLAYER_BIT = 4;
	public static final short KONG_BIT = 8;
	public static final short PRINCESS_BIT = 16;
	public static final short ATEAMMAN_BIT = 32;
	public static final short BULLET_BIT = 64;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public Texture batch() {
		// TODO Auto-generated method stub
		return batch();
	}
	

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}
