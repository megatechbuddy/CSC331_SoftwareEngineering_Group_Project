package scenes;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UI {
	public Stage stage;
	private Viewport viewport;

	private int worldTimer;
	private float timeCount;
	private int score;
	
	Label countdownLabel;
	Label scoreLabel;
	Label timeLabel;
	Label levelLabel;
	Label marioLabel;
	
	public UI(SpriteBatch sb) {
		worldTimer = 300;
		timeCount = 0;
		score = 0;
		
		viewport = new FitViewport(AngryDonkeyKongLibGDX.V_WIDTH, AngryDonkeyKongLibGDX.V_HEIGHT, new OrthographicCamera());
	}
}
