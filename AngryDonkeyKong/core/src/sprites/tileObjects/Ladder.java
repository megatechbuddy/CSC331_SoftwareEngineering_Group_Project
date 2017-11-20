//Author: Sean Benson 
//Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.
package sprites.tileObjects;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;

import screens.PlayScreen;
import sprites.InteractiveTileObject;

public class Ladder  extends InteractiveTileObject {
	public Ladder(PlayScreen screen,  MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        fixture.isSensor();
        setCategoryFilter(AngryDonkeyKongLibGDX.LADDER_BIT);
    }
}
