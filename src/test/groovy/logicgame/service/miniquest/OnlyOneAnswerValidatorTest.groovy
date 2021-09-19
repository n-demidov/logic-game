package logicgame.service.miniquest

import spock.lang.Specification

class OnlyOneAnswerValidatorTest extends Specification {
    OnlyOneAnswerValidator miniQuestValidator = new OnlyOneAnswerValidator()

    def "should valid when rightVarIdx was specified"() {
        expect:
        apply(subjectIdx, inverse, false, rightVarIdx) == result

        where:
        subjectIdx       | inverse                              | rightVarIdx  | result
        [0, 1, 0, 1, 2]  | [false, false, true, false, true]    | 2            | true
        [0, 1, 0, 1, 2]  | [false, false, true, false, true]    | -1           | false
        [0, 1, 0, 1, 2]  | [false, false, true, false, true]    | 0            | false
        [0, 1, 0, 1, 2]  | [false, false, true, false, true]    | 1            | false
        [0, 1, 0, 1, 2]  | [false, false, true, false, true]    | 3            | false
    }

    def "should valid when rightVarIdx was specified; inversed"() {
        expect:
        apply(subjectIdx, inverse, true, rightVarIdx) == result

        where:
        subjectIdx       | inverse                             | rightVarIdx  | result
        [0, 0, 0, 1, 2]  | [false, true, true, true, false]    | 2            | true
        [0, 0, 0, 1, 2]  | [false, true, true, true, false]    | -1           | false
        [0, 0, 0, 1, 2]  | [false, true, true, true, false]    | 0            | false
        [0, 0, 0, 1, 2]  | [false, true, true, true, false]    | 1            | false
        [0, 0, 0, 1, 2]  | [false, true, true, true, false]    | 3            | false
    }

    def "should valid when standard"() {
        expect:
        apply(subjectIdx, inverse, false, rightVarIdx) == result

        where:
        subjectIdx       | inverse                              | rightVarIdx  | result
        [0, 0, 1, 1]     | [false, false, false, false]         | -1           | false  // Contradictions (противоречия)
        [0, 1, 2]        | [false, false, false]                | -1           | false  // Several correct answers (should be only 1)
        [0, 1, 2]        | [true, false, false]                 | -1           | false  // No one correct answers + Several correct answers
        [0, 1]           | [false, true]                        | -1           | false  // Several correct answers in 1 condition
        [0, 0, 0, 1, 1]  | [false, true, true, true, true]      | -1           | false  // Contradictions + more than 1 correct answers

        [0, 1, 1]        | [false, false, false]                | 0            | true
        [0, 1, 0]        | [true, true, true]                   | 0            | true
        [0, 1, 0, 2, 2]  | [true, true, true, false, false]     | 0            | true
        [0, 0, 1, 2]     | [false, true, false, false]          | 0            | true
        [0, 1, 2, 1, 2]  | [false, false, false, false, false]  | 0            | true
        [0, 1, 2, 1, 2]  | [true, false, false, true, false]    | 0            | true  // Cause only 1 answer is right
        [0, 1, 0, 1, 2]  | [false, false, true, false, true]    | 2            | true
        [0, 1, 0, 2, 2]  | [true, true, false, false, false]    | 1            | true
    }

    def "should valid when inversed"() {
        expect:
        apply(subjectIdx, inverse, true, rightVarIdx) == result

        where:
        subjectIdx       | inverse                             | rightVarIdx  | result
        [0, 0, 1, 1]     | [false, false, false, false]        | 0            | false  // Contradictions (противоречия)
        [0, 1]           | [false, false]                      | 0            | false  // Several correct answers (should be only 1)
        [0, 1, 2]        | [true, false, false]                | 0            | false  // ...
        [0, 1, 2]        | [false, true, true]                 | 0            | false  // No one correct answers + Several correct answers in 1 condition
        [0, 0, 1]        | [true, true, false]                 | 0            | false  // Contradictions + No one correct answer
        [0, 0, 1, 1, 2]  | [true, true, true, true, false]     | 0            | false  // Contradictions + No one correct answer
        [0, 0, 0]        | [false, true, true]                 | 0            | false
        [0, 0, 1]        | [true, true, false]                 | 0            | false
        [0, 0, 1, 1, 2]  | [true, true, true, true, false]     | 0            | false
        [0, 0, 0, 1, 1]  | [false, true, true, true, true]     | 0            | false  // Contradictions + No one correct answer

        [0, 1, 1]        | [true, true, true]                  | 0            | true
        [0, 1, 1]        | [false, false, true]                | 0            | true
        [0, 1, 1]        | [true, false, true]                 | 1            | true
        [0, 0, 1, 2]     | [true, false, true, true]           | 0            | true
        [0, 1, 1, 2, 2]  | [true, true, true, true, true]      | 0            | true
        [0, 0, 0, 1, 2]  | [false, true, true, true, false]    | 2            | true
        [0, 0, 1, 1, 2]  | [false, true, false, false, true]   | 1            | true
        [0, 1, 0, 2, 0]  | [false, true, false, false, false]  | 0            | true
    }

    boolean apply(List<Integer> subjectIdx, List<Boolean> inverse, questionInversed, int rightVariantIdx) {
        List<MiniQuestGenerator.Condition> conditions = new ArrayList<>()
        for (int i = 0; i < subjectIdx.size(); i++) {
            conditions.add(new MiniQuestGenerator.Condition(i, subjectIdx.get(i), inverse.get(i)))
        }

        return miniQuestValidator.valid(conditions, questionInversed, rightVariantIdx)
    }

}
