package logicgame.service;

import logicgame.model.SubjectParameters;
import logicgame.model.generalquest.GeneralQuestScenario;
import logicgame.model.miniquest.MiniQuest;
import logicgame.service.miniquest.MiniQuestGenerator;

import java.util.List;

public class ComplexityValidator {
    private static final int MAX_COMPLEXITY_ITERATIONS = Integer.MAX_VALUE;

    public boolean isValid(SubjectParameters subjectsParams,
                           Complexity complexity,
                           List<GeneralQuestScenario> scenarios,
                           MiniQuestGenerator miniQuestGenerator) {
        System.out.println("[ComplexityValidator] Start validation...");

        int counter = 1;
        for (GeneralQuestScenario scenario : scenarios) {
            System.out.println(String.format("%d) %s", counter, scenario));

            for (int iC = 0; iC < MAX_COMPLEXITY_ITERATIONS; iC++) {
                int complexityIdx = complexity.normalizeComplexity(iC);
                if (complexityIdx != iC) {
                    break;
                }

                String log = String.format("  complexityIdx=%s", complexityIdx);
                System.out.println(log);

                createMiniQuest(subjectsParams, scenario, complexityIdx, complexity, miniQuestGenerator);
            }
            counter++;
        }

        System.out.println("[ComplexityValidator] validation successfully passed");
        return true;
    }

    private MiniQuest createMiniQuest(SubjectParameters subjectsParams, GeneralQuestScenario scenario, int complexityCounter, Complexity complexity, MiniQuestGenerator miniQuestGenerator) {
        return miniQuestGenerator.generateStatements(
                scenario, subjectsParams,
                complexity.getStatementsNum(complexityCounter),
                complexity.getRepeatsLimit(complexityCounter),
                complexity.getContradictionsAllow(complexityCounter));
    }

}
