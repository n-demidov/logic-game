package logicgame_1.service;

import logicgame_1.model.SubjectGroup;
import logicgame_1.model.SubjectParameters;
import logicgame_1.model.generalquest.GeneralQuestAnswer;
import logicgame_1.model.generalquest.GeneralQuestParams;
import logicgame_1.model.generalquest.GeneralQuestScenario;

import java.util.ArrayList;
import java.util.List;

public class Demo1GeneralQuestService {
    private static final int SOUGHT_SMOKED_ID = 1;

    private final List<GeneralQuestScenario> miniQuestParamsTemplate = new ArrayList<>();

    public Demo1GeneralQuestService() {
        initScenarioTemplates();
    }

    public int getMainClueIdx() {
        //todo: replace in <GeneralQuestParams>. And transform via <SubjectParameters> + remake point which call this method.
        return SOUGHT_SMOKED_ID;
    }

    public GeneralQuestParams generateScenario(SubjectParameters subjectsParams) {
        List<GeneralQuestScenario> scenarios = new ArrayList<>(miniQuestParamsTemplate.size());
        for (GeneralQuestScenario generalQuestScenario : miniQuestParamsTemplate) {
            scenarios.add(new GeneralQuestScenario(
                    generalQuestScenario.subjectGroup,
                    subjectsParams.getParamsIdxs(generalQuestScenario.subjectGroup).get(generalQuestScenario.subjectIdx),
                    generalQuestScenario.variantGroup,
                    subjectsParams.getParamsIdxs(generalQuestScenario.variantGroup).get(generalQuestScenario.rightVariantIdx),
                    generalQuestScenario.inverse));
        }

        List<GeneralQuestAnswer> answers = new ArrayList<>();

        answers.add(new GeneralQuestAnswer()
                .addParam(SubjectGroup.NATIONALITY, subjectsParams.getParamsIdxs(SubjectGroup.NATIONALITY).get(0))
                .addParam(SubjectGroup.DRINKS, subjectsParams.getParamsIdxs(SubjectGroup.DRINKS).get(1))
                .addParam(SubjectGroup.SMOKES, subjectsParams.getParamsIdxs(SubjectGroup.SMOKES).get(0)));
        answers.add(new GeneralQuestAnswer()
                .addParam(SubjectGroup.NATIONALITY, subjectsParams.getParamsIdxs(SubjectGroup.NATIONALITY).get(1))
                .addParam(SubjectGroup.DRINKS, subjectsParams.getParamsIdxs(SubjectGroup.DRINKS).get(0))
                .addParam(SubjectGroup.SMOKES, subjectsParams.getParamsIdxs(SubjectGroup.SMOKES).get(1)));
        answers.add(new GeneralQuestAnswer()
                .addParam(SubjectGroup.NATIONALITY, subjectsParams.getParamsIdxs(SubjectGroup.NATIONALITY).get(2))
                .addParam(SubjectGroup.DRINKS, subjectsParams.getParamsIdxs(SubjectGroup.DRINKS).get(2))
                .addParam(SubjectGroup.SMOKES, subjectsParams.getParamsIdxs(SubjectGroup.SMOKES).get(2)));

        return new GeneralQuestParams(scenarios, answers);
    }

    private void initScenarioTemplates() {
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.NATIONALITY, 0, SubjectGroup.DRINKS, 0, true));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.NATIONALITY, 2, SubjectGroup.DRINKS, 1, true));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.SMOKES, 2, SubjectGroup.DRINKS, 0, true));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.SMOKES, 0, SubjectGroup.DRINKS, 1, false));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.NATIONALITY, 2, SubjectGroup.SMOKES, 1, true));
    }

}
