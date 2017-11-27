//Author: Minh Hua
package sprites;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import screens.PlayScreen;

/**
 * @author Minh Hua - Followed
 *         https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 *         tutorial and modified things tremendously for our game.
 */
public class ATeamMan extends Sprite {
	public enum State {
		STILL, ROLLING, EXPLODING
	};

	// Defines variables
	public World world;
	public Body b2body;
	public State currentState;
	public State previousState;
	private TextureRegion playerStand;
	private boolean runningRight;
	private float stateTimer;
	public static int speed = 20;

	/**
	 * @param screen
	 *            This function renders the ATeamMan onto the playscreen.
	 */
	public ATeamMan(PlayScreen screen) {
		super(screen.getAtlas().findRegion("ateamman sprite"));
		this.world = screen.getWorld();

		// change picture animation
		currentState = State.STILL;
		previousState = State.STILL;
		stateTimer = 0;
		runningRight = true;

		playerStand = new TextureRegion(screen.getAtlas().findRegion("ateamman sprite"));

		// show picture
		defineSprite();
		setBounds(0, 0, 16 / AngryDonkeyKongLibGDX.PPM, 16 / AngryDonkeyKongLibGDX.PPM);
		setRegion(playerStand);

	}

	/**
	 * 
	 * @param dt This function positions the ATeamMan on the playscreen.
	 */
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}
	/**
	 * 
	 * @param dt This function renders the texture of the ATeamMan, giving it a sprite.
	 * @return
	 */
	public TextureRegion getFrame(float dt) {
		currentState = getState();

		TextureRegion region = null;
		switch (currentState) {
		case STILL:
			// region = playerJump.getKeyFrame(stateTimer);
			// break;

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
	 * This function shapes the size of the ATeamMan.
	 */
	public void defineSprite() {
		BodyDef bdef = new BodyDef();
		Vector2 start_position = new Vector2(13, 7);
		bdef.position.set(start_position);
		b2body = world.createBody(bdef);

		FixtureDef fDef = new FixtureDef();
		PolygonShape shape2 = new PolygonShape();

		shape2.setAsBox(10 / AngryDonkeyKongLibGDX.PPM, 10 / AngryDonkeyKongLibGDX.PPM);

		fDef.shape = shape2;
		fDef.density = 1f;
		fDef.filter.categoryBits = AngryDonkeyKongLibGDX.ATEAMMAN_BIT;
		fDef.filter.maskBits = AngryDonkeyKongLibGDX.BRICK_BIT | AngryDonkeyKongLibGDX.BARREL_BIT
				| AngryDonkeyKongLibGDX.PLAYER_BIT | AngryDonkeyKongLibGDX.KONG_BIT | AngryDonkeyKongLibGDX.PRINCESS_BIT
				| AngryDonkeyKongLibGDX.ATEAMMAN_BIT;

		b2body.createFixture(fDef).setUserData(this);
		shape2.dispose();
	}
	
	/**
	 * This function updates the current status of the ATeamMan.
	 * @return
	 */
	public State getState() {
		return State.EXPLODING;
	}
}

