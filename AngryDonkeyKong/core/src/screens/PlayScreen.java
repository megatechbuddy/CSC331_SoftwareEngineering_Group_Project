//Author: Sean Benson, Kevin Singleton, Minh Hua
//Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.

package screens;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Tools.B2WorldCreator;
import Tools.WorldContactListener;
import scenes.Hud;
import sprites.Barrel;
import sprites.Bullet;
import sprites.Kong;
import sprites.Player;
import sprites.Player.State;
import sprites.Princess;
//import sprites.ATeamMan;

public class PlayScreen implements Screen {
	private AngryDonkeyKongLibGDX game;

	// DEBUG MODE
	private boolean debug_mode = false;
	
	
	// TODO: Switch from home screen texture to game format.
	// private Texture img;

	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;

	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	// time
	long time_startOfJump = 0;

	// Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;

	// sprites
	private Player player;
	private TextureAtlas atlas;
	private Barrel barrel;
	private Kong kong;
	private Princess princess;
	private Bullet bullet;
	// private ATeamMan ateamman;

	// velocities of the player
	private float player_x_velocity = 0;
	private float player_y_velocity = 0;

	// previous inputs
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

		world = new World(new Vector2(0, -3000 / AngryDonkeyKongLibGDX.PPM), true);
		if(debug_mode == true) {
			b2dr = new Box2DDebugRenderer();
		}
		//b2dr = new Box2DDebugRenderer();

		new B2WorldCreator(this);

		// create mario in our game world
		player = new Player(this);
		barrel = new Barrel(this);
		kong = new Kong(this);
		princess = new Princess(this);
		bullet = new Bullet(this);
		// ateamman = new ATeamMan(this);

		// initialize variables
		previousSpaceState = false;

		world.setContactListener(new WorldContactListener());
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub lol

	}

	public void handleInput(float dt) {

		// variables for velocity
		player_x_velocity = 0;
		// player_y_velocity = 0;
		if (player.getState() != State.JUMPING) {
			player_y_velocity = 0;
		}
		// key inputs for movement

		// if (Gdx.input.isKeyPressed(Input.Keys.UP) ||
		// Gdx.input.isKeyPressed(Input.Keys.W)) {
		// player_y_velocity += 1;
		// }
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			if (player.getState() != State.JUMPING && player.getState() != State.FALLING) {
				player.setStateJumping(true);
				time_startOfJump = System.currentTimeMillis();
				System.out.println("Initiating a new jump. Time: " + time_startOfJump);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			player_x_velocity += 2;
			player.faceRight();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			player_x_velocity -= 2;
			player.faceLeft();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			// barrel.startExplosion();
		}

		// key inputs for weapons
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !previousSpaceState) {
			// fire gun
			player.setStateFireGun();
			// System.out.println("firing gun now");

			// bullet
			bullet.b2body.setLinearVelocity(100, 0);

			// update the state recording variable
			previousSpaceState = true;
		} else if (!Gdx.input.isKeyPressed(Input.Keys.SPACE) && previousSpaceState) {
			// stop gun
			player.setStateFireGun();

			// update bullet

			// update the state recording variable
			previousSpaceState = false;
		}

		// for debugging
		// System.out.println(player.getX() + ", " + player.getY());

		// jumping
		if (player.getState() == State.JUMPING) {
			if ((System.currentTimeMillis() - time_startOfJump) > 150) {
				player.setStateJumping(false);
			}
			// execute the jump
			player_y_velocity = 2;
		}

		// set the velocity to the player
		player.b2body.setLinearVelocity(player_x_velocity * Player.speed, player_y_velocity * Player.speed);

	}

	boolean barrelState = true;

	public void moveBarrel() {
		//debuging
//		System.out.println("x: " + barrel.getX());
		
		//change direction
		if (barrel.getX() >= 58) {
			barrelState = false;
		} else if (barrel.getX() <= 1) {
			barrelState = true;
		}
		
		//move the barrel here
		if (barrelState) {
			// right
			barrel.b2body.setLinearVelocity(20, barrel.b2body.getLinearVelocity().y);
		} else {
			// left
			barrel.b2body.setLinearVelocity(-20, barrel.b2body.getLinearVelocity().y);
		}
	}

	public void update(float dt) {
		handleInput(dt);
		
		//update barrels
		moveBarrel();

		world.step(1 / 60f, 6, 2);
		player.update(dt);
		barrel.update(dt);
		kong.update(dt);
		princess.update(dt);
		bullet.update(dt);
		// ateamman.update(dt);
		hud.update(dt);
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

		if(debug_mode == true) {
			b2dr.render(world, gamecam.combined);
		}
		//b2dr.render(world, gamecam.combined);
		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		// game.batch.draw(getAtlas().getTextures().first(), player.getX() -
		// player.getWidth()/4, player.getY() -
		// player.getHeight()/2,player.getWidth(),player.getHeight());

		player.draw(game.batch);
		barrel.draw(game.batch);
		kong.draw(game.batch);
		princess.draw(game.batch);
		bullet.draw(game.batch);
		// ateamman.draw(game.batch);
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

	public TiledMap getMap() {
		return map;
	}

	public World getWorld() {
		return world;
	}

}
