package logicgame_1.service.miniquest

import spock.lang.Specification

class OnlyOneAnswerValidatorTest extends Specification {
    OnlyOneAnswerValidator miniQuestValidator = new OnlyOneAnswerValidator()

    def "should valid when standard"() {
        expect:
        apply(subjectIdx, inverse, false) == result

        where:
        subjectIdx       | inverse                              | result
        [0, 0, 1, 1]     | [false, false, false, false]         | false  // Contradictions (противоречия)
        [0, 1, 2]        | [false, false, false]                | false  // Several correct answers (should be only 1)
        [0, 1, 2]        | [true, false, false]                 | false  // No one correct answers + Several correct answers
        [0, 1]           | [false, true]                        | false  // Several correct answers in 1 condition
        [0, 1, 2, 1, 2]  | [true, false, false, true, false]    | false  // Complex
        [0, 1, 0, 1, 2]  | [false, false, true, false, true]    | false
        [0, 1, 0, 2, 2]  | [true, true, false, false, false]    | false
        [0, 1, 1]        | [false, false, false]                | true
        [0, 1, 0]        | [true, true, true]                   | true
        [0, 1, 0, 2, 2]  | [true, true, true, false, false]     | true
        [0, 0, 1, 2]     | [false, true, false, false]          | true
        [0, 1, 2, 1, 2]  | [false, false, false, false, false]  | true
    }

    def "should valid when inversed"() {
        expect:
        apply(subjectIdx, inverse, true) == result

        where:
        subjectIdx       | inverse                             | result
        [0, 0, 1, 1]     | [false, false, false, false]        | false  // Contradictions (противоречия)
        [0, 1]           | [false, false]                      | false  // Several correct answers (should be only 1)
        [0, 1, 2]        | [true, false, false]                | false  // ...
        [0, 1, 2]        | [false, true, true]                 | false  // No one correct answers + Several correct answers in 1 condition
        [0, 0, 1, 1, 2]  | [false, true, false, false, true]   | false
        [0, 1, 0, 2, 0]  | [false, true, false, false, false]  | false
        [0, 1, 0]        | [false, false, true]                | false
        [0, 0, 0]        | [false, true, true]                 | false
        [0, 1, 1]        | [true, true, true]                  | true
        [0, 1, 1, 2, 2]  | [true, true, true, true, true]      | true
        [0, 0, 0, 1, 2]  | [false, true, true, true, false]    | true
    }

    boolean apply(List<Integer> subjectIdx, List<Boolean> inverse, questionInversed) {
        List<MiniQuestGenerator.Condition> conditions = new ArrayList<>()
        for (int i = 0; i < subjectIdx.size(); i++) {
            conditions.add(new MiniQuestGenerator.Condition(i, subjectIdx.get(i), inverse.get(i)))
        }

        return miniQuestValidator.valid(conditions, questionInversed, 1)
    }

}
