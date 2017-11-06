package sprites;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import screens.PlayScreen;

public class Player extends Sprite{
	public enum State {FALLING, JUMPING, STANDING, RUNNING};
	public State currentState;
	public State previousState;
	private Animation<TextureRegion> playerRun;
	private TextureRegion playerStand;
	private Animation<TextureRegion> playerJump;
	private boolean runningRight;
	private float stateTimer;
	public World world;
	public Body b2body;
	public static int speed = 20;
	
	public Player(World world, PlayScreen screen) {
		super(screen.getAtlas().findRegion("a"));
		this.world = world;
		
		//change picture animation
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		//Running frames
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1; i < 4; i++) {
			frames.add(new TextureRegion(getTexture(), i * 65, 1, 65, 120));
		}
		playerRun = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();

		//Jumping Frames
		Array<TextureRegion> frames2 = new Array<TextureRegion>();
		for(int i = 1; i < 4; i++) {
			frames2.add(new TextureRegion(getTexture(), i * 65, 1, 65, 120));
		}
		playerJump = new Animation<TextureRegion>(0.1f, frames2);
		frames2.clear();
		
		playerStand = new TextureRegion(getTexture(),1,1,65,120);
		
		//show picture
		definePlayer();
		setBounds(0, 0, 16/AngryDonkeyKongLibGDX.PPM , 16/AngryDonkeyKongLibGDX.PPM);
		setRegion(playerStand);
	}

	public void update(float dt)
	{
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		setRegion(getFrame(dt));
	}
	
	private TextureRegion getFrame(float dt) {
		currentState = getState();
		
		TextureRegion region;
		switch(currentState) {
			case JUMPING:
				//region = playerJump.getKeyFrame(stateTimer);
				//break;
			case RUNNING:
				region = playerRun.getKeyFrame(stateTimer, true);
				break;
			case FALLING:
			case STANDING:
			default:
				region = playerStand;
				break;
		}
		if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		}else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
	    previousState = currentState;
	    return region;
	}

	private State getState() {
		if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
			return State.JUMPING;
		}else if(b2body.getLinearVelocity().y < 0) {
			return State.FALLING;
		}else if(b2body.getLinearVelocity().x != 0) {
			return State.RUNNING;
		}else{
			return State.STANDING;
		}
	}

	private void definePlayer() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(50/AngryDonkeyKongLibGDX.PPM,50/AngryDonkeyKongLibGDX.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fDef = new FixtureDef();
		
		Texture img = new Texture("AngryDonkeyKongSprites.png"); //string is irrelevant
        Sprite sprite = new Sprite(img);
		PolygonShape shape2 = new PolygonShape();
		
        shape2.setAsBox(8/AngryDonkeyKongLibGDX.PPM,8/AngryDonkeyKongLibGDX.PPM);

        fDef.shape = shape2;
        fDef.density = 1f;

        b2body.createFixture(fDef);
        // Shape is the only disposable of the lot, so get rid of it
        shape2.dispose();
		
	}
}
