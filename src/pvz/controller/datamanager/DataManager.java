package pvz.controller.datamanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import pvz.controller.constants.GameConstants;
import pvz.controller.constants.GamePaths;
import pvz.controller.constants.Mode;
import pvz.controller.serializable.Player;
import pvz.controller.serializable.PlayerImpl;
import pvz.controller.serializable.Score;
import pvz.controller.serializable.ScoreImpl;
import pvz.model.entity.plant.PlantType;

/**
 * This class handles all backups and all data referred to Players and Scores.
 * 
 */
public class DataManager implements DataManagerInterface {

    private final File playersDir = new File(GamePaths.PLAYERSINFO_DIR);
    private final File highScoresDir = new File(GamePaths.HIGHSCORES_DIR);
    private final File backUpDir = new File(GamePaths.PVZ_DIR);
    private final File arcadeHighScores = new File(GamePaths.ARCADE_HIGHSCORES_FILE);

    private Map<Mode, List<Score>> allHighScores;
    private Optional<Player> currentPlayer = Optional.empty();
    private String playerName;
    private boolean updatedPlayer;
    private boolean updatedScores;
    /**
     * Basic constructor for DataManager.
     */
    public DataManager() {
        try {
            this.setDirectories();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        this.allHighScores = new HashMap<>();
        this.updatedPlayer = false;
        this.updatedScores = false;
        this.loadScores();

    }

    @Override
    public void updatePlayerProgress() {
        if (this.currentPlayer.isPresent()) {
            this.currentPlayer.get().updateHistoryProgress();
            this.updatedPlayer = true;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override

    public void addScore(final Score score, final Mode mode) {
        if (this.getRegisteredPlayers().contains(score.getPlayer())
                || !GameConstants.AVAILABLE_MODES_FILES.contains(mode)) {
            if (!(this.allHighScores.get(mode).contains(score))) {
                List<Score> list = this.allHighScores.get(mode);
                list.add(score);
                this.allHighScores.get(mode).sort((s1, s2) -> -s1.getScore() + s2.getScore());
                this.updatedScores = true;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override

    public void loadPlayer(final String playerName) {

        if (this.updatedPlayer) {
            this.saveData();
        }
        if (this.playerAlreadyExist(playerName)) {

            this.playerName = playerName;
            this.updatedPlayer = false;

            try (JsonReader reader = new JsonReader(new FileReader(this.currentPlayerPath()))) {
                Gson gson = new GsonBuilder().create();

                PlayerImpl player = gson.fromJson(reader, PlayerImpl.class);
                this.currentPlayer = Optional.of(player);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalStateException();
        }

    }

    @Override
    public boolean registerPlayer(final String playerName) {
        if (this.updatedPlayer) {
            this.saveData();
        }
        this.checkDirectories();
        if (this.playerAlreadyExist(playerName)) {
            return false;
        } else {
            this.updatedPlayer = false;
            this.playerName = playerName;
            File newPlayerFile = new File(this.currentPlayerPath());

            try (Writer writer = new FileWriter(this.currentPlayerPath());) {

                newPlayerFile.createNewFile();
                this.currentPlayer = Optional.of(new PlayerImpl(playerName));
                this.playerName = playerName;

                Gson gson = new GsonBuilder().create();
                gson.toJson(this.currentPlayer.get(), writer);

            } catch (final Exception e) {
                e.printStackTrace();
            }
            return true;
        }

    }

    @Override
    public List<Score> getPlayerHighScores(final String playerName, final Mode mode) {

        List<Score> list = this.allHighScores.get(mode).stream().filter(e -> e.getPlayer().equals(playerName))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public Map<Mode, List<Score>> getHighScores() {
        return Collections.unmodifiableMap(this.allHighScores);
    }

    @Override
    public void saveData() {

        this.checkDirectories();
        if (this.updatedPlayer) {
            if (this.currentPlayer.isPresent()) {

                try (Writer writer = new FileWriter(this.currentPlayerPath());) {

                    Gson gson = new GsonBuilder().create();
                    gson.toJson(this.getPlayer(), writer);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.updatedPlayer = false;
            }
        }

        if (this.updatedScores) {
            for (Mode mode : this.allHighScores.keySet()) {
                try (Writer scoreWriter = new FileWriter(mode.getModePath())) {
                    Gson gson2 = new GsonBuilder().create();
                    gson2.toJson(this.allHighScores.get(mode), scoreWriter);
                } catch (IOException e) {

                    e.printStackTrace();
                }
                this.updatedScores = false;
            }

        }

    }

    @Override
    public List<String> getRegisteredPlayers() {
        this.checkDirectories();

        List<File> allPlayers = Arrays.asList(this.playersDir.listFiles());

        List<String> allPlayersNames = allPlayers.stream().map(e -> e.getName().replaceAll(".json", ""))
                .filter(e -> !e.equals(".DS_Store")).collect(Collectors.toList());
        return allPlayersNames;

    }
    @Override
    public Optional<Player> getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public List<String> getCredits() throws IOException {
        List<String> list = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(GamePaths.CREDITS_FILE)))) {
            String line = bf.readLine();
            while (line != null) {
                list.add(line);
                line = bf.readLine();
            }
        }
        return list;
    }
    @Override
    public void removePlayer(final String playerName) {
        if (this.playerAlreadyExist(playerName)) {
            final String playerPath = GamePaths.PLAYERSINFO_DIR + GamePaths.SEPARATOR + playerName + ".json";
            final File fileToRemove = new File(playerPath);
            fileToRemove.delete();
            if (this.currentPlayer.isPresent() && this.playerName.equals(playerName)) {
                this.updatedPlayer = false;
            }
            for (final Mode mode : this.allHighScores.keySet()) {
                final int oldSize = this.allHighScores.get(mode).size();
                this.allHighScores.get(mode).removeIf(e -> e.getPlayer().equals(playerName));
                final int newSize = this.allHighScores.get(mode).size();
                if (oldSize > newSize) {
                    this.updatedScores = true;
                }
            }
        }

    }

    private void loadScores() {

        for (Mode mode : GameConstants.AVAILABLE_MODES_FILES) {

            try (JsonReader reader = new JsonReader(new FileReader(mode.getModePath()))) {
                final Type type = new TypeToken<List<ScoreImpl>>() {
                }.getType();
                Gson gson = new GsonBuilder().create();

                List<Score> scoreList = gson.fromJson(reader, type);
                if (scoreList == null) {
                    scoreList = new ArrayList<>();
                }

                this.allHighScores.put(mode, scoreList);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private Player getPlayer() {
        return this.currentPlayer.get();
    }

    private void setDirectories() throws Exception {

        if (!backUpDir.exists()) {
            if (!backUpDir.mkdir() || !playersDir.mkdir() || !highScoresDir.mkdir()
                    || !arcadeHighScores.createNewFile()) {
                throw new Exception();
            }
        }

    }

    private String currentPlayerPath() {
        this.checkDirectories();

        return GamePaths.PLAYERSINFO_DIR + GamePaths.SEPARATOR + this.playerName + ".json";
    }

    private void checkDirectories() {
        if (!this.arcadeHighScores.exists() || !this.playersDir.exists()) {
            throw new IllegalStateException();
        }
    }

    @Override
    public List<PlantType> getPlantsUnlocked() {
        if (this.currentPlayer.isPresent()) {
            final int level = this.currentPlayer.get().getLevelProgress();
            List<PlantType> list = Arrays.asList(PlantType.values()).stream()
                    .limit(2 + (level == GameConstants.STORY_LEVELS ? level + 1 : level)).collect(Collectors.toList());
            return list;
        } else {
            throw new IllegalStateException();
        }

    }

    @Override
    public int getLevelsUnlocked() {
        if (this.currentPlayer.isPresent()) {
            return this.currentPlayer.get().getLevelProgress();
        } else {
            throw new IllegalStateException();
        }
    }

    private boolean playerAlreadyExist(final String playerName) {
        this.checkDirectories();

        return new File(GamePaths.PLAYERSINFO_DIR + System.getProperty("file.separator") + playerName + ".json")
                .exists();
    }

}
