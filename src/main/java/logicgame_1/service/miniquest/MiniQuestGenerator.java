package logicgame_1.service.miniquest;

import com.usefulsoft.terver.service.Randomizer;
import logicgame_1.model.SubjectGroup;
import logicgame_1.model.SubjectParameters;
import logicgame_1.model.generalquest.GeneralQuestScenario;
import logicgame_1.model.miniquest.MiniQuest;
import logicgame_1.service.Complexity;
import logicgame_1.service.ParamsDictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MiniQuestGenerator {
    private static final Randomizer randomizer = new Randomizer();
    private static final OnlyOneAnswerValidator onlyOneAnswerValidator = new OnlyOneAnswerValidator();
    private static final NoRepeatsValidator noRepeatsValidator = new NoRepeatsValidator();

    public MiniQuest generateStatements(GeneralQuestScenario generalQuestScenario,
                                        SubjectParameters subjectsParams,
                                        int statementsNum,
                                        int repeatsLimit,
                                        int trueStatementsNum) {
        List<Condition> statements = generateValidStatements(generalQuestScenario, subjectsParams, statementsNum, repeatsLimit, trueStatementsNum);
        String subjectName = ParamsDictionary.getSubjectParams(generalQuestScenario.subjectGroup)[generalQuestScenario.subjectIdx];
        List<String> stringStatements = generateStringStatements(statements, subjectName, generalQuestScenario.inverse, generalQuestScenario);
        String askQuestion = getAskQuestion(subjectName, generalQuestScenario, trueStatementsNum);

        return new MiniQuest(stringStatements, askQuestion);
    }

    private List<Condition> generateValidStatements(GeneralQuestScenario generalQuestScenario,
                                                    SubjectParameters subjectsParams,
                                                    int statementsNum,
                                                    int repeatsLimit, int trueStatementsNum) {
        List<Condition> statements;
        do {
            statements = generateFalseStatements(
                    subjectsParams, generalQuestScenario.variantGroup, statementsNum, generalQuestScenario.rightVariantIdx);
            randomlySetTrueStatement(statements, trueStatementsNum);
        } while (!isValid(statements, generalQuestScenario, repeatsLimit, trueStatementsNum));

        return statements;
    }

    private List<Condition> generateFalseStatements(SubjectParameters subjectsParams,
                                                    SubjectGroup variantGroup,
                                                    int statementsNum, int rightVariantIdx) {
        List<Condition> result = new ArrayList<>(statementsNum);
        List<Integer> variantIdxs = subjectsParams.getParamsIdxs(variantGroup);

        for (int i = 0; i < statementsNum; i++) {
            int randomVariantIdx = variantIdxs.get(randomizer.generateFromRange(0, variantIdxs.size() - 1));
            boolean inversed = randomVariantIdx == rightVariantIdx;
            result.add(new Condition(i, randomVariantIdx, inversed));
        }

        return result;
    }

//    private void randomlySetTrueStatement(List<Condition> falseStatements, int trueStatesmentsNum) {
//        int randomStatementIndex = randomizer.generateFromRange(0, falseStatements.size() - 1);
//        Condition randomCondition = falseStatements.get(randomStatementIndex);
//        randomCondition.inverse = !randomCondition.inverse;
//    }

    private void randomlySetTrueStatement(List<Condition> falseStatements, int trueStatesmentsNum) {
        Set<Integer> bookedStatements = new HashSet<>(trueStatesmentsNum);
        for (int i = 0; i < trueStatesmentsNum; i++) {
            int randomStatementIndex = randomizer.generateFromRange(0, falseStatements.size() - 1);
            Condition randomCondition = falseStatements.get(randomStatementIndex);
            if (bookedStatements.contains(randomCondition.id)) {
                continue;
            }
            randomCondition.inverse = !randomCondition.inverse;
            bookedStatements.add(randomCondition.id);
        }
    }

    private boolean isValid(List<Condition> statements, GeneralQuestScenario generalQuestScenario,
                            int repeatsLimit, int trueStatementsNum) {
        if (repeatsLimit != Complexity.INFINITY_REPEATS_LIMIT &&
                !noRepeatsValidator.valid(statements, repeatsLimit)) {
            return false;
        }

        return onlyOneAnswerValidator.valid(statements, generalQuestScenario.inverse, trueStatementsNum);
    }

    private List<String> generateStringStatements(List<Condition> statements, String subjectName,
                                                  boolean inverse, GeneralQuestScenario generalQuestScenario) {
        List<String> result = new ArrayList<>(statements.size());

        for (Condition statement : statements) {
            String stringStatement = getVariantFullSentence(generalQuestScenario, subjectName);

            if (inverse) {
                if (!statement.inverse) {
                    stringStatement += " не";
                }

                stringStatement += " мог " + ParamsDictionary.getSubjectVerb(generalQuestScenario.variantGroup) + " ";
            } else {
                if (statement.inverse) {
                    stringStatement += " не ";
                } else {
                    stringStatement += " ";
                }
                stringStatement += ParamsDictionary.getSubjectVerb2(generalQuestScenario.variantGroup) + " ";
            }
            stringStatement += ParamsDictionary.getSubjectParams(generalQuestScenario.variantGroup)[statement.subjectIdx];

            result.add(stringStatement);
        }

        return result;
    }

    private String getVariantFullSentence(GeneralQuestScenario generalQuestScenario, String subjectName) {
        if (generalQuestScenario.subjectGroup == SubjectGroup.NATIONALITY) {
            return subjectName;
        } else {
            return "Тот кто " + ParamsDictionary.getSubjectVerb2(generalQuestScenario.subjectGroup) + " " + subjectName;
        }
    }

    private String getAskQuestion(String subjectName, GeneralQuestScenario generalQuestScenario, int trueStatesmentsNum) {
        StringBuilder result = new StringBuilder();
        result.append("Установите что ");
        if (generalQuestScenario.inverse) {
            result.append("не ");
        }
        result.append(ParamsDictionary.getSubjectVerb2(generalQuestScenario.variantGroup));
        result.append(" ");

        if (generalQuestScenario.subjectGroup != SubjectGroup.NATIONALITY) {
            result.append("тот, кто ");
            result.append(ParamsDictionary.getSubjectVerb2(generalQuestScenario.subjectGroup));
            result.append(" ");
        }

        result.append(subjectName);
//        result.append("? При условии, что только одно высказывание оказалось правдивым.");
        result.append("? При условии, что только ");
        result.append(trueStatesmentsNum);
        result.append(" высказывание(ий) оказалось(ись) правдивым(и).");
        result.append("\n(Примечание: \"Мог\" - означает, что такая возможность была; и нет информации воспользовался ли он ей. \"Не мог\" - означает, что он однозначно этого не делал.)");
        return result.toString();
    }

    public static class Condition {
        final int id;
        final int subjectIdx;
        boolean inverse;

        public Condition(int id, int subjectIdx, boolean inverse) {
            this.id = id;
            this.subjectIdx = subjectIdx;
            this.inverse = inverse;
        }

        @Override
        public String toString() {
            return "Condition{" +
                    "idx=" + id +
                    ", subjectIdx=" + subjectIdx +
                    ", inverse=" + inverse +
                    '}';
        }
    }

}
