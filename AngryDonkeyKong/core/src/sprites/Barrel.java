package sprites;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import sprites.Player.State;


public class Barrel extends Sprite implements MovingSpriteInterface{
//	public enum State {
//		STILL, ROLLING, EXPLODING
//	};
	public World world;
	public Body b2body;
	public State currentState;
	public State previousState;
	private Animation<TextureRegion> playerRun;
	private TextureRegion playerStand;
	private Animation<TextureRegion> playerJump;
	private boolean runningRight;
	private float stateTimer;
	private TextureRegion donkeyStand;
	
	public Barrel(World world, PlayScreen screen) {
		BodyDef bdef = new BodyDef();
		Vector2 start_position = new Vector2(350/AngryDonkeyKongLibGDX.PPM,350/AngryDonkeyKongLibGDX.PPM);
		bdef.position.set(start_position);
		b2body = world.createBody(bdef);
		
		FixtureDef fDef = new FixtureDef();
		PolygonShape shape2 = new PolygonShape();
		
		shape2.setAsBox(10 / AngryDonkeyKongLibGDX.PPM, 10 / AngryDonkeyKongLibGDX.PPM);
		
		fDef.shape = shape2;
		fDef.density = 1f;
		
		b2body.createFixture(fDef);
		shape2.dispose();
	}
	
	public void update(float dt)
	{
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}

	@Override
	public TextureRegion getFrame(float dt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void defineSprite() {
		// TODO Auto-generated method stub
		
	}

}
