package logicgame.service;

import java.util.HashMap;
import java.util.Map;

public class Complexity {
    public static int INFINITY_REPEATS_LIMIT = -1;
    public static int MAX_COMPLEXITY_COUNTER = 6;

    private final Map<Integer, Integer> statementsNumByLevel = new HashMap<>();
    private final Map<Integer, Integer> repeatsLimit = new HashMap<>();
    private final Map<Integer, Boolean> contradictionsAllow = new HashMap<>();

    public Complexity() {
        initStatementsNum();
        initNoRepeats();
        initContradictionsAllow();
    }

    public int normalizeComplexity(int complexityCounter) {
        return Math.min(complexityCounter, MAX_COMPLEXITY_COUNTER);
    }

    public int getStatementsNum(int level) {
        return statementsNumByLevel.get(level);
    }

    public int getRepeatsLimit(int level) {
        return repeatsLimit.get(level);
    }

    public boolean getContradictionsAllow(int level) {
        return contradictionsAllow.get(level);
    }

    private void initStatementsNum() {
        int i = 0;
        statementsNumByLevel.put(i++, 3);
        statementsNumByLevel.put(i++, 3);

        statementsNumByLevel.put(i++, 4);
        statementsNumByLevel.put(i++, 5);
        statementsNumByLevel.put(i++, 5);
        statementsNumByLevel.put(i++, 6);

        statementsNumByLevel.put(i++, 7); // Из-за исключения некоторых персонажей мы можем получить только 6 уникальных предметов.
    }

    private void initNoRepeats() {
        int i = 0;
        repeatsLimit.put(i++, INFINITY_REPEATS_LIMIT);
        repeatsLimit.put(i++, 0);

        repeatsLimit.put(i++, 2);
        repeatsLimit.put(i++, 4);
        repeatsLimit.put(i++, 0);
        repeatsLimit.put(i++, 3);

        repeatsLimit.put(i++, 4);
    }

    private void initContradictionsAllow() {
        int i = 0;
        contradictionsAllow.put(i++, false);
        contradictionsAllow.put(i++, true);

        contradictionsAllow.put(i++, false);
        contradictionsAllow.put(i++, true);
        contradictionsAllow.put(i++, true);
        contradictionsAllow.put(i++, false);

        contradictionsAllow.put(i++, false);
    }

}
