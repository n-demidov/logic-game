package logicgame.model;

import logicgame.model.generalquest.GeneralQuestScenario;

public class Hook {
    public final int key;
    public final Type type;
    public GeneralQuestScenario generalQuestScenario;
    public final String ask;
    public String info;

    public Hook(int key, Type type, GeneralQuestScenario generalQuestScenario, String ask) {
        this.key = key;
        this.type = type;
        this.generalQuestScenario = generalQuestScenario;
        this.ask = ask;
    }

    public Hook(int key, Type type, String ask, String info) {
        this.key = key;
        this.type = type;
        this.ask = ask;
        this.info = info;
    }

    public enum Type {
        QUEST,
        INFO
    }
}
