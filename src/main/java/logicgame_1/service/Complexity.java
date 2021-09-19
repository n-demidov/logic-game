package logicgame_1.service;

import java.util.HashMap;
import java.util.Map;

public class Complexity {
    public static int INFINITY_REPEATS_LIMIT = -1;

    private Map<Integer, Integer> statementsNumByLevel = new HashMap<>();
    private Map<Integer, Integer> repeatsLimit = new HashMap<>();
    private Map<Integer, Integer> trueStatesmentsNum = new HashMap<>();

    public Complexity() {
        initStatementsNum();
        initNoRepeats();
        initTrueStatesmentsNum();
    }

    public int getStatementsNum(int level) {
        return statementsNumByLevel.get(level);
    }

    public int getRepeatsLimit(int level) {
        return repeatsLimit.get(level);
    }

    public int getTrueStatesmentsNum(int level) {
        return trueStatesmentsNum.get(level);
    }

    private void initStatementsNum() {
        int i = 0;
        statementsNumByLevel.put(i++, 3);
        statementsNumByLevel.put(i++, 4);
        statementsNumByLevel.put(i++, 5);
        statementsNumByLevel.put(i++, 5);
        statementsNumByLevel.put(i++, 6);
    }

    private void initNoRepeats() {
        int i = 0;
        repeatsLimit.put(i++, 0);
        repeatsLimit.put(i++, 2);
        repeatsLimit.put(i++, 3);
        repeatsLimit.put(i++, 3);
        repeatsLimit.put(i++, 4);
    }

    private void initTrueStatesmentsNum() {
        int i = 0;
        trueStatesmentsNum.put(i++, 1);
        trueStatesmentsNum.put(i++, 1);
        trueStatesmentsNum.put(i++, 2);
        trueStatesmentsNum.put(i++, 2);
        trueStatesmentsNum.put(i++, 3);
    }
}
