//Author: Sean Benson 
//Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.

package Tools;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import scenes.Hud;
import sprites.Barrel;
import sprites.Player;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		// Gdx.app.log("Begin Contact", "");

		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		// if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
		// Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
		// Fixture object = head == fixA ? fixB : fixA;
		//
		// if(object.getUserData() instanceof InteractiveTileObject) {
		// ((InteractiveTileObject) object. getUserData()).onHeadHit();
		// }
		//
		// }

		if (fixA.getFilterData().categoryBits == AngryDonkeyKongLibGDX.PLAYER_BIT
				|| fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.PLAYER_BIT) {
			if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BARREL_BIT) {
				Gdx.app.log("Player", " collided with Barrel");
				((Barrel) fixB.getUserData()).startExplosion();
				Hud.addScore(500);
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.KONG_BIT) {
				Gdx.app.log("Player", " collided with AngryDonkeyKong");
				Hud.addScore(-500);
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.PRINCESS_BIT) {
				Gdx.app.log("Player", " collided with Princess");
				Hud.addScore(200);
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.LADDER_BIT) {
				Gdx.app.log("Player", " collided with Ladder");
				((Player) fixA.getUserData()).ladderCollision(true);
			}else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BARREL_BIT) {
				Gdx.app.log("Bullet", " collided with Barrel");
				((Barrel) fixA.getUserData()).startExplosion();
				Hud.addScore(500);
			} else {
			
			}
		}

		if (fixA.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BULLET_BIT
				|| fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BULLET_BIT) {
			if (fixA.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BARREL_BIT) {
				Gdx.app.log("Bullet", " collided with Barrel");
				((Barrel) fixA.getUserData()).startExplosion();
				Hud.addScore(500);
			}else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BARREL_BIT) {
				Gdx.app.log("Bullet", " collided with Barrel");
				((Barrel) fixB.getUserData()).startExplosion();
				Hud.addScore(500);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// Gdx.app.log("End Contact", "");

		Fixture fixA = contact.getFixtureA();
		if(fixA.getFilterData().categoryBits == AngryDonkeyKongLibGDX.PLAYER_BIT) {
			((Player) fixA.getUserData()).ladderCollision(false);		
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
