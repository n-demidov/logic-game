package logicgame.model.miniquest;

import logicgame.model.SubjectGroup;

import java.util.List;

public class MiniQuest {
    public final List<String> statements;
    public final String askQuestion;
    public String answer;
//    public SubjectGroup answerSubjectGroup;  //todo: delete it?
//    public int answerSubjectIdx;             //todo: delete it?

    public MiniQuest(List<String> statements, String askQuestion) {
        this.statements = statements;
        this.askQuestion = askQuestion;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

//    public void setAnswerData(SubjectGroup answerSubjectGroup, int answerSubjectIdx) {
//        this.answerSubjectGroup = answerSubjectGroup;
//        this.answerSubjectIdx = answerSubjectIdx;
//    }



//    public final Type type;
//    public List<String> statements;
//    public String askQuestion;
//    public String info;
//    public String answer;
//    public SubjectGroup answerSubjectGroup;
//    public int answerSubjectIdx;
//
//    public MiniQuest(Type type, List<String> statements, String askQuestion) {
//        this.type = type;
//        this.statements = statements;
//        this.askQuestion = askQuestion;
//    }
//
//    public MiniQuest(Type type, String info) {
//        this.type = type;
//        this.info = info;
//    }
//
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }
//
//    public void setAnswerData(SubjectGroup answerSubjectGroup, int answerSubjectIdx) {
//        this.answerSubjectGroup = answerSubjectGroup;
//        this.answerSubjectIdx = answerSubjectIdx;
//    }
//
//    public enum Type {
//        QUEST,
//        INFO
//    }
}
