package pvz.controller.datamanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import pvz.controller.constants.Mode;
import pvz.controller.serializable.ScoreImpl;
/**
 * Class Test for DataManager.
 */
public class TestDataManager {
    /**
     * Basic test.
     */
    @Test
    public void test1() {
        final DataManagerInterface dtm = new DataManager();

        List<String> players = dtm.getRegisteredPlayers();
        players.stream().forEach(e -> dtm.removePlayer(e));
        players = dtm.getRegisteredPlayers();
        Assert.assertTrue(players.size() == 0);
        for (Mode mode : dtm.getHighScores().keySet()) {
            Assert.assertTrue(dtm.getHighScores().get(mode).isEmpty());
        }
        dtm.saveData();
        try {
            dtm.loadPlayer("Simone");
        } catch (IllegalStateException e) {

        }
        Assert.assertTrue(!dtm.getCurrentPlayer().isPresent());
        Assert.assertTrue(dtm.registerPlayer("Simone"));
        Assert.assertTrue(dtm.getCurrentPlayer().isPresent());
        Assert.assertTrue(dtm.registerPlayer("Mario"));
        Assert.assertTrue(dtm.getCurrentPlayer().isPresent());
        Assert.assertTrue(dtm.getRegisteredPlayers().containsAll(Arrays.asList("Simone", "Mario")));
        Assert.assertFalse(dtm.registerPlayer("Simone"));

        players = dtm.getRegisteredPlayers();
        players.stream().forEach(e -> dtm.removePlayer(e));
        players = dtm.getRegisteredPlayers();
        Assert.assertTrue(players.size() == 0);

        Assert.assertFalse(dtm.getHighScores().get(Mode.ARCADE).contains(new ScoreImpl(2, "Simone")));
        Assert.assertFalse(dtm.getPlayerHighScores("Simone", Mode.ARCADE).contains(new ScoreImpl(2, "Simone")));

        try {
            dtm.addScore(new ScoreImpl(2, "Simone"), Mode.ARCADE);
        } catch (IllegalArgumentException e) {

        }
        Assert.assertTrue(dtm.registerPlayer("Simone"));
        dtm.addScore(new ScoreImpl(2, "Simone"), Mode.ARCADE);

        Assert.assertTrue(dtm.getHighScores().get(Mode.ARCADE).contains(new ScoreImpl(2, "Simone")));
        Assert.assertTrue(dtm.getPlayerHighScores("Simone", Mode.ARCADE).contains(new ScoreImpl(2, "Simone")));

        DataManagerInterface dtm2 = new DataManager();

        Assert.assertFalse(dtm2.getHighScores().get(Mode.ARCADE).contains(new ScoreImpl(2, "Simone")));
        Assert.assertFalse(dtm2.getPlayerHighScores("Simone", Mode.ARCADE).contains(new ScoreImpl(2, "Simone")));

        dtm.saveData();

        dtm2 = new DataManager();

        Assert.assertTrue(dtm2.getHighScores().get(Mode.ARCADE).contains(new ScoreImpl(2, "Simone")));
        Assert.assertTrue(dtm2.getPlayerHighScores("Simone", Mode.ARCADE).contains(new ScoreImpl(2, "Simone")));

        dtm.saveData();
        Assert.assertEquals(dtm.getPlayerHighScores("Simone", Mode.ARCADE),
                new ArrayList<>(Arrays.asList(new ScoreImpl(2, "Simone"))));
        dtm.addScore(new ScoreImpl(10, "Simone"), Mode.ARCADE);
        Assert.assertNotEquals(dtm.getPlayerHighScores("Simone", Mode.ARCADE),
                new ArrayList<>(Arrays.asList(new ScoreImpl(2, "Simone"))));
        Assert.assertEquals(dtm.getPlayerHighScores("Simone", Mode.ARCADE),
                new ArrayList<>(Arrays.asList(new ScoreImpl(10, "Simone"), new ScoreImpl(2, "Simone"))));
    }

}
