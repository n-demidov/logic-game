package logicgame.model;

import java.util.List;
import java.util.Map;

public class SubjectParameters {
    private final int subjectsNum;
    public final Map<SubjectGroup, List<Integer>> subjectParamsByGroup;

    public SubjectParameters(int subjectsNum, Map<SubjectGroup, List<Integer>> subjectParamsByGroup) {
        this.subjectsNum = subjectsNum;
        this.subjectParamsByGroup = subjectParamsByGroup;
    }

    public List<Integer> getParamsIdxs(SubjectGroup subjectGroup) {
        return subjectParamsByGroup.get(subjectGroup);
    }

    public Map<SubjectGroup, List<Integer>> getSubjectParamsByGroup() {
        return subjectParamsByGroup;
    }

}
