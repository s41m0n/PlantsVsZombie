package pvz.controller.gameloop;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import pvz.controller.ControllerInterface;
import pvz.controller.constants.GameConstants;
import pvz.controller.constants.Mode;
import pvz.controller.datamanager.DataManagerInterface;
import pvz.controller.serializable.Player;
import pvz.controller.serializable.ScoreImpl;
import pvz.model.GameStatus;
import pvz.model.Model;
import pvz.model.ModelInterface;
import pvz.model.entity.Entity;
import pvz.model.entity.plant.PlantType;
import pvz.utility.Vector;
import pvz.view.ViewInterface;
import pvz.view.input.InputInterface;

/**
 * The class GameLoop is the core of the game.<br>
 * GameLoop can be in 4 different States:<br>
 * -Ready : GameLoop has been instanced but it is not running;<br>
 * -Running : GameLoop is running and it alternates phases of render and
 * update;<br>
 * -Pause : GameLoop is running but it doesn't do any render or update , it only
 * sleeps waiting for resume or quit; <br>
 * -Killed : In this state the GameLoop will be stopped and tells to Controller
 * to "kill" the loop;
 * 
 */
public class GameLoop extends Thread implements GameLoopInterface {

    /**
     * Enumeration for the loop State.<br>
     * The Loop can be in 4 different States : Ready,Running, Paused and Killed;
     */
    public enum State {
        /**
         * Instantiated and ready to run.
         */
        READY,
        /**
         * Running.
         * 
         */
        RUNNING,
        /**
         * Paused.
         */
        PAUSED,
        /**
         * Killed.
         */
        KILLED;
    }

    private ControllerInterface controller;
    private DataManagerInterface dataManager;
    private ViewInterface view;
    private ModelInterface model;
    private State state;
    private int fps;

    private int msPerRender;
    private Mode loopMode;

    /**
     * Basic constructor for GameLoop.It instantiates the model for the specific
     * mode .<br>
     * It also sets the specific parameter fps.
     * 
     * @param controller instance of ControllerInterface.
     * @param view  instance of ViewInterface.
     * @param level if level is empty will be choose ArcadeMode.
     * @param dtm datamanager.
     * @param mode mode.
     * @param fps frame per second.
     * @param plants plants you will use to play.
     */
    public GameLoop(final ControllerInterface controller, final ViewInterface view, final Optional<Integer> level,
            final DataManagerInterface dtm, final Mode mode, final int fps, final Set<PlantType> plants) {

        switch (mode) {
        case ARCADE:
            this.model = new Model(plants);
            break;
        case HISTORY:
            this.model = new Model(level.get(), plants);
        default:
            break;
        }
        this.loopMode = mode;
        this.state = State.READY;
        this.controller = controller;
        this.view = view;
        this.fps = fps;
        this.dataManager = dtm;
        this.msPerRender = 1000 / this.fps;

    }

    /**
     * This method sets the game State.
     * 
     * @param state
     */
    private void setState(final State state) {
        this.state = state;
    }

    /**
     * This method checks if the game State is state.
     * 
     * @param state state
     * @return is in state
     */
    public boolean isInState(final State state) {
        return this.state.equals(state);
    }

    /**
     * This method sets the game State in Killed.
     */
    public void abortGameLoop() {
        this.setState(State.KILLED);

    }

    /**
     * This method sets the game State in Paused.
     */
    public void pauseGameLoop() {
        if (this.isRunning()) {
            this.setState(State.PAUSED);
        }
    }

    /**
     * This method sets the game State in Running.
     */
    public void resumeGameLoop() {
        if (this.isInPause()) {
            this.setState(State.RUNNING);
        }
    }

    /**
     * This method checks if the GameLoop is Running.
     * 
     * @return isRunning
     */
    public boolean isRunning() {
        return this.isInState(State.RUNNING);
    }

    /**
     * This method checks if the GameLoop is Paused.
     * 
     * @return isInPause
     */
    public boolean isInPause() {
        return this.isInState(State.PAUSED);
    }

    /**
     * This method is the main loop of the game.
     */
    @Override
    public void run() {
        this.setState(State.RUNNING);
        long lastStep = System.currentTimeMillis();
        long lag = 0;

        while (!this.isInState(State.KILLED)) {
            if (this.isInState(State.RUNNING)) {
                if (this.model.getGameStatus().equals(GameStatus.PLAYING)) {

                    long loopTimer = System.currentTimeMillis();
                    long firstStep = System.currentTimeMillis();
                    long elapsed = firstStep - lastStep;
                    lastStep = firstStep;
                    lag += elapsed;

                    // INPUTS----------------------------------------------

                    this.parseInputs(this.view.getInput());

                    // UPDATE---------------------------------------------

                    while (lag >= GameConstants.MS_PER_UPDATE) {
                        this.model.update();
                        lag -= GameConstants.MS_PER_UPDATE;
                    }
                    // RENDER-------------------------------------------------

                    final List<Entity> toDraw = this.model.getEntityList();
                    this.view.render(toDraw, this.model.getAffordablePlants(), this.model.getCurrentEnergy(),
                            this.model.getCurrentLevel());

                    // SLEEP----------------------------------------------------
                    try {
                        long gap = System.currentTimeMillis() - loopTimer;
                        if (gap < this.msPerRender) {
                            Thread.sleep(this.msPerRender - gap);
                        }

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }

                } else if (this.model.getGameStatus().equals(GameStatus.LOST)) {
                    final int level = this.model.getCurrentLevel();
                    final Player player = this.dataManager.getCurrentPlayer().get();
                    this.abortGameLoop();
                    this.view.notifyLevelEnd(GameStatus.LOST, this.loopMode);

                    switch (this.loopMode) {
                    case ARCADE:
                        this.dataManager.addScore(new ScoreImpl(level, player.getName()), Mode.ARCADE);
                        break;
                    case HISTORY:
                        break;
                    default:
                        break;
                    }
                } else {
                    this.abortGameLoop();
                    this.view.notifyLevelEnd(GameStatus.WON, this.loopMode);

                    switch (this.loopMode) {
                    case ARCADE:
                        break;
                    case HISTORY:
                        final Player player = this.dataManager.getCurrentPlayer().get();
                        if (this.model.getCurrentLevel() == player.getLevelProgress()) {
                            this.dataManager.updatePlayerProgress();
                        }
                        break;
                    default:
                        break;
                    }
                }

            } else {
                this.pauseStep();
                lastStep = System.currentTimeMillis() + lag;
            }
        }
        this.controller.abortGameLoop();
    }

    /**
     * When the GameLoop is paused it sleeps for PAUSE_MS.
     */
    private void pauseStep() {
        try {
            Thread.sleep(GameConstants.PAUSE_MS);
        } catch (final InterruptedException e) {
            this.setState(State.KILLED);
        }
    }

    private void parseInputs(final List<InputInterface> inputs) {

        inputs.stream().forEach(e -> {
            switch (e.getInputType()) {
            case ADD_PLANT:
                this.model.putPlant(Vector.of(e.getX(), e.getY()), e.getPlant().get());
                break;
            case HARVEST_ENERGY:
                this.model.harvestEnergy(Vector.of(e.getX(), e.getY()));
                break;
            case REMOVE_PLANT:
                this.model.removePlant(Vector.of(e.getX(), e.getY()));
                break;
            default:
                break;
            }
        });

    }

    @Override
    public void startGameLoop() {
        this.start();
    }

}
