package pvz.view;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import pvz.controller.ControllerInterface;
import pvz.controller.constants.Mode;
import pvz.model.GameStatus;
import pvz.model.entity.Entity;
import pvz.model.entity.plant.PlantType;
import pvz.view.input.InputInterface;
import pvz.view.menu.GamingMenu;

/**
 * The class View! It starts the entirely screen.
 *
 */
public class View implements ViewInterface {

    private static ControllerInterface controller;
    private static GamingMenu gamingScreen;

    /**
     * Public constructor! Set the controller.
     * 
     * @param controller
     *            controller to be set.
     */
    public View(final ControllerInterface controller) {
        this.setController(controller);
    }

    /**
     * Thread safe setter for the controller.
     * 
     * @param controller
     *            controller to be set.
     */
    private synchronized void setController(final ControllerInterface controller) {
        View.controller = controller;
    }

    @Override
    public void init() {
        Application.launch(MainWindow.class);
    }

    @Override
    public void render(final List<Entity> listEntities, final List<PlantType> buttonsAvailable, final int currentEnergy,
            final int currentLevel) {
        Platform.runLater(() -> gamingScreen.render(listEntities, buttonsAvailable, currentEnergy, currentLevel));
    }

    @Override

    public void notifyLevelEnd(final GameStatus state, final Mode mode) {
        Platform.runLater(() -> gamingScreen.showLevelEnd(state, mode));

    }

    @Override
    public List<InputInterface> getInput() {
        return gamingScreen.getInputList();
    }

    /**
     * Static method to get the controller from all scenes.
     * 
     * @return the controller.
     */
    public static ControllerInterface getController() {
        return View.controller;
    }

    /**
     * Static method to set the gaming screen.
     * 
     * @param game
     *            the game menu created.
     */
    public static void setGamingScreen(final GamingMenu game) {
        gamingScreen = game;
    }
}
