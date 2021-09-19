package logicgame_1.service;

import logicgame_1.model.SubjectGroup;
import logicgame_1.model.SubjectParameters;
import logicgame_1.model.generalquest.GeneralQuestAnswer;
import logicgame_1.model.generalquest.GeneralQuestParams;
import logicgame_1.model.generalquest.GeneralQuestScenario;
import logicgame_1.model.miniquest.MiniQuest;
import logicgame_1.service.miniquest.MiniQuestGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GameService {
    private final SubjectsParametersGenerator subjectsParametersGenerator = new SubjectsParametersGenerator();
    private final Demo1GeneralQuestService demo1GeneralQuestService = new Demo1GeneralQuestService();
    private final MiniQuestGenerator miniQuestGenerator = new MiniQuestGenerator();
    private final Scanner sc = new Scanner(System.in);

    public void start(int subjectsNum) {
        SubjectParameters subjectsParams = subjectsParametersGenerator.generate(subjectsNum);
        Complexity complexity = new Complexity();

        System.out.println("Имеются 3 возможных подозреваемых: " + getSubjectNames(subjectsParams, SubjectGroup.NATIONALITY) + ".");
        System.out.println("На месте преступления нашли окурок от " + getSoughtSmokeName(subjectsParams) + ". Надо найти того, кто его оставил.");
        System.out.println("О подозреваемых известно, что в тот вечер все они пили разные напитки - " +
                getSubjectNames(subjectsParams, SubjectGroup.DRINKS) +
                ", - и курили разные марки сигарет - " +
                getSubjectNames(subjectsParams, SubjectGroup.SMOKES) +
                ".");

        GeneralQuestParams generalQuestParams = demo1GeneralQuestService.generateScenario(subjectsParams);
        List<GeneralQuestScenario> scenarios = generalQuestParams.getGeneralQuestScenarios();

        for (int gqpIdx = 0; gqpIdx < scenarios.size(); gqpIdx++) {
            GeneralQuestScenario generalQuestParam = scenarios.get(gqpIdx);

            MiniQuest miniQuest = miniQuestGenerator.generateStatements(
                    generalQuestParam, subjectsParams,
                    complexity.getStatementsNum(gqpIdx), complexity.getRepeatsLimit(gqpIdx), complexity.getTrueStatesmentsNum(gqpIdx));

            System.out.println("\nОпрос #" + (gqpIdx + 1) + ":");
            for (int i = 0; i < miniQuest.statements.size(); i++) {
                System.out.println("Опрашиваемый " + (i + 1) + ": \"" + miniQuest.statements.get(i) + "\".");
            }
            System.out.println(miniQuest.askQuestion);

            sc.nextLine();

            System.out.println("Answer is: " + getSubjectName(generalQuestParam.variantGroup, generalQuestParam.rightVariantIdx));
        }

        System.out.println("\nОтветы:");
        List<GeneralQuestAnswer> generalQuestAnswers = generalQuestParams.getGeneralQuestAnswers();
        for (int i = 0; i < subjectsNum; i++) {
            String answer = getAnswer(generalQuestAnswers.get(i).getAnswerBySubjectIdxs());
            System.out.println(answer);
        }
    }

    private String getSoughtSmokeName(SubjectParameters subjectsParams) {
        Integer paramIdx = subjectsParams.getParamsIdxs(SubjectGroup.SMOKES).get(demo1GeneralQuestService.getMainClueIdx());
        return ParamsDictionary.getSubjectParams(SubjectGroup.SMOKES)[paramIdx];
    }

    private String getSubjectNames(SubjectParameters subjectsParams, SubjectGroup subjectGroup) {
        List<Integer> paramsIdxs = new ArrayList<>(subjectsParams.getParamsIdxs(subjectGroup));
        Collections.shuffle(paramsIdxs);
        return paramsIdxs
                .stream()
                .map(i -> ParamsDictionary.getSubjectParams(subjectGroup)[i])
                .collect(Collectors.joining(", "));
    }

    private String getSubjectName(SubjectGroup subjectGroup, int paramIdx) {
        return ParamsDictionary.getSubjectParams(subjectGroup)[paramIdx];
    }

    private String getAnswer(int[] paramsIdxs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramsIdxs.length; i++) {
            sb.append(ParamsDictionary.getSubjectParams(SubjectGroup.by(i))[paramsIdxs[i]]);
            if (i <= paramsIdxs.length - 2) {
                sb.append(" = ");
            }
        }

        return sb.toString();
    }

}
