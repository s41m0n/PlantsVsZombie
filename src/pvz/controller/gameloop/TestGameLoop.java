package pvz.controller.gameloop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Assert;
import org.junit.Test;

import pvz.controller.ControllerInterface;
import pvz.controller.constants.Mode;
import pvz.controller.datamanager.DataManagerInterface;
import pvz.controller.gameloop.GameLoop.State;
import pvz.controller.serializable.Player;
import pvz.controller.serializable.PlayerImpl;
import pvz.controller.serializable.Score;
import pvz.model.GameStatus;
import pvz.model.entity.Entity;
import pvz.model.entity.plant.PlantType;
import pvz.view.ViewInterface;
import pvz.view.input.InputInterface;

/**
 * Test GameLoop.
 */
public class TestGameLoop {
    private static final int SLEEP = 2000;
    /**
     * Basic test for GameLoop.
     */
    @Test
    public void basicTest() {
        final ViewInterface view = new TestView();
        final ControllerInterface controller = new TestController();
        final DataManagerInterface dataManager = new TestDataManager();
        final GameLoop gameLoop = new GameLoop(controller, view, Optional.of(1), dataManager, Mode.HISTORY, 60,
                new TreeSet<>(Arrays.asList(PlantType.values())));
        Assert.assertTrue(gameLoop.isInState(State.READY));
        gameLoop.start();
        try {
            Thread.sleep(SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(gameLoop.isRunning());
        Assert.assertFalse(gameLoop.isInPause());
        try {
            Thread.sleep(SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameLoop.pauseGameLoop();
        Assert.assertFalse(gameLoop.isRunning());
        Assert.assertTrue(gameLoop.isInPause());
        gameLoop.resumeGameLoop();
        Assert.assertTrue(gameLoop.isRunning());
        Assert.assertFalse(gameLoop.isInPause());
        try {
            Thread.sleep(SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameLoop.abortGameLoop();
        Assert.assertFalse(gameLoop.isRunning());
        Assert.assertFalse(gameLoop.isInPause());
        Assert.assertTrue(gameLoop.isInState(State.KILLED));

    }

    private class TestView implements ViewInterface {

        @Override
        public void init() {
        }

        @Override
        public void render(final List<Entity> listEntities, final List<PlantType> buttonsAffordable, final int currentEnergy,
                final int currentLevel) {
        }

        @Override
        public void notifyLevelEnd(final GameStatus state, final Mode mode) {
        }

        @Override
        public List<InputInterface> getInput() {
            return new ArrayList<>();
        }

    }


    private class TestController implements ControllerInterface {

        @Override
        public void quit() {
        }

        @Override
        public void abortGameLoop() {

        }

        @Override
        public void resumeGameLoop() {

        }

        @Override
        public void pauseGameLoop() {

        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public boolean isInPause() {
            return false;
        }

        @Override
        public boolean registerPlayer(final String playerName) {
            return false;
        }

        @Override
        public void loadPlayer(final String playerName) {

        }

        @Override
        public List<String> getRegisteredPlayers() {
            return null;
        }

        @Override
        public Map<Mode, List<Score>> getHighScores() {
            return null;
        }

        @Override
        public List<Score> getPlayerHighScores(final String playerName, final Mode mode) {
            return null;
        }

        @Override
        public List<PlantType> getPlantsUnlocked() {
            return null;
        }

        @Override
        public int getLevelsUnlocked() {
            return 0;
        }

        @Override
        public void startGame(final Optional<Integer> level, final Set<PlantType> plants) {

        }

        @Override
        public List<String> getCredits() throws IOException {
            return null;
        }

        @Override
        public void setFPS(final int fps) {

        }

        @Override
        public void setMode(final Mode mode) {

        }

        @Override
        public int getFPS() {
            return 0;
        }

        @Override
        public void removePlayer(final String playerName) {
        }

        @Override
        public void startGameLoop() {
        }

    }

    private class TestDataManager implements DataManagerInterface {

        @Override
        public void updatePlayerProgress() {
        }

        @Override
        public void addScore(final Score score, final Mode mode) {
        }

        @Override
        public void loadPlayer(final String playerName) {

        }

        @Override
        public boolean registerPlayer(final String playerName) {
            return false;
        }

        @Override
        public List<Score> getPlayerHighScores(final String playerName, final Mode mode) {

            return null;
        }

        @Override
        public Map<Mode, List<Score>> getHighScores() {

            return null;
        }

        @Override
        public void saveData() {

        }

        @Override
        public List<String> getRegisteredPlayers() {
            return null;
        }

        @Override
        public Optional<Player> getCurrentPlayer() {
            return Optional.of(new PlayerImpl("Simone"));
        }

        @Override
        public List<String> getCredits() throws IOException {

            return null;
        }

        @Override
        public void removePlayer(final String playerName) {

        }

        @Override
        public List<PlantType> getPlantsUnlocked() {
            return null;
        }

        @Override
        public int getLevelsUnlocked() {
            return 0;
        }
    }
}
