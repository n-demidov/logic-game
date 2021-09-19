package logicgame.model.generalquest;

import logicgame.model.SubjectGroup;

public class PairGeneralQuestScenario {
    public final SubjectGroup subjectGroup;
    public final int subjectIdx1;
    public final int subjectIdx2;
    public final SubjectGroup variantGroup;
    public final int rightVariantIdx;
    public final boolean inverse;

    public PairGeneralQuestScenario(SubjectGroup subjectGroup, int subjectIdx1, int subjectIdx2, SubjectGroup variantGroup, int rightVariantIdx) {
        this(subjectGroup, subjectIdx1, subjectIdx2, variantGroup, rightVariantIdx, false);
    }

    public PairGeneralQuestScenario(SubjectGroup subjectGroup, int subjectIdx1, int subjectIdx2, SubjectGroup variantGroup, int rightVariantIdx, boolean inverse) {
        this.subjectGroup = subjectGroup;
        this.subjectIdx1 = subjectIdx1;
        this.subjectIdx2 = subjectIdx2;
        this.variantGroup = variantGroup;
        this.rightVariantIdx = rightVariantIdx;
        this.inverse = inverse;
    }
}
