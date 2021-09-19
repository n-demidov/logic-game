package logicgame_1.model.generalquest;

import java.util.List;

public class GeneralQuestParams {

    private final List<GeneralQuestScenario> generalQuestScenarios;
    private final List<GeneralQuestAnswer> generalQuestAnswers;

    public GeneralQuestParams(List<GeneralQuestScenario> generalQuestScenarios, List<GeneralQuestAnswer> generalQuestAnswers) {
        this.generalQuestScenarios = generalQuestScenarios;
        this.generalQuestAnswers = generalQuestAnswers;
    }

    public List<GeneralQuestScenario> getGeneralQuestScenarios() {
        return generalQuestScenarios;
    }

    public List<GeneralQuestAnswer> getGeneralQuestAnswers() {
        return generalQuestAnswers;
    }
}
