//Author: Sean Benson 
//Followed https://www.youtube.com/watch?v=7idwNW5a8Qs&index=4&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial 
//and modified things for our game.

package screens;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import scenes.Hud;

public class PlayScreen implements Screen{
	private AngryDonkeyKongLibGDX game;
	
	//TODO: Switch from home screen texture to game format.
	//private Texture img;
	
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private	Hud hud;
	
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;
	
	public PlayScreen(AngryDonkeyKongLibGDX game) {
		this.game = game;
		
		//This uses the image that Chuyang made.
		
//		img = new Texture("TitlePicture.jpg");
		
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(AngryDonkeyKongLibGDX.V_WIDTH, AngryDonkeyKongLibGDX.V_HEIGHT, gamecam);
		//other options for displays
		//gamePort = new ScreenViewport(gamecam);
		//gamePort = new StretchViewport(902, 590, gamecam);
		hud = new Hud(game.batch);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("marioMapV3.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()/2, 0);
		
		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//create background
		for(MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight()/2);
			
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		//create ground
		for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight()/2);
			
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		//create platform
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight()/2);
			
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void handleInput (float  dt) {
		if(Gdx.input.isTouched())
			gamecam.position.x += 100 * dt;
		
	}

	public void update(float dt) {
		handleInput(dt);
		gamecam.update();
		renderer.setView(gamecam);
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		
		//clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		
//		game.batch.setProjectionMatrix(gamecam.combined);
//		game.batch.begin();
//		game.batch.draw(img, -451, -295);
//		game.batch.end();
	
		b2dr.render(world, gamecam.combined);
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
		
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
