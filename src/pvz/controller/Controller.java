package pvz.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import pvz.controller.constants.GameConstants;
import pvz.controller.constants.Mode;
import pvz.controller.datamanager.DataManager;
import pvz.controller.datamanager.DataManagerInterface;
import pvz.controller.gameloop.GameLoop;
import pvz.controller.gameloop.GameLoopInterface;
import pvz.controller.serializable.Score;
import pvz.model.entity.plant.PlantType;
import pvz.view.ViewInterface;

/**
 * Controller.
 */
public class Controller implements ControllerInterface {

    private ViewInterface view;
 
    private Optional<GameLoopInterface> gameLoop;
    private DataManagerInterface dataManager;

    private Integer fps;
    private Optional<Mode> currentMode;

    /**
     * Basic constructor for controller, it sets 60fps for default.<br>
     */
    public Controller() {
        this.fps = GameConstants.FPS_60;
        this.dataManager = new DataManager();
        this.gameLoop = Optional.empty();
        this.currentMode = Optional.empty();
    }

    @Override
    public void quit() {
        this.dataManager.saveData();
        System.exit(0);
    }

    @Override
    public void abortGameLoop() {
        if (this.gameLoop.isPresent()) {
            if (this.gameLoop.get().isInPause()) {
                this.gameLoop.get().abortGameLoop();
            } else {
                this.gameLoop = Optional.empty();
            }
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void resumeGameLoop() {
        if (!this.gameLoop.get().isInPause()) {
            throw new IllegalStateException();
        } else {
            this.gameLoop.get().resumeGameLoop();
        }
    }

    @Override
    public void pauseGameLoop() {
        if (!this.gameLoop.get().isRunning()) {
            throw new IllegalStateException();
        } else {
            this.gameLoop.get().pauseGameLoop();
        }
    }

    @Override
    public boolean isRunning() {
        if (this.gameLoop.isPresent()) {
            return this.gameLoop.get().isRunning();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean isInPause() {
        if (this.gameLoop.isPresent()) {
            return this.gameLoop.get().isInPause();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean registerPlayer(final String playerName) {
        return this.dataManager.registerPlayer(playerName);
    }

    @Override
    public void loadPlayer(final String playerName) {
        this.dataManager.loadPlayer(playerName);
    }

    @Override
    public List<String> getRegisteredPlayers() {
        return this.dataManager.getRegisteredPlayers();
    }

    @Override
    public Map<Mode, List<Score>> getHighScores() {
        return this.dataManager.getHighScores();
    }

    @Override
    public List<Score> getPlayerHighScores(final String playerName, final Mode mode) {

        return this.dataManager.getPlayerHighScores(playerName, mode);
    }

    @Override
    public List<PlantType> getPlantsUnlocked() {
        return  this.dataManager.getPlantsUnlocked();
    }

    @Override
    public int getLevelsUnlocked() {
        return this.dataManager.getLevelsUnlocked();
    }

    @Override
    public void startGame(final Optional<Integer> level, final Set<PlantType> plants) {

        if (this.gameLoop.isPresent() ||  !this.currentMode.isPresent()) {
            throw new IllegalStateException();
        }
        final GameLoop gameLoop = new GameLoop(this, this.view, level, this.dataManager, this.currentMode.get(),
                this.fps, plants);
        this.gameLoop = Optional.of(gameLoop);
        this.gameLoop.get().startGameLoop();

    }

    @Override
    public List<String> getCredits() throws IOException {
        return this.dataManager.getCredits();
    }

    @Override
    public void setFPS(final int fps) {
        this.fps = fps;
    }

    @Override
    public void setMode(final Mode mode) {
        this.currentMode = Optional.of(mode);
    }

    @Override
    public int getFPS() {
        return this.fps;
    }

    @Override
    public void removePlayer(final String playerName) {
        this.dataManager.removePlayer(playerName);
    }

    @Override
    public void startGameLoop() {
        this.gameLoop.get().startGameLoop();
    }
    /**
     * This method sets the view.
     * @param view instance of ViewInterface.
     */
    public void setView(final ViewInterface view) {
        this.view = view;
    }
}
