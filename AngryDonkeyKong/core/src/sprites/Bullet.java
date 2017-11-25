//Author: Sean Benson 
//Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.

package sprites;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import screens.PlayScreen;
import sprites.Player.State;

public class Bullet extends Sprite{
	public enum State {
		STILL, MOVING, HIT
	};

	public World world;
	public Body b2body;
	public State currentState;
	public State previousState;
	private Animation<TextureRegion> barrellRoll;
	private TextureRegion playerStand;
	private Animation<TextureRegion> barrellExplode;
	private boolean runningRight;
	private float stateTimer;
	private TextureRegion donkeyStand;

	private boolean bulletFired;
	private boolean bulletHit;
	public static int speed = 20;

	private float x;
	private float y;
	
	public Bullet(PlayScreen screen, float inputXLocation, float inputYLocation) {
		super(screen.getAtlas().findRegion("Bullet"));
		this.world = screen.getWorld();

		// change picture animation
		currentState = State.STILL;
		previousState = State.STILL;
		stateTimer = 0;
		runningRight = true;
		this.x = inputXLocation;
		this.y = inputYLocation;

		// setters
		bulletFired = false;
		bulletHit = false;

		// Rolling frames
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(screen.getAtlas().findRegion("Bullet"));

		barrellRoll = new Animation(0.1f, frames);
		frames.clear();

		// StartExplosion Frames
		frames.add(screen.getAtlas().findRegion("Bullet"));

		barrellExplode = new Animation(0.4f, frames);
		frames.clear();

		playerStand = new TextureRegion(screen.getAtlas().findRegion("Bullet"));

		// show picture
		defineSprite();
		setBounds(0, 0, 4 / AngryDonkeyKongLibGDX.PPM, 1 / AngryDonkeyKongLibGDX.PPM);
		setRegion(playerStand);
		
		MassData mass = new MassData();
	}

	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}

	public TextureRegion getFrame(float dt) {
		currentState = getState();

		TextureRegion region;
		switch (currentState) {
			case STILL:
				// region = playerJump.getKeyFrame(stateTimer);
				// break;
			case MOVING:
				//System.out.println("running");
				region = barrellRoll.getKeyFrame(stateTimer, true);			
				break;
				
			case HIT:
				// debugging
				//System.out.println("gun");
				region = barrellExplode.getKeyFrame(stateTimer);
				if(barrellExplode.isAnimationFinished(stateTimer)) {
					bulletFired = false;
				}
				break;
			default:
				//System.out.println("stand");
				currentState = State.STILL;
				region = playerStand;
				break;
		}
		
		if (!runningRight && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		} else if (runningRight && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}

	public State getState() {
		if (bulletFired) {
			return State.HIT;
		} else if (true) {
			return State.MOVING;
		} else {
			return State.STILL;
		}
	}
	
	public void setStateFireGun() {
		bulletFired = true;
	}
	
	public void setStateHit() {
		bulletHit = true;
	}
	
	public void defineSprite() {
		BodyDef bdef = new BodyDef();
		Vector2 start_position = new Vector2(x, y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(start_position);
		b2body = world.createBody(bdef);

		FixtureDef fDef = new FixtureDef();
		PolygonShape shape2 = new PolygonShape();

		shape2.setAsBox(4 / AngryDonkeyKongLibGDX.PPM/2, 1 / AngryDonkeyKongLibGDX.PPM/2);

		fDef.shape = shape2;
		fDef.density = 1f;
		fDef.filter.categoryBits = AngryDonkeyKongLibGDX.BULLET_BIT;
		fDef.filter.maskBits = AngryDonkeyKongLibGDX.BRICK_BIT | 
				AngryDonkeyKongLibGDX.BARREL_BIT | 
				AngryDonkeyKongLibGDX.PLAYER_BIT|
				AngryDonkeyKongLibGDX.KONG_BIT|
				AngryDonkeyKongLibGDX.PRINCESS_BIT|
				AngryDonkeyKongLibGDX.ATEAMMAN_BIT;


		b2body.createFixture(fDef).setUserData(this);
		shape2.dispose();
	}
}
