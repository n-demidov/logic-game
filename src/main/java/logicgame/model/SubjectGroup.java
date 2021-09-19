package logicgame.model;

public enum SubjectGroup {
    NATIONALITY(true),
    DRINKS(false),
    SMOKES(false);

    private final boolean alive;

    SubjectGroup(boolean alive) {
        this.alive = alive;
    }

    public static SubjectGroup by(int idx) {
        for (SubjectGroup value : SubjectGroup.values()) {
            if (value.ordinal() == idx) {
                return value;
            }
        }
        throw new IllegalStateException("Can't find SubjectGroup by idx=" + idx);
    }

    public boolean isAlive() {
        return alive;
    }

}
