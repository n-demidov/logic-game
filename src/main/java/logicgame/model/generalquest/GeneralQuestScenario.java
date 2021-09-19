package logicgame.model.generalquest;

import logicgame.model.SubjectGroup;

import java.util.Collection;
import java.util.Collections;

public class GeneralQuestScenario {
    public final SubjectGroup subjectGroup;
    public final int subjectIdx;
    public final SubjectGroup variantGroup;
    public final int rightVariantIdx;
    public final GeneralQuestType type;
    public final Collection<Integer> excludedIdxs;

    public QuestType questType;
    public GeneralQuestScenario followingScenrio;

    public GeneralQuestScenario(SubjectGroup subjectGroup, int subjectIdx, SubjectGroup variantGroup, int rightVariantIdx, QuestType questType) {
        this(subjectGroup, subjectIdx, variantGroup, rightVariantIdx, questType, GeneralQuestType.STANDARD);
    }

    public GeneralQuestScenario(SubjectGroup subjectGroup, int subjectIdx, SubjectGroup variantGroup, int rightVariantIdx, QuestType questType, GeneralQuestType type) {
        this(subjectGroup, subjectIdx, variantGroup, rightVariantIdx, questType, type, Collections.EMPTY_SET);
    }

    public GeneralQuestScenario(SubjectGroup subjectGroup, int subjectIdx, SubjectGroup variantGroup,
                                int rightVariantIdx, QuestType questType, GeneralQuestType type, Collection<Integer> excludedIdxs) {
        this.subjectGroup = subjectGroup;
        this.subjectIdx = subjectIdx;
        this.variantGroup = variantGroup;
        this.rightVariantIdx = rightVariantIdx;
        this.questType = questType;
        this.type = type;
        this.excludedIdxs = excludedIdxs;
    }

    public GeneralQuestScenario getFollowingScenrio() {
        return followingScenrio;
    }

    public void setFollowingScenrio(GeneralQuestScenario followingScenrio) {
        this.followingScenrio = followingScenrio;
    }

    @Override
    public String toString() {
        return "GeneralQuestScenario{" +
                "subjectGroup=" + subjectGroup +
                ", subjectIdx=" + subjectIdx +
                ", variantGroup=" + variantGroup +
                ", rightVariantIdx=" + rightVariantIdx +
                ", type=" + type +
                ", excludedIdxs=" + excludedIdxs +
                ", questType=" + questType +
                '}';
    }

    public static enum QuestType {
        GLOBAL, FILTER
    }
}
