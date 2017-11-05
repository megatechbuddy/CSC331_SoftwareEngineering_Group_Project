package sprites;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import screens.PlayScreen;


public class Barrel extends Sprite {
	public World world;
	public Body b2body;
	
	public Barrel(World world, PlayScreen screen) {
		BodyDef bdef = new BodyDef();
		Vector2 start_position = new Vector2(90/AngryDonkeyKongLibGDX.PPM,90/AngryDonkeyKongLibGDX.PPM);
		bdef.position.set(start_position);
	}
	
	public void update(float dt)
	{
		
	}

}
