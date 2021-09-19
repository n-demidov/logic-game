package logicgame_1.service.miniquest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoRepeatsValidator {

    public boolean valid(List<MiniQuestGenerator.Condition> statements, int repeatsLimit) {
        Map<String, Integer> inverseBySubjectIdx = new HashMap<>();
        for (MiniQuestGenerator.Condition statement : statements) {
            String key = "" + statement.subjectIdx + statement.inverse;
            inverseBySubjectIdx.compute(key,
                    (k, v) -> {
                if (v == null) {
                    v = 0;
                }
                v++;
                return v;
            });
        }

        int sum = 0;
        for (Integer value : inverseBySubjectIdx.values()) {
            if (value > 1) {
                sum += value;
            }
        }


        return sum <= repeatsLimit;
    }

}
