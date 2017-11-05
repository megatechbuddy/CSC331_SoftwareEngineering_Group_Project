package com.angrydonkeykong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity {

	private static int SPEED = 10;
	private static final int JUMP_VELOCITY = 5;
	
	//TODO: SELECT A NEW IMAGE
	Texture image;
	
	public Player (float x, float y, GameMap map, boolean grounded) {
		super(x, y, EntityType.PLAYER, map, grounded);
	}
	
	@Override
	public void update(float deltaTime, float gravity) {
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded)
			this.velocityY += JUMP_VELOCITY * getWeight();
		else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded && this.velocityY >0)
			this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
		super.update(deltaTime, gravity); // Apply Gravity
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			moveX()
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		
	}
	
}
