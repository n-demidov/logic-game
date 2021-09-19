package logicgame_1.model.miniquest;

import java.util.List;

public class MiniQuest {
    public final List<String> statements;
    public final String askQuestion;

    public MiniQuest(List<String> statements, String askQuestion) {
        this.statements = statements;
        this.askQuestion = askQuestion;
    }
}
