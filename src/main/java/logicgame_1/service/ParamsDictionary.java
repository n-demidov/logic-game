package logicgame_1.service;

import logicgame_1.model.SubjectGroup;

public class ParamsDictionary {

    private static final String[] paramNames = new String[3];
    private static final String[] verbs = new String[3];
    private static final String[] verbs2 = new String[3];
    private static final String[][] params = new String[3][8];

    static {
        paramNames[SubjectGroup.NATIONALITY.ordinal()] = "национальность";
        paramNames[SubjectGroup.DRINKS.ordinal()] = "напиток";
        paramNames[SubjectGroup.SMOKES.ordinal()] = "сигареты";

        verbs[SubjectGroup.NATIONALITY.ordinal()] = "<тест77>";
        verbs[SubjectGroup.DRINKS.ordinal()] = "пить";
        verbs[SubjectGroup.SMOKES.ordinal()] = "курить";

        verbs2[SubjectGroup.NATIONALITY.ordinal()] = "<тест77>";
        verbs2[SubjectGroup.DRINKS.ordinal()] = "пил";
        verbs2[SubjectGroup.SMOKES.ordinal()] = "курил";

        params[SubjectGroup.NATIONALITY.ordinal()][0] = "норвежец";
        params[SubjectGroup.NATIONALITY.ordinal()][1] = "украинец";
        params[SubjectGroup.NATIONALITY.ordinal()][2] = "англичанин";
        params[SubjectGroup.NATIONALITY.ordinal()][3] = "испанец";
        params[SubjectGroup.NATIONALITY.ordinal()][4] = "японец";
        params[SubjectGroup.NATIONALITY.ordinal()][5] = "француз";
        params[SubjectGroup.NATIONALITY.ordinal()][6] = "мексиканец";
        params[SubjectGroup.NATIONALITY.ordinal()][7] = "швед";

        params[SubjectGroup.DRINKS.ordinal()][0] = "какао";
        params[SubjectGroup.DRINKS.ordinal()][1] = "чай";
        params[SubjectGroup.DRINKS.ordinal()][2] = "молоко";
        params[SubjectGroup.DRINKS.ordinal()][3] = "сок";
        params[SubjectGroup.DRINKS.ordinal()][4] = "кофе";
        params[SubjectGroup.DRINKS.ordinal()][5] = "бурбон";
        params[SubjectGroup.DRINKS.ordinal()][6] = "виски";
        params[SubjectGroup.DRINKS.ordinal()][7] = "саке";

        params[SubjectGroup.SMOKES.ordinal()][0] = "Kool";
        params[SubjectGroup.SMOKES.ordinal()][1] = "Chesterfield";
        params[SubjectGroup.SMOKES.ordinal()][2] = "Old Gold";
        params[SubjectGroup.SMOKES.ordinal()][3] = "Lucky Strike";
        params[SubjectGroup.SMOKES.ordinal()][4] = "Parliament";
    }

    public static String[] getSubjectParams(SubjectGroup subjectGroup) {
        return params[subjectGroup.ordinal()];
    }

    public static String getSubjectVerb(SubjectGroup subjectGroup) {
        return verbs[subjectGroup.ordinal()];
    }

    public static String getSubjectVerb2(SubjectGroup subjectGroup) {
        return verbs2[subjectGroup.ordinal()];
    }

}
