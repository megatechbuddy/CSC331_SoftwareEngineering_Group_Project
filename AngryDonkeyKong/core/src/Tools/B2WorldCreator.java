package Tools;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import screens.PlayScreen;
import sprites.tileObjects.Brick;

public class B2WorldCreator {

	public B2WorldCreator(PlayScreen screen) {
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		// create body and fixture variables
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;

		// create background
		for (MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / AngryDonkeyKongLibGDX.PPM,
					(rect.getY() + rect.getHeight() / 2) / AngryDonkeyKongLibGDX.PPM);

			body = world.createBody(bdef);

			shape.setAsBox((rect.getWidth() / 2) / AngryDonkeyKongLibGDX.PPM,
					(rect.getHeight() / 2) / AngryDonkeyKongLibGDX.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}

//		// create ground
//		for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
//			Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//			bdef.type = BodyDef.BodyType.StaticBody;
//			bdef.position.set((rect.getX() + rect.getWidth() / 2) / AngryDonkeyKongLibGDX.PPM,
//					(rect.getY() + rect.getHeight() / 2) / AngryDonkeyKongLibGDX.PPM);
//
//			body = world.createBody(bdef);
//
//			shape.setAsBox((rect.getWidth() / 2) / AngryDonkeyKongLibGDX.PPM,
//					(rect.getHeight() / 2) / AngryDonkeyKongLibGDX.PPM);
//			fdef.shape = shape;
//			body.createFixture(fdef);
//		}

//		//create platform
//		for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
//			Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//			bdef.type = BodyDef.BodyType.StaticBody;
//			bdef.position.set((rect.getX() + rect.getWidth() / 2) / AngryDonkeyKongLibGDX.PPM,
//					(rect.getY() + rect.getHeight() / 2) / AngryDonkeyKongLibGDX.PPM);
//
//			body = world.createBody(bdef);
//
//			shape.setAsBox((rect.getWidth() / 2) / AngryDonkeyKongLibGDX.PPM,
//					(rect.getHeight() / 2) / AngryDonkeyKongLibGDX.PPM);
//			fdef.shape = shape;
////			fdef.filter.categoryBits = AngryDonkeyKongLibGDX.BRICK_BIT;
////			fdef.filter.maskBits = AngryDonkeyKongLibGDX.BRICK_BIT | AngryDonkeyKongLibGDX.BARREL_BIT;
////			
//			body.createFixture(fdef);
//		}
		
		// create brick bodies/fixtures
		for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			new Brick(screen, object);
		}
		//
		// // create all goombas
		// goombas = new Array<Goomba>();
		// for (MapObject object :
		// map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
		// Rectangle rect = ((RectangleMapObject) object).getRectangle();
		// goombas.add(new Goomba(screen, rect.getX() / MarioBros.PPM, rect.getY() /
		// MarioBros.PPM));
		// }
		// turtles = new Array<Turtle>();
		// for (MapObject object :
		// map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
		// Rectangle rect = ((RectangleMapObject) object).getRectangle();
		// turtles.add(new Turtle(screen, rect.getX() / MarioBros.PPM, rect.getY() /
		// MarioBros.PPM));
		// }
	}

}
