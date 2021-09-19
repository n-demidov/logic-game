package logicgame.service.miniquest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OnlyOneAnswerValidator {
    private static final int RIGHT_PHRASES_NUM = 1;

    public boolean valid(List<MiniQuestGenerator.Condition> statements, boolean questionInversed, int rightVariantIdx) {
        int rightAnswersNum = 1;
        Set<Integer> rightPhrases = new HashSet<>();
        for (MiniQuestGenerator.Condition statement : statements) {
            Result result = countRightAnswers(statement, statements, questionInversed, rightAnswersNum);

            if (result.valid) {
                rightPhrases.add(result.subjectIdx);
            }
        }

        return rightPhrases.size() == RIGHT_PHRASES_NUM && rightPhrases.contains(rightVariantIdx);
    }

    private Result countRightAnswers(MiniQuestGenerator.Condition checkingStatement,
                                      List<MiniQuestGenerator.Condition> statements,
                                      boolean questionInversed,
                                      int rightAnswersNum) {
        Set<Integer> rightAnswersBySubject = new HashSet<>();
        Map<Integer, Boolean> contradictionsBySubject = new HashMap<>();

        for (MiniQuestGenerator.Condition statement : statements) {
            if (statement.id == checkingStatement.id) {
                if (checkingStatement.inverse == questionInversed) {
                    rightAnswersBySubject.add(checkingStatement.subjectIdx);
                }
                continue;
            }

            if (statement.inverse != questionInversed) {
                rightAnswersBySubject.add(statement.subjectIdx);
            }

            // can drink milk = can drink milk; can't drink milk = can't drink milk
            if (statement.subjectIdx == checkingStatement.subjectIdx &&
                    statement.inverse == checkingStatement.inverse) {
                return Result.ERRORED_RESULT;
            }

            if (statement.subjectIdx != checkingStatement.subjectIdx &&
                    areThereContradictions(contradictionsBySubject, statement)) {
                return Result.ERRORED_RESULT;
            }
            contradictionsBySubject.put(statement.subjectIdx, statement.inverse);
        }

        if (rightAnswersBySubject.size() == rightAnswersNum) {
            return new Result(true, rightAnswersBySubject.iterator().next());
        } else {
            return Result.ERRORED_RESULT;
        }

//        return rightAnswersBySubject.size() == rightAnswersNum;  //todo: Можно скопировать эту проверку еще раз в цикл для оптимизации по скорости.
    }

    private boolean areThereContradictions(Map<Integer, Boolean> contradictionsBySubject, MiniQuestGenerator.Condition statement) {
        Boolean contradiction = contradictionsBySubject.get(statement.subjectIdx);
        return contradiction != null && contradiction != statement.inverse;
    }

    private static class Result {
        static Result ERRORED_RESULT = new Result(false, -1);
        boolean valid;
        int subjectIdx = -1;

        public Result(boolean valid, int subjectIdx) {
            this.valid = valid;
            this.subjectIdx = subjectIdx;
        }
    }

}
