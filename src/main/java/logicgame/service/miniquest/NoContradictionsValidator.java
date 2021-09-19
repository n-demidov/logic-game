package logicgame.service.miniquest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoContradictionsValidator {

    public boolean valid(List<MiniQuestGenerator.Condition> statements, boolean contradictions) {
        if (contradictions) {
            return true;
        }

        Map<Integer, Boolean> valuesBySubjectIdx = new HashMap<>();
        for (MiniQuestGenerator.Condition statement : statements) {
            int key = statement.subjectIdx;
            Boolean value = valuesBySubjectIdx.get(key);
            if (value != null && value != statement.inverse) {
                return false;
            } else {
                valuesBySubjectIdx.put(key, statement.inverse);
            }
        }

        return true;
    }

}
