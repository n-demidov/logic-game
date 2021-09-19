package logicgame_1.model.generalquest;

import logicgame_1.model.SubjectGroup;

public class GeneralQuestAnswer {
    private int[] answerBySubjectIdx = new int[3];

    public GeneralQuestAnswer addParam(SubjectGroup subjectGroup, int subjectIdx) {
        answerBySubjectIdx[subjectGroup.ordinal()] = subjectIdx;
        return this;
    }

    public int[] getAnswerBySubjectIdxs() {
        return answerBySubjectIdx;
    }
}
