package logicgame.service.miniquest;

import com.usefulsoft.terver.service.Randomizer;
import logicgame.model.SubjectGroup;
import logicgame.model.SubjectParameters;
import logicgame.model.generalquest.GeneralQuestScenario;
import logicgame.model.generalquest.GeneralQuestType;
import logicgame.model.miniquest.MiniQuest;
import logicgame.service.Complexity;
import logicgame.service.ParamsDictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class MiniQuestGenerator {
    private static final Randomizer randomizer = new Randomizer();
    private static final OnlyOneAnswerValidator onlyOneAnswerValidator = new OnlyOneAnswerValidator();
    private static final NoRepeatsValidator noRepeatsValidator = new NoRepeatsValidator();
    private static final NoContradictionsValidator noContradictionsValidator = new NoContradictionsValidator();

    public MiniQuest generateStatements(GeneralQuestScenario generalQuestScenario,
                                        SubjectParameters subjectsParams,
                                        int statementsNum,
                                        int repeatsLimit,
                                        boolean contradictionsAllow) {
        List<Condition> statements = generateValidStatements(generalQuestScenario, subjectsParams, statementsNum, repeatsLimit, contradictionsAllow);
        String subjectName = ParamsDictionary.getSubjectParams(generalQuestScenario.subjectGroup)[generalQuestScenario.subjectIdx];
        List<String> stringStatements = generateStringStatements(statements, subjectName, generalQuestScenario.type, generalQuestScenario);
        String askQuestion = getAskQuestion(subjectName, generalQuestScenario);

        return new MiniQuest(stringStatements, askQuestion);
    }

    private List<Condition> generateValidStatements(GeneralQuestScenario generalQuestScenario,
                                                    SubjectParameters subjectsParams,
                                                    int statementsNum,
                                                    int repeatsLimit,
                                                    boolean contradictionsEnabled) {
        List<Condition> statements;
        do {
            Collection<Integer> excludedVariantIdxs = getExcludedVariantIdx(generalQuestScenario);
            excludedVariantIdxs.addAll(generalQuestScenario.excludedIdxs);

            statements = generateFalseStatements(
                    subjectsParams, generalQuestScenario.variantGroup, statementsNum, generalQuestScenario.rightVariantIdx, excludedVariantIdxs, generalQuestScenario.type);
            randomlySetTrueStatement(statements);
        } while (!isValid(statements, generalQuestScenario, repeatsLimit, contradictionsEnabled));

        return statements;
    }

    private Collection<Integer> getExcludedVariantIdx(GeneralQuestScenario generalQuestScenario) {
        Collection<Integer> result = new HashSet<>();
        boolean equalsGroups = areGroupsEquals(generalQuestScenario);
        if (equalsGroups) {
            result.add(generalQuestScenario.subjectIdx);
        }
        return result;
    }

    private List<Condition> generateFalseStatements(SubjectParameters subjectsParams,
                                                    SubjectGroup variantGroup,
                                                    int statementsNum,
                                                    int rightVariantIdx,
                                                    Collection<Integer> excludedVariantIdxs,
                                                    GeneralQuestType type) {
        List<Condition> result = new ArrayList<>(statementsNum);
        List<Integer> variantIdxs = subjectsParams.getParamsIdxs(variantGroup);
        boolean generalQuestInversed = type == GeneralQuestType.INVERSED;

        // Add right variant
        result.add(new Condition(0, rightVariantIdx, !generalQuestInversed));

        // Add random variants
        for (int i = 1; i < statementsNum; i++) {
            int randomVariantIdx;
            do {
                randomVariantIdx = variantIdxs.get(randomizer.generateFromRange(0, variantIdxs.size() - 1));
            } while (excludedVariantIdxs.contains(randomVariantIdx));

            boolean inversed = randomVariantIdx == rightVariantIdx;
            if (generalQuestInversed) {
                inversed = !inversed;
            }
            result.add(new Condition(i, randomVariantIdx, inversed));
        }

        randomizer.shuffle(result);

        return result;
    }

    private void randomlySetTrueStatement(List<Condition> falseStatements) {
        int randomStatementIndex = randomizer.generateFromRange(0, falseStatements.size() - 1);
        Condition randomCondition = falseStatements.get(randomStatementIndex);
        randomCondition.inverse = !randomCondition.inverse;
    }

    private boolean isValid(List<Condition> statements,
                            GeneralQuestScenario generalQuestScenario,
                            int repeatsLimit,
                            boolean contradictionsEnabled) {
        if (repeatsLimit != Complexity.INFINITY_REPEATS_LIMIT &&
                !noRepeatsValidator.valid(statements, repeatsLimit)) {
            return false;
        }

        if (!noContradictionsValidator.valid(statements, contradictionsEnabled)) {
            return false;
        }

        return onlyOneAnswerValidator.valid(statements, generalQuestScenario.type == GeneralQuestType.INVERSED, generalQuestScenario.rightVariantIdx);
    }

    private List<String> generateStringStatements(List<Condition> statements, String subjectName,
                                                  GeneralQuestType miniQuestInversed, GeneralQuestScenario generalQuestScenario) {
        List<String> result = new ArrayList<>(statements.size());

        for (Condition statement : statements) {
            String stringStatement = getVariantFullSentence(generalQuestScenario, subjectName);

            if (statement.inverse) {
                stringStatement += " не";
            }

            if (miniQuestInversed == GeneralQuestType.INVERSED) {
                stringStatement += " мог " + ParamsDictionary.getSubjectVerb(generalQuestScenario.variantGroup) + " ";
            } else {
                stringStatement += " " + ParamsDictionary.getSubjectVerb2(generalQuestScenario.variantGroup) + " ";
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

    private String getAskQuestion(String subjectName, GeneralQuestScenario generalQuestScenario) {
        StringBuilder result = new StringBuilder();
        boolean equalsGroups = areGroupsEquals(generalQuestScenario);

        result.append("Установите ");
        if (equalsGroups) {
            result.append("кто ");
        } else {
            result.append("что ");
        }

        if (generalQuestScenario.type == GeneralQuestType.INVERSED) {
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
        result.append("? При условии, что только одно высказывание оказалось правдивым.");
        if (generalQuestScenario.type == GeneralQuestType.INVERSED) {
            if (equalsGroups) {
                result.append(" И он не мог ");
                result.append(ParamsDictionary.getSubjectVerb(generalQuestScenario.variantGroup));
                result.append(" только 1 человеком.");
            } else {
                result.append(" И он не мог ");
                result.append(ParamsDictionary.getSubjectVerb(generalQuestScenario.variantGroup));
                result.append(" ровно 1 вещь.");
            }
        } else {
            if (equalsGroups) {
                result.append(" И он играл только с одним человеком.");
            }
        }

        result.append("\n(Примечание: \"Мог\" - означает, что такая возможность была; и нет информации воспользовался ли он ей. \"Не мог\" - означает, что он однозначно этого не делал.)");
        return result.toString();
    }

    private boolean areGroupsEquals(GeneralQuestScenario generalQuestScenario) {
        return generalQuestScenario.subjectGroup == generalQuestScenario.variantGroup && generalQuestScenario.subjectGroup == SubjectGroup.NATIONALITY;
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
