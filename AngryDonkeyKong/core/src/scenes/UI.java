package scenes;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Sean Benson - 
 * Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.
 */
public class UI {
	private Stage stage;
	private Viewport viewport;

	private int worldTimer;
	private float timeCount;
	private int score;
	
	private Label countdownLabel;
	private Label scoreLabel;
	private Label timeLabel;
	private Label levelLabel;
	private Label marioLabel;
	
	/**
	 * Constructor that sets up the timer and labels for the game.
	 */
	public UI(SpriteBatch sb) {
		worldTimer = 300;
		timeCount = 0;
		score = 0;
		
		viewport = new FitViewport(AngryDonkeyKongLibGDX.V_WIDTH, AngryDonkeyKongLibGDX.V_HEIGHT, new OrthographicCamera());
	}
}
