package sprites;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import screens.PlayScreen;

public class Player extends Sprite{
	public World world;
	public Body b2body;
	private TextureRegion donkeyStand;
	
	public Player(World world, PlayScreen screen) {
		super(screen.getAtlas().findRegion("KongFront"));
		this.world = world;
		definePlayer();
		donkeyStand = new TextureRegion(getTexture(),1,1,52,44);
		setBounds(0, 0, 32/AngryDonkeyKongLibGDX.PPM , 32/AngryDonkeyKongLibGDX.PPM);
		setRegion(donkeyStand);
	}

	public void update(float dt)
	{
		setPosition(b2body.getPosition().x, b2body.getPosition().y);
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
		
        shape2.setAsBox(1,1);

        fDef.shape = shape2;
        fDef.density = 1f;

        b2body.createFixture(fDef);
        // Shape is the only disposable of the lot, so get rid of it
        shape2.dispose();
		
	}
}
