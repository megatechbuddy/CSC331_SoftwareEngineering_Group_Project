package sprites;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import screens.PlayScreen;

/**
 * @author Sean Benson, Kevin Singleton - 
 * Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.
 */
public class Barrel extends Sprite {
	public enum State {
		STILL, ROLLING, EXPLODING
	}

	public World world;
	public Body b2body;
	public State currentState;
	public State previousState;
	private Animation<TextureRegion> barrellRoll;
	private TextureRegion playerStand;
	private Animation<TextureRegion> barrellExplode;
	private boolean runningRight;
	private float stateTimer;
	private boolean startExplosion;
	public static int speed = 20;
	private boolean barrelMotionState;
	private boolean barrelDead;

	/**
	 * Constructor
	 */
	public Barrel(PlayScreen screen) {
		super(screen.getAtlas().findRegion("Barrel_A"));
		this.world = screen.getWorld();

		// change picture animation
		currentState = State.STILL;
		previousState = State.STILL;
		stateTimer = 0;
		runningRight = true;
		barrelMotionState = true;
		barrelDead = false;

		// setters
		startExplosion = false;

		// Rolling frames
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(screen.getAtlas().findRegion("Barrel_A"));
		frames.add(screen.getAtlas().findRegion("Barrel_B"));
		frames.add(screen.getAtlas().findRegion("Barrel_C"));
		frames.add(screen.getAtlas().findRegion("Barrel_D"));
		frames.add(screen.getAtlas().findRegion("Barrel_E"));
		frames.add(screen.getAtlas().findRegion("Barrel_F"));
		frames.add(screen.getAtlas().findRegion("Barrel_G"));
		frames.add(screen.getAtlas().findRegion("Barrel_H"));

		barrellRoll = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();

		// StartExplosion Frames
		frames.add(screen.getAtlas().findRegion("Explosion_a"));
		frames.add(screen.getAtlas().findRegion("Explosion_b"));
		frames.add(screen.getAtlas().findRegion("Explosion_c"));

		barrellExplode = new Animation<TextureRegion>(0.4f, frames);
		frames.clear();

		playerStand = new TextureRegion(screen.getAtlas().findRegion("Barrel_A"));

		// show picture
		defineSprite();
		setBounds(0, 0, 25 / AngryDonkeyKongLibGDX.PPM, 25 / AngryDonkeyKongLibGDX.PPM);
		setRegion(playerStand);

	}

	/**
	 * Updates an individual barrel.
	 */
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}

	
	/**
	 * Returns the TextureRegion of an individual frame.
	 */
	public TextureRegion getFrame(float dt) {
		currentState = getState();

		TextureRegion region;
		switch (currentState) {
		case STILL:
			// region = playerJump.getKeyFrame(stateTimer);
			// break;
		case ROLLING:
			// System.out.println("running");
			region = barrellRoll.getKeyFrame(stateTimer, true);
			break;

		case EXPLODING:
			// debugging
			// System.out.println("gun");
			region = barrellExplode.getKeyFrame(stateTimer);
			if (barrellExplode.isAnimationFinished(stateTimer)) {
				startExplosion = false;
				// setBarrelDead();
				// world.destroyBody(b2body);
			}
			break;
		default:
			// System.out.println("stand");
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

	
	/**
	 * @return the State of the Barrel. Possible states are: EXPLODING, ROLLING, and STILL.
	 */
	public State getState() {
		if (startExplosion) {
			return State.EXPLODING;
		} else {
			return State.ROLLING;
		}
	}

	
	/**
	 * This is a setter class that starts the explosion of the barrel.
	 */
	public void startExplosion() {
		startExplosion = true;
	}

	/**
	 * This defines the image, dimensions, and contact points of the sprite.
	 */
	public void defineSprite() {
		FixtureDef fDef;
		BodyDef bdef;
		bdef = new BodyDef();
		Vector2 start_position = new Vector2(20, 44);
		bdef.position.set(start_position);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		fDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(10 / AngryDonkeyKongLibGDX.PPM);
		fDef.filter.categoryBits = AngryDonkeyKongLibGDX.BARREL_BIT;
		fDef.filter.maskBits = AngryDonkeyKongLibGDX.BRICK_BIT | AngryDonkeyKongLibGDX.BARREL_BIT
				| AngryDonkeyKongLibGDX.KONG_BIT | AngryDonkeyKongLibGDX.PRINCESS_BIT
				| AngryDonkeyKongLibGDX.ATEAMMAN_BIT | AngryDonkeyKongLibGDX.PLAYER_BIT
				| AngryDonkeyKongLibGDX.LADDER_GROUND | AngryDonkeyKongLibGDX.BULLET_BIT;

		fDef.shape = shape;

		b2body.createFixture(fDef).setUserData(this);
	}

	
	/**
	 * @param input True/False to determine whether the barrel is in motion.
	 */
	public void setBarrelMotionState(boolean input) {
		barrelMotionState = input;
	}

	/**
	 * @return Returns boolean that represents the state of the barrel, whether it is in motion or not. In motion(true). Not moving(false).
	 */
	public boolean getBarrelMotionState() {
		return barrelMotionState;
	}

	/**
	 * This method is called to terminate the barrel.
	 */
	public void setBarrelDead() {
		barrelDead = true;
	}

	/**
	 * @return Returns whether the barrel is dead or alive.
	 */
	public boolean getBarrelDead() {
		return barrelDead;
	}
}
