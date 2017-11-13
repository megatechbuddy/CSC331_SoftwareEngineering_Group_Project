//Author: Sean Benson 
//Followed https://www.youtube.com/watch?v=7idwNW5a8Qs&index=4&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial 
//and modified things for our game.

package screens;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.PluggableGroupStrategy;
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
import com.badlogic.gdx.utils.viewport.Viewport;

import scenes.Hud;
import sprites.Barrel;
import sprites.Player;

public class PlayScreen implements Screen {
	private AngryDonkeyKongLibGDX game;

	// TODO: Switch from home screen texture to game format.
	// private Texture img;

	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;

	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	// Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;

	// sprites
	private Player player;
	private TextureAtlas atlas;
	private Barrel barrel;

	// velocities of the player
	private float player_x_velocity = 0;
	private float player_y_velocity = 0;

	//previous inputs
	boolean previousSpaceState;
	
	public PlayScreen(AngryDonkeyKongLibGDX game) {
		this.game = game;

		// sprites
		atlas = new TextureAtlas("AllSpritesCombined.pack");

		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(AngryDonkeyKongLibGDX.V_WIDTH / AngryDonkeyKongLibGDX.PPM,
				AngryDonkeyKongLibGDX.V_HEIGHT / AngryDonkeyKongLibGDX.PPM, gamecam);
		// other options for displays
		// gamePort = new ScreenViewport(gamecam);
		// gamePort = new StretchViewport(902, 590, gamecam);
		hud = new Hud(game.batch);

		mapLoader = new TmxMapLoader();
		map = mapLoader.load("marioMapV3.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / AngryDonkeyKongLibGDX.PPM);
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		world = new World(new Vector2(0, -5000 / AngryDonkeyKongLibGDX.PPM), true);
		b2dr = new Box2DDebugRenderer();

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

		// create ground
		for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
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

		// create platform
		for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
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

		// create mario in our game world
		player = new Player(world, this);
		barrel = new Barrel(world, this);
		
		//initialize variables 
		previousSpaceState = false;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub lol

	}

	public void handleInput(float dt) {
		
		// variables for velocity
		player_x_velocity = 0;
		player_y_velocity = 0;
		// key inputs for movement
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			player_y_velocity += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			player_x_velocity += 1;
			player.faceRight();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			player_x_velocity -= 1;
			player.faceLeft();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			barrel.startExplosion();
		}
		
		// key inputs for weapons
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !previousSpaceState) {
			//fire gun
			player.setStateFireGun();
			//System.out.println("firing gun now");
			
			//update the state recording variable
			previousSpaceState = true;
		}else if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && previousSpaceState){
			//stop gun
			player.setStateFireGun();

			//update the state recording variable
			previousSpaceState = false;
		}
		

		// put map boundaries x
		if (player.getX() < 0 && player_x_velocity < 0) {
			player_x_velocity = 0;
		} else if (player.getX() > 39 && player_x_velocity > 0) {
			player_x_velocity = 0;
		}
		
		// put map boundaries y
		if (player.getY() < 0 && player_y_velocity < 0) {
			player_y_velocity = 0;
		} else if (player.getY() > 18 && player_y_velocity > 0) {
			player_y_velocity = 0;
		}

		//for debugging
		//System.out.println(player.getX() + ", " + player.getY());
		
		// set the velocity to the player
		player.b2body.setLinearVelocity(player_x_velocity * Player.speed, player_y_velocity * Player.speed);

	}

	public void update(float dt) {
		handleInput(dt);

		world.step(1 / 60f, 6, 2);
		player.update(dt);
		barrel.update(dt);
		gamecam.update();
		renderer.setView(gamecam);
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		// clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		

		renderer.render();

		b2dr.render(world, gamecam.combined);
		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		// game.batch.draw(getAtlas().getTextures().first(), player.getX() -
		// player.getWidth()/4, player.getY() -
		// player.getHeight()/2,player.getWidth(),player.getHeight());
		
		player.draw(game.batch);
		barrel.draw(game.batch);
		game.batch.end();

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

	public TextureAtlas getAtlas() {
		return atlas;
	}

	public Hud getHud() {
		return hud;
	}

}
