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

public class Player extends Sprite{
	public World world;
	public Body b2body;
	
	public Player(World world) {
		this.world = world;
		definePlayer();
		
	}

	public void update(float dt)
	{
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y -getHeight()/2);
	}
	
	private void definePlayer() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(50/AngryDonkeyKongLibGDX.PPM,50/AngryDonkeyKongLibGDX.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fDef = new FixtureDef();
//		CircleShape shape = new CircleShape();
//		shape.setRadius(10/AngryDonkeyKongLibGDX.PPM);
//		fDef.shape = shape;
//
//		b2body.createFixture(fDef);
		
		Texture img = new Texture("ateamman sprite.png");
        Sprite sprite = new Sprite(img);
		PolygonShape shape2 = new PolygonShape();
		
		//changing #64 to a different bigger number makes the main character smaller
        shape2.setAsBox(sprite.getWidth()/64, sprite.getHeight()/64);

        fDef.shape = shape2;
        fDef.density = 1f;

        b2body.createFixture(fDef);
        // Shape is the only disposable of the lot, so get rid of it
        shape2.dispose();
		
	}
}
