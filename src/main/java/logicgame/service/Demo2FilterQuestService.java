package logicgame.service;

import logicgame.model.SubjectGroup;
import logicgame.model.SubjectParameters;
import logicgame.model.generalquest.GeneralQuestParams;
import logicgame.model.generalquest.GeneralQuestScenario;
import logicgame.model.generalquest.GeneralQuestType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Demo2FilterQuestService {
    private static final List<GeneralQuestScenario> miniQuestParamsTemplate = new ArrayList<>();

    static {
        initScenarioTemplates();
    }

    private static void initScenarioTemplates() {
        // Backup
        /* 1) 1 и 4 не играли за одним столом.
           2) 3 и 1 играли за одним столом.
           3) 4 и 6 играли за одним столом.
           4) 2 играл в бридж. (стартовый вывод)
           5) 5 и 6 играли за одним столом. (стартовый вывод) */
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.NATIONALITY, 0, SubjectGroup.NATIONALITY, 3, GeneralQuestScenario.QuestType.FILTER, GeneralQuestType.INVERSED));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.NATIONALITY, 2, SubjectGroup.NATIONALITY, 0, GeneralQuestScenario.QuestType.FILTER, GeneralQuestType.STANDARD, Arrays.asList(1)));
        miniQuestParamsTemplate.add(new GeneralQuestScenario(SubjectGroup.NATIONALITY, 3, SubjectGroup.NATIONALITY, 5, GeneralQuestScenario.QuestType.FILTER, GeneralQuestType.STANDARD, Arrays.asList(4)));
    }

    public GeneralQuestParams generateScenario(SubjectParameters subjectsParams) {
        List<GeneralQuestScenario> scenarios = new ArrayList<>(miniQuestParamsTemplate.size());
        for (GeneralQuestScenario generalQuestScenario : miniQuestParamsTemplate) {
            scenarios.add(new GeneralQuestScenario(
                    generalQuestScenario.subjectGroup,
                    subjectsParams.getParamsIdxs(generalQuestScenario.subjectGroup).get(generalQuestScenario.subjectIdx),
                    generalQuestScenario.variantGroup,
                    subjectsParams.getParamsIdxs(generalQuestScenario.variantGroup).get(generalQuestScenario.rightVariantIdx),
                    generalQuestScenario.questType,
                    generalQuestScenario.type,
                    convertWithSubjectParameters(generalQuestScenario.excludedIdxs, generalQuestScenario.variantGroup, subjectsParams)));
        }

        scenarios.get(0).setFollowingScenrio(scenarios.get(2));

        return new GeneralQuestParams(scenarios,  new ArrayList<>());
    }

    private Collection<Integer> convertWithSubjectParameters(Collection<Integer> idxs, SubjectGroup variantGroup, SubjectParameters subjectsParams) {
        return idxs.stream()
                .map(idx -> subjectsParams.getParamsIdxs(variantGroup).get(idx))
                .collect(Collectors.toSet());
    }

}
