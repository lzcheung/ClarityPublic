package clarity.main;

import clarity.state.StateManager;
import clarity.utilities.Timer;
import clarity.utilities.input.Keyboard;
import clarity.utilities.input.Mouse;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {

  /**
   * Default serial ID.
   */
  private static final long serialVersionUID = 1L;
  /**
   * Width of the window.
   */
  public static final int WINDOW_WIDTH = 480;
  /**
   * Height of the window.
   */
  public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
  /**
   * Scale of the window.
   */
  public static final int SCALE = 3;
  /**
   * Title of the game.
   */
  public static final String TITLE = "Clarity";

  private static boolean isRunning;

  /**
   * Set to true to enable full screen mode.
   */
  public static final boolean FULL_SCREEN_MODE = false;
  public static final int MONITOR_WIDTH =
      (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
  public static final int MONITOR_HEIGHT =
      (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
  public static final double MONITOR_SCALE =
      Toolkit.getDefaultToolkit().getScreenSize().getWidth() / WINDOW_WIDTH;

  private JFrame frame;
  private transient Graphics2D graphics;
  private transient BufferedImage image;
  private StateManager manager;

  private Keyboard keyboard;

  /**
   * Game constructor.
   */
  public Game() {
    setPreferredSize(new Dimension((int) (WINDOW_WIDTH * SCALE), (int) (WINDOW_HEIGHT * SCALE)));
    setFocusable(true);
    requestFocus();
    keyboard = new Keyboard();
    addKeyListener(keyboard);
    addMouseListener(Mouse.getMouse());
    addMouseMotionListener(Mouse.getMouse());

    image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
    graphics = (Graphics2D) image.getGraphics();

    frame = new JFrame();
    frame.setResizable(false);
    frame.setTitle(TITLE);
    frame.add(this);
    if (FULL_SCREEN_MODE) {
      frame.setUndecorated(true); // no boarders
    }
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    if (FULL_SCREEN_MODE) {
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // full screen mode
    }
    frame.setVisible(true);
    frame.setFocusable(true);
    isRunning = true;
    run();
  }

  private void run() {
    Timer gameLoopTimer = new Timer();
    Timer titleTimer = new Timer();
    int frames = 0;
    int updates = 0;
    requestFocus();
    manager = new StateManager(keyboard);

    while (isRunning) {
      if (gameLoopTimer.hasElapsed(1000.0 / 60.0)) { // update 60 times per second
        update();
        updates++;
        gameLoopTimer.reset();
        render(); // render as fast as possible
        frames++;
      }
      if (titleTimer.hasElapsed(1000)) {
        frame.setTitle(TITLE + "  |  " + updates + " UPS, " + frames + " FPS");
        updates = 0;
        frames = 0;
        titleTimer.reset();
      }
    }
  }

  private void update() {
    keyboard.update();
    manager.update();
  }

  private void render() {
    manager.render(graphics);

    //////// add above here ////////
    Graphics graphics2 = getGraphics(); // gets image to render
    if (FULL_SCREEN_MODE) {
      // render image to screen
      graphics2.drawImage(image, (int) (MONITOR_WIDTH - WINDOW_WIDTH * MONITOR_SCALE) / 2,
          (int) (MONITOR_HEIGHT - WINDOW_HEIGHT * MONITOR_SCALE) / 2,
          (int) (WINDOW_WIDTH * MONITOR_SCALE), (int) (WINDOW_HEIGHT * MONITOR_SCALE), null);
    } else {
      // render image to screen
      graphics2.drawImage(image, 0, 0, (int) (WINDOW_WIDTH * SCALE), (int) (WINDOW_HEIGHT * SCALE),
          null);
    }
    graphics2.dispose(); // dispose of graphics from memory
  }

  /**
   * Exit the game.
   */
  public static void exitGame() {
    isRunning = false;
  }

}
