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
import com.badlogic.gdx.utils.Array;

import screens.PlayScreen;
import sprites.Player.State;

public class Barrel extends Sprite{
	public enum State {
		STILL, ROLLING, EXPLODING
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

	private boolean startExplosion;
	public static int speed = 20;

	public Barrel(World world, PlayScreen screen) {
		super(screen.getAtlas().findRegion("Barrel_A"));
		this.world = world;

		// change picture animation
		currentState = State.STILL;
		previousState = State.STILL;
		stateTimer = 0;
		runningRight = true;

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

		barrellRoll = new Animation(0.1f, frames);
		frames.clear();

		// StartExplosion Frames
		frames.add(screen.getAtlas().findRegion("Explosion_a"));
		frames.add(screen.getAtlas().findRegion("Explosion_b"));

		barrellExplode = new Animation(0.4f, frames);
		frames.clear();

		playerStand = new TextureRegion(screen.getAtlas().findRegion("Barrel_A"));

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
			case STILL:
				// region = playerJump.getKeyFrame(stateTimer);
				// break;
			case ROLLING:
				//System.out.println("running");
				region = barrellRoll.getKeyFrame(stateTimer, true);			
				break;
				
			case EXPLODING:
				// debugging
				//System.out.println("gun");
				region = barrellExplode.getKeyFrame(stateTimer);
				if(barrellExplode.isAnimationFinished(stateTimer)) {
					startExplosion = false;
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
		if (startExplosion) {
			return State.EXPLODING;
		} else if (true) {
			return State.ROLLING;
		} else {
			return State.STILL;
		}
	}

	public void startExplosion() {
		startExplosion = true;
	}
	
	public void defineSprite() {
		BodyDef bdef = new BodyDef();
		Vector2 start_position = new Vector2(20, 13);
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

}
