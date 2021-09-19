package logicgame_1.model;

public enum SubjectGroup {
    NATIONALITY, DRINKS, SMOKES;

    public static SubjectGroup by(int idx) {
        for (SubjectGroup value : SubjectGroup.values()) {
            if (value.ordinal() == idx) {
                return value;
            }
        }
        throw new IllegalStateException("Can't find SubjectGroup by idx=" + idx);
    }
}
