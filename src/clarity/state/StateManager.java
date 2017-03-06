package clarity.state;

import clarity.graphics.Background;
import clarity.ui.MainMenu;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StateManager {
  private static final String LEVELS_PATH = "/levels/levels.txt";
  private static final String LOADING_SCREEN_PATH = "loadingScreen.png";

  private State currentState;
  private ArrayList<String> levels;
  private int currentLevelIndex;
  public static final Background loadingScreen = new Background(LOADING_SCREEN_PATH);

  /**
   * Create the state manager.
   */
  public StateManager() {
    this.currentState = new MainMenu(this);
    this.currentLevelIndex = 0;
    getLevels();
  }

  /**
   * @return The current level's index.
   */
  public int getCurrentLevelIndex() {
    return currentLevelIndex;
  }

  /**
   * @param nextLevel The next level's index.
   */
  public void setCurrentLevelIndex(int nextLevel) {
    this.currentLevelIndex = nextLevel;
  }


  /**
   * @param state The next state.
   */
  public void loadNextState(State state) {
    currentState.close();
    currentState = state;
  }

  /**
   * @return The current level's file path.
   */
  public String getCurrentLevel() {
    return levels.get(currentLevelIndex);
  }

  /**
   * @return The list of levels.
   */
  private ArrayList<String> getLevels() {
    if (levels == null) {
      levels = new ArrayList<String>();
      try {
        InputStream input = getClass().getResourceAsStream(LEVELS_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = "";
        while (line != null) {
          line = reader.readLine();
          if (line == null || line.isEmpty()) {
            break;
          }
          levels.add(line);
        }
        reader.close();
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    return levels;
  }

  /**
   * @return The current state of the game.
   */
  public State getCurrentState() {
    return currentState;
  }

  /**
   * Update the current state.
   */
  public void update() {
    if (currentState != null) {
      currentState.update();
    }
  }

  /**
   * Render the current state.
   * 
   * @param graphics The graphics to render.
   */
  public void render(Graphics2D graphics) {
    if (currentState != null) {
      currentState.render(graphics);
    }
  }

}
