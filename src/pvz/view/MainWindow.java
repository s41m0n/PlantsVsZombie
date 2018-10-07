package pvz.view;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pvz.view.input.QuitHandler;
import pvz.view.menu.MainMenu;

/**
 * The main Window that will'be launched.
 * 
 */
public class MainWindow extends Application {

    /**
     * Monitor Width.
     */
    private static final double MONITOR_WIDTH = Screen.getPrimary().getBounds().getWidth();

    /**
     * Ratio Width/Height.
     */
    private static final double RATIO = 1.6;

    private static final MediaPlayer PVZMUSIC = new MediaPlayer(
            new Media(MainWindow.class.getResource("/music/MenuMusic.mp3").toString()));
    private static final MediaPlayer CROWS = new MediaPlayer(
            new Media(MainWindow.class.getResource("/music/Crows.wav").toExternalForm()));
    private static final MediaPlayer SUN = new MediaPlayer(
            new Media(MainWindow.class.getResource("/music/SunPickup.wav").toExternalForm()));

    private static boolean soundOff = false;

    private final Stage mainWindow = new Stage();

    /**
     * Scene width.
     */
    private static final double WIDTH = MONITOR_WIDTH / 1.25;

    /**
     * Scene height.
     */
    private static final double HEIGHT = WIDTH / RATIO;

    /**
     * Empty Constructor.
     */
    public MainWindow() {
    }

    @Override
    public void start(final Stage primaryStage) {
        this.mainWindow.initStyle(StageStyle.DECORATED);
        this.mainWindow.setTitle("Plants vs Zombies");
        this.mainWindow.centerOnScreen();
        this.mainWindow.setResizable(false);
        this.mainWindow.setOnCloseRequest(e -> {
            e.consume();
            try {
                if (View.getController().isRunning()) {
                    View.getController().pauseGameLoop();
                }
            } catch (IllegalStateException p) {
            }
            QuitHandler.exitProgram(this.mainWindow);
        });

        this.mainWindow.setScene(MainMenu.get(this.mainWindow));
        this.mainWindow.show();
        SUN.setVolume(SUN.getVolume() / 2);
        PVZMUSIC.setCycleCount(MediaPlayer.INDEFINITE);
        PVZMUSIC.setAutoPlay(true);
    }

    /**
     * Method to play crows sound.
     */
    public static void playCrows() {
        if (!soundOff) {
            CROWS.stop();
            CROWS.play();
        }
    }

    /**
     * Method to play sunPickup sound.
     */
    public static void playSun() {
        if (!soundOff) {
            SUN.stop();
            SUN.play();
        }
    }

    /**
     * Method to stop background music.
     */
    public static void disableMusic() {
        soundOff = true;
        PVZMUSIC.stop();
    }

    /**
     * Method to resume background music.
     */
    public static void resumeMusic() {
        soundOff = false;
        PVZMUSIC.play();
    }

    /**
     * Method to know if music is disabled.
     * 
     * @return true if music is off, else true.
     */
    public static boolean isMusicDisabled() {
        return soundOff;
    }

    /**
     * Public getter for scene height.
     * 
     * @return scene's Height.
     */
    public static double getSceneHeight() {
        return HEIGHT;
    }

    /**
     * Public getter for scene width.
     * 
     * @return scene's Width.
     */
    public static double getSceneWidth() {
        return WIDTH;
    }
}
