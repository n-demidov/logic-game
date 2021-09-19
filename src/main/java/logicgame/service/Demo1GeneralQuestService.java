package logicgame.service;

import logicgame.model.SubjectGroup;
import logicgame.model.SubjectParameters;
import logicgame.model.generalquest.GeneralQuestAnswer;
import logicgame.model.generalquest.GeneralQuestParams;
import logicgame.model.generalquest.GeneralQuestScenario;
import logicgame.model.generalquest.GeneralQuestType;

import java.util.ArrayList;
import java.util.List;

public class Demo1GeneralQuestService {
    private static final int SOUGHT_SMOKED_ID = 1;
    private static final int MULTIPLIER_RANGE = 3;

    private static final List<GeneralQuestScenario> miniQuestParamsTemplate = new ArrayList<>();

    static {
        initScenarioTemplates();
    }

    private static void initScenarioTemplates() {
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.NATIONALITY, 0, SubjectGroup.DRINKS, 0, GeneralQuestScenario.QuestType.GLOBAL, GeneralQuestType.INVERSED));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.NATIONALITY, 2, SubjectGroup.DRINKS, 1, GeneralQuestScenario.QuestType.GLOBAL, GeneralQuestType.INVERSED));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.SMOKES, 2, SubjectGroup.DRINKS, 0, GeneralQuestScenario.QuestType.GLOBAL, GeneralQuestType.INVERSED));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.SMOKES, 0, SubjectGroup.DRINKS, 1, GeneralQuestScenario.QuestType.GLOBAL, GeneralQuestType.STANDARD));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.NATIONALITY, 2, SubjectGroup.SMOKES, 1, GeneralQuestScenario.QuestType.GLOBAL, GeneralQuestType.INVERSED));
    }

    public int getMainClueIdx() {
        //todo: replace in <GeneralQuestParams>. And transform via <SubjectParameters> + remake point which call this method.
        // Искомый элемент - всегда это число у самого первого квеста.
        return SOUGHT_SMOKED_ID;
    }

    public GeneralQuestParams generateScenario(SubjectParameters subjectsParams, int questIdx) {
        int multiplierRange = questIdx * MULTIPLIER_RANGE;
        List<GeneralQuestScenario> scenarios = new ArrayList<>(miniQuestParamsTemplate.size());
        GeneralQuestScenario prevScenario = null;
        for (GeneralQuestScenario generalQuestScenario : miniQuestParamsTemplate) {
            GeneralQuestScenario newScenario = new GeneralQuestScenario(
                    generalQuestScenario.subjectGroup,
                    subjectsParams.getParamsIdxs(generalQuestScenario.subjectGroup).get(generalQuestScenario.subjectIdx + multiplierRange),
                    generalQuestScenario.variantGroup,
                    subjectsParams.getParamsIdxs(generalQuestScenario.variantGroup).get(generalQuestScenario.rightVariantIdx + multiplierRange),
                    generalQuestScenario.questType,
                    generalQuestScenario.type);
            scenarios.add(newScenario);
            if (prevScenario != null) {
                prevScenario.setFollowingScenrio(newScenario);
            }

            prevScenario = newScenario;
        }

        List<GeneralQuestAnswer> answers = new ArrayList<>();
        answers.add(new GeneralQuestAnswer()
                .addParam(SubjectGroup.NATIONALITY, subjectsParams.getParamsIdxs(SubjectGroup.NATIONALITY).get(0 + multiplierRange))
                .addParam(SubjectGroup.DRINKS, subjectsParams.getParamsIdxs(SubjectGroup.DRINKS).get(1 + multiplierRange))
                .addParam(SubjectGroup.SMOKES, subjectsParams.getParamsIdxs(SubjectGroup.SMOKES).get(0 + multiplierRange)));
        answers.add(new GeneralQuestAnswer()
                .addParam(SubjectGroup.NATIONALITY, subjectsParams.getParamsIdxs(SubjectGroup.NATIONALITY).get(1 + multiplierRange))
                .addParam(SubjectGroup.DRINKS, subjectsParams.getParamsIdxs(SubjectGroup.DRINKS).get(0 + multiplierRange))
                .addParam(SubjectGroup.SMOKES, subjectsParams.getParamsIdxs(SubjectGroup.SMOKES).get(1 + multiplierRange)));
        answers.add(new GeneralQuestAnswer()
                .addParam(SubjectGroup.NATIONALITY, subjectsParams.getParamsIdxs(SubjectGroup.NATIONALITY).get(2 + multiplierRange))
                .addParam(SubjectGroup.DRINKS, subjectsParams.getParamsIdxs(SubjectGroup.DRINKS).get(2 + multiplierRange))
                .addParam(SubjectGroup.SMOKES, subjectsParams.getParamsIdxs(SubjectGroup.SMOKES).get(2 + multiplierRange)));

        return new GeneralQuestParams(scenarios, answers);
    }

}
