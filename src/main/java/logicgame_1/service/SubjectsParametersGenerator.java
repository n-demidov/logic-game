package logicgame_1.service;

import com.usefulsoft.terver.service.Randomizer;
import logicgame_1.model.SubjectGroup;
import logicgame_1.model.SubjectParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectsParametersGenerator {
    private static final Randomizer randomizer = new Randomizer();

    public SubjectParameters generate(int subjectsNum) {
        Map<SubjectGroup, List<Integer>> subjectParamsByGroup = new HashMap<>(subjectsNum);
        for (SubjectGroup subjectGroup : SubjectGroup.values()) {
            subjectParamsByGroup.put(
                    subjectGroup,
                    getRandomParamsIdx(subjectsNum, ParamsDictionary.getSubjectParams(subjectGroup)));
        }

        return new SubjectParameters(subjectsNum, subjectParamsByGroup);
    }

    private List<Integer> getRandomParamsIdx(int subjectsNum, String[] params) {
        List<Integer> result = new ArrayList<>(subjectsNum);

        for (int subjectIdx = 0; subjectIdx < subjectsNum; subjectIdx++) {
            int randomIdx;
            do {
                randomIdx = randomizer.generateFromRange(0, params.length - 1);
            } while (result.contains(randomIdx) || params[randomIdx] == null);
            result.add(randomIdx);
        }

        return result;
    }

}
