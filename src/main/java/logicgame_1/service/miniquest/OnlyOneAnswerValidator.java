package logicgame_1.service.miniquest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlyOneAnswerValidator {

    public boolean valid(List<MiniQuestGenerator.Condition> statements, boolean questionInversed, int trueStatementsNum) {
        int rightAnswers = 0;
        for (MiniQuestGenerator.Condition statement : statements) {
            rightAnswers += countRightAnswers(statement, statements, questionInversed, trueStatementsNum);
        }

        return rightAnswers == 1;
    }

    private int countRightAnswers(MiniQuestGenerator.Condition checkingStatement,
                                  List<MiniQuestGenerator.Condition> statements,
                                  boolean questionInversed, int trueStatementsNum) {
        Map<Integer, Boolean> rightAnswersBySubject = new HashMap<>();
        Map<Integer, Boolean> contradictionsBySubject = new HashMap<>();

        for (MiniQuestGenerator.Condition statement : statements) {
            if (statement.id == checkingStatement.id) {
                if (checkingStatement.inverse == questionInversed) {
                    rightAnswersBySubject.put(checkingStatement.subjectIdx, true);
                }
                continue;
            }

            if (statement.inverse != questionInversed) {
                rightAnswersBySubject.put(statement.subjectIdx, true);
            }

            // can drink milk = can drink milk; can't drink milk = can't drink milk
            if (statement.subjectIdx == checkingStatement.subjectIdx &&
                    statement.inverse == checkingStatement.inverse) {
                return 0;
            }

            if (statement.subjectIdx != checkingStatement.subjectIdx &&
                    areThereContradictions(contradictionsBySubject, statement)) {
                return 0;
            }
        }

        if (rightAnswersBySubject.size() >= trueStatementsNum) {
            return rightAnswersBySubject.size();
        } else {
            return 0;
        }
    }

//    private int countRightAnswers(MiniQuestGenerator.Condition checkingStatement,
//                                  List<MiniQuestGenerator.Condition> statements,
//                                  boolean questionInversed, int trueStatementsNum) {
//        Map<Integer, Boolean> rightAnswersBySubject = new HashMap<>();
//        Map<Integer, Boolean> contradictionsBySubject = new HashMap<>();
//
//        for (MiniQuestGenerator.Condition statement : statements) {
//            if (statement.id == checkingStatement.id) {
//                if (checkingStatement.inverse == questionInversed) {
//                    rightAnswersBySubject.put(checkingStatement.subjectIdx, true);
//                }
//                continue;
//            }
//
//            if (statement.inverse != questionInversed) {
//                rightAnswersBySubject.put(statement.subjectIdx, true);
//            }
//
//            // can drink milk = can drink milk; can't drink milk = can't drink milk
//            if (statement.subjectIdx == checkingStatement.subjectIdx &&
//                    statement.inverse == checkingStatement.inverse) {
//                return 0;
//            }
//
//            if (statement.subjectIdx != checkingStatement.subjectIdx &&
//                    areThereContradictions(contradictionsBySubject, statement)) {
//                return 0;
//            }
//        }
//
//        if (rightAnswersBySubject.size() >= trueStatementsNum) {
//            return rightAnswersBySubject.size();
//        } else {
//            return 0;
//        }
//    }

    private boolean areThereContradictions(Map<Integer, Boolean> contradictionsBySubject, MiniQuestGenerator.Condition statement) {
        Boolean contradiction = contradictionsBySubject.get(statement.subjectIdx);
        if (contradiction != null && contradiction != statement.inverse) {
            return true;
        }
        contradictionsBySubject.put(statement.subjectIdx, statement.inverse);
        return false;
    }

}
