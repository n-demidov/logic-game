package logicgame.service;

import logicgame.model.SubjectGroup;

public class ParamsDictionary {

    private static final String[] paramNames = new String[3];
    private static final String[] verbs = new String[3];
    private static final String[] verbs2 = new String[3];
    private static final String[][] params = new String[3][16];

    static {
        paramNames[SubjectGroup.NATIONALITY.ordinal()] = "национальность";
        paramNames[SubjectGroup.DRINKS.ordinal()] = "напиток";
        paramNames[SubjectGroup.SMOKES.ordinal()] = "сигареты";

        verbs[SubjectGroup.NATIONALITY.ordinal()] = "играть с";
        verbs[SubjectGroup.DRINKS.ordinal()] = "пить";
        verbs[SubjectGroup.SMOKES.ordinal()] = "курить";

        verbs2[SubjectGroup.NATIONALITY.ordinal()] = "играл с";
        verbs2[SubjectGroup.DRINKS.ordinal()] = "пил";
        verbs2[SubjectGroup.SMOKES.ordinal()] = "курил";

        int i = 0;
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "норвежец";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "украинец";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "англичанин";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "испанец";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "японец";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "француз";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "мексиканец";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "швед";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "австралиец";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "аргентинец";
        params[SubjectGroup.NATIONALITY.ordinal()][i++] = "балиец";

        i = 0;
        params[SubjectGroup.DRINKS.ordinal()][i++] = "какао";
        params[SubjectGroup.DRINKS.ordinal()][i++] = "чай";
        params[SubjectGroup.DRINKS.ordinal()][i++] = "грог";
        params[SubjectGroup.DRINKS.ordinal()][i++] = "сок";
        params[SubjectGroup.DRINKS.ordinal()][i++] = "кофе";
        params[SubjectGroup.DRINKS.ordinal()][i++] = "бурбон";
        params[SubjectGroup.DRINKS.ordinal()][i++] = "виски";
        params[SubjectGroup.DRINKS.ordinal()][i++] = "саке";
        params[SubjectGroup.DRINKS.ordinal()][i++] = "пиво";

        i = 0;
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Kool";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Chesterfield";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Old Gold";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Lucky Strike";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Parliament";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Marlboro";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Pall Mall";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Dunhill";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Rothmans";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Winfield";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Philip Morris";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Camel";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Winston";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Newport";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Salem";
        params[SubjectGroup.SMOKES.ordinal()][i++] = "Tiparillo";
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
