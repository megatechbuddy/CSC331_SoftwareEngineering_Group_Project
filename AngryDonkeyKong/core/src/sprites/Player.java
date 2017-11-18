//Author: Sean Benson 
//Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.

package sprites;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import screens.PlayScreen;

public class Player extends Sprite{
	public enum State {
		FALLING, JUMPING, STANDING, RUNNING, FIRING_GUN
	};

	private State currentState;
	private State previousState;
	private Animation<TextureRegion> playerRun;
	private TextureRegion playerStand;
	private Animation<TextureRegion> playerFireGun;
	private boolean runningRight;
	private float stateTimer;
	public World world;
	public Body b2body;
	private TextureRegion donkeyStand;
	private boolean fireGun;

	public static int speed = 10;

	public Player(PlayScreen screen) {
		super(screen.getAtlas().findRegion("Running_a"));
		this.world = screen.getWorld();

		// change picture animation
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;

		// setters
		fireGun = false;

		// Running frames
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(screen.getAtlas().findRegion("Running_a"));
		frames.add(screen.getAtlas().findRegion("Running_b"));
		frames.add(screen.getAtlas().findRegion("Running_c"));
		frames.add(screen.getAtlas().findRegion("Running_d"));
		frames.add(screen.getAtlas().findRegion("Running_e"));
		frames.add(screen.getAtlas().findRegion("Running_f"));
		frames.add(screen.getAtlas().findRegion("Running_g"));
		frames.add(screen.getAtlas().findRegion("Running_h"));
		frames.add(screen.getAtlas().findRegion("Running_i"));
		frames.add(screen.getAtlas().findRegion("Running_j"));
		frames.add(screen.getAtlas().findRegion("Running_k"));
		frames.add(screen.getAtlas().findRegion("Running_l"));
		frames.add(screen.getAtlas().findRegion("Running_m"));

		playerRun = new Animation(0.1f, frames);
		frames.clear();

		// FiringGun Frames
		frames.add(screen.getAtlas().findRegion("FireGun_a"));
		frames.add(screen.getAtlas().findRegion("FireGun_b"));
		frames.add(screen.getAtlas().findRegion("FireGun_c"));
		frames.add(screen.getAtlas().findRegion("FireGun_d"));
		frames.add(screen.getAtlas().findRegion("FireGun_e"));
		frames.add(screen.getAtlas().findRegion("FireGun_f"));
		frames.add(screen.getAtlas().findRegion("FireGun_g"));
		frames.add(screen.getAtlas().findRegion("FireGun_g"));
		frames.add(screen.getAtlas().findRegion("FireGun_g"));
		frames.add(screen.getAtlas().findRegion("Running_a"));

		playerFireGun = new Animation(0.1f, frames);
		frames.clear();

		playerStand = new TextureRegion(screen.getAtlas().findRegion("Running_a"));

		// show picture
		defineSprite();
		setBounds(0, 0, 16 / AngryDonkeyKongLibGDX.PPM, 16 / AngryDonkeyKongLibGDX.PPM);
		setRegion(playerStand);
	}

	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}

	public TextureRegion getFrame(float dt) {
		currentState = getState();

		TextureRegion region;
		switch (currentState) {
			case JUMPING:
				// region = playerJump.getKeyFrame(stateTimer);
				// break;
			case RUNNING:
				//System.out.println("running");
				region = playerRun.getKeyFrame(stateTimer, true);			
				break;
				
			case FIRING_GUN:
				// debugging
				//System.out.println("gun");
				region = playerFireGun.getKeyFrame(stateTimer);
				if(playerFireGun.isAnimationFinished(stateTimer)) {
					fireGun = false;
				}
				break;
//			case FALLING:
//				region = playerStand;	
//				break;
//			case STANDING:
//				region = playerStand;	
//				break;
			default:
				//System.out.println("stand");
				currentState = State.STANDING;
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

	public void faceRight() {
		this.runningRight = true;
	}

	public void faceLeft() {
		this.runningRight = false;
	}

	public State getState() {
		if (fireGun) {
			//System.out.println("gun");
			return State.FIRING_GUN;
//		} else if (b2body.getLinearVelocity().y > 0
//				|| (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
//			return State.JUMPING;
//		} else if (b2body.getLinearVelocity().y < 0) {
//			return State.FALLING;
		} else if (b2body.getLinearVelocity().x != 0) {
		//	System.out.println("running");
			return State.RUNNING;
		} else {
			return State.STANDING;
		}
	}

	public void setStateFireGun() {
		fireGun = true;

		// debugging
		// if(inputFiringState)
		// System.out.println("GunFired " + inputFiringState);
	}

	public void defineSprite() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(50 / AngryDonkeyKongLibGDX.PPM, 50 / AngryDonkeyKongLibGDX.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		FixtureDef fDef = new FixtureDef();

		PolygonShape shape2 = new PolygonShape();

		shape2.setAsBox(8 / AngryDonkeyKongLibGDX.PPM, 8 / AngryDonkeyKongLibGDX.PPM);
		
		fDef.shape = shape2;
		fDef.density = 1f;
		

		fDef.filter.categoryBits = AngryDonkeyKongLibGDX.PLAYER_BIT;
		fDef.filter.maskBits = AngryDonkeyKongLibGDX.BRICK_BIT | 
				AngryDonkeyKongLibGDX.BARREL_BIT | 
				AngryDonkeyKongLibGDX.PLAYER_BIT|
				AngryDonkeyKongLibGDX.KONG_BIT|
				AngryDonkeyKongLibGDX.PRINCESS_BIT;
		b2body.createFixture(fDef).setUserData(this);
	
//		EdgeShape head = new EdgeShape();
//		head.set(new Vector2(-2 / AngryDonkeyKongLibGDX.PPM, 9/AngryDonkeyKongLibGDX.PPM), new Vector2(2 / AngryDonkeyKongLibGDX.PPM, 9/AngryDonkeyKongLibGDX.PPM));
//		fDef.shape = head;
//		fDef.isSensor = true;
//		
//		b2body.createFixture(fDef).setUserData("head");
		
		// Shape is the only disposable of the lot, so get rid of it
		shape2.dispose();
	}
}
