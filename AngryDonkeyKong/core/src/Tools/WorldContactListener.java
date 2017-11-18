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

import sprites.Barrel;

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
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.KONG_BIT) {
				Gdx.app.log("Player", " collided with AngryDonkeyKong");
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.PRINCESS_BIT) {
				Gdx.app.log("Player", " collided with Princess");
			}
		}

	}

	@Override
	public void endContact(Contact contact) {
		// Gdx.app.log("End Contact", "");

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
