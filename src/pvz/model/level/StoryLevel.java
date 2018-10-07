package pvz.model.level;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import pvz.controller.constants.GameConstants;
import pvz.model.GameStatus;
import pvz.model.entity.Entity;
import pvz.model.entity.zombie.ZombieType;
import pvz.model.level.spawner.ZombieSpawner;
import pvz.utility.Pair;
/**
 * Story Level.
 */
public class StoryLevel extends AbstractLevel {

    private String levelPath;
    /**
     * StoryLevel public constructor.
     * @param level level value.
     * @param entityList entity list.
     */
    public StoryLevel(final int level, final List<Entity> entityList) {

        super(entityList, level);
        if (level <= 0 || level > GameConstants.STORY_LEVELS) {
            throw new IllegalArgumentException();
        }
        this.levelPath = "/levels/level" + level + ".json";
        this.setZombieSpawner(new ZombieSpawner(this.loadZombies(level), this));

    }
    @Override
    public void win() {
        this.setGameStatus(GameStatus.WON);
    }

    private List<Pair<Map<ZombieType, Integer>, Integer>> loadZombies(final int level) {

        List<Pair<Map<ZombieType, Integer>, Integer>> zombieList = Collections.emptyList();

        try (Reader reader = new InputStreamReader(this.getClass().getResourceAsStream(this.levelPath))) {
            final Type type = new TypeToken<List<Pair<Map<ZombieType, Integer>, Integer>>>() {
            }.getType();
            Gson gson = new GsonBuilder().create();
            zombieList = gson.fromJson(reader, type);
            return zombieList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

}
