package logicgame_1.model.generalquest;

import logicgame_1.model.SubjectGroup;

public class GeneralQuestScenario {
    public final SubjectGroup subjectGroup;
    public final int subjectIdx;
    public final SubjectGroup variantGroup;
    public final int rightVariantIdx;
    public final boolean inverse;

    public GeneralQuestScenario(SubjectGroup subjectGroup, int subjectIdx, SubjectGroup variantGroup, int rightVariantIdx) {
        this(subjectGroup, subjectIdx, variantGroup, rightVariantIdx, false);
    }

    public GeneralQuestScenario(SubjectGroup subjectGroup, int subjectIdx, SubjectGroup variantGroup, int rightVariantIdx, boolean inverse) {
        this.subjectGroup = subjectGroup;
        this.subjectIdx = subjectIdx;
        this.variantGroup = variantGroup;
        this.rightVariantIdx = rightVariantIdx;
        this.inverse = inverse;
    }
}
