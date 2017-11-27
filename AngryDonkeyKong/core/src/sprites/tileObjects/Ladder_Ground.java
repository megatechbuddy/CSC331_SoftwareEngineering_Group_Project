package sprites.tileObjects;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.maps.MapObject;
import screens.PlayScreen;
import sprites.InteractiveTileObject;

/**
 * @author Sean Benson
 * Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.
 */
public class Ladder_Ground extends InteractiveTileObject {
	public Ladder_Ground(PlayScreen screen,  MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        fixture.isSensor();
        setCategoryFilter(AngryDonkeyKongLibGDX.LADDER_GROUND);
    }
}
