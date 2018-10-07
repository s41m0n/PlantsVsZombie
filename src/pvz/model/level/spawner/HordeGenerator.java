package pvz.model.level.spawner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import pvz.model.entity.zombie.ZombieType;
import pvz.utility.Pair;
/**
 * This class defines the internal logic that Arcade level builds hordes of zombie.
 */
public final class HordeGenerator {

    private static final int NEW_ZOMBIE_DELAY = 3;
    private static final List<ZombieType> ALLZOMBIE_TYPE = new ArrayList<>(Arrays.asList(ZombieType.values()));
    private static final int HORDE_AMOUNT = 4;
    private static final Double[] HORDE_PROPORTIONS = new Double[] { 0.1, 0.2, 0.3, 0.4 };
    private static final int BASIC_HORDE_DELAY = 20;
    private static final int BASIC_ZOMBIE_AMOUNT = 10;
    private static final int ZOMBIE_UPGRADE = 2;
    /**
     * 
     * @param level level value.
     * @return horde of zobie
     */
    public static List<Pair<Map<ZombieType, Integer>, Integer>> getHorde(final int level) {

        int totZombies = BASIC_ZOMBIE_AMOUNT + (level * ZOMBIE_UPGRADE);
        List<Pair<Map<ZombieType, Integer>, Integer>> zombieList = new ArrayList<>();
        final int zombieUnlocked = getZombies(level).size();
        for (int i = 0; i < HORDE_AMOUNT; i++) {

            Map<ZombieType, Integer> zombieMap = new HashMap<>();
            int zombieAmount = (int) (totZombies * HORDE_PROPORTIONS[i]);

            for (final ZombieType type : getZombies(level)) {
                int amount = (zombieAmount / zombieUnlocked);
                amount = (amount == 0) ? 1 : 0;
                zombieMap.put(type, amount);
                zombieAmount -= amount;
                if (zombieAmount <= 0) {
                    break;
                }
            }
            if (zombieAmount > 0) {
                Random rnd = new Random();
                ZombieType typeToReplace = getZombies(level).get(rnd.nextInt(zombieUnlocked));
                int newAmount = zombieMap.get(typeToReplace) + zombieAmount;
                zombieMap.replace(typeToReplace, newAmount);
            }
            zombieList.add(new Pair<>(zombieMap, BASIC_HORDE_DELAY));

        }

        return zombieList;
    }

    private static List<ZombieType> getZombies(final int level) {
        final int zombieToDispense = 1 + (int) (level / NEW_ZOMBIE_DELAY);
        return ALLZOMBIE_TYPE.stream().limit(zombieToDispense).collect(Collectors.toList());
    }
   /**
    * Private constructor for horde generator.
    */
   private HordeGenerator() {
   }

}
