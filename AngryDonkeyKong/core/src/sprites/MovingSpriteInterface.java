package sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import sprites.Player.State;

public interface MovingSpriteInterface{

	public void update(float dt);

	public TextureRegion getFrame(float dt);
	
	public State getState();

	public void defineSprite();
}
