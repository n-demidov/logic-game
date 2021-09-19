package logicgame.service.miniquest

import spock.lang.Specification

class NoRepeatsValidatorTest extends Specification {

    NoRepeatsValidator noRepeatsValidator = new NoRepeatsValidator()

    def "should valid repeats correctly"() {
        expect:
        apply(subjectIdx, inverse, repeatsLimit) == result

        where:
        subjectIdx       | inverse                            | repeatsLimit   | result
        [0, 0]           | [false, false]                     | 0              | false
        [0, 1, 0]        | [true, true, true]                 | 0              | false
        [0, 0, 1, 1]     | [false, false, true, true]         | 0              | false
        [0, 1, 2, 1, 2]  | [true, true, false, true, true]    | 0              | false
        [0, 1, 0, 1, 2]  | [false, false, true, false, true]  | 0              | false
        [0, 1, 0, 2, 2]  | [true, true, false, false, false]  | 0              | false
        [0, 1, 0, 2, 2]  | [true, true, true, false, true]    | 0              | false
        [0, 1, 0, 2, 0]  | [true, true, false, false, true]   | 0              | false
        [0, 1, 0, 2, 0]  | [true, true, false, false, false]  | 0              | false
        [0, 1, 2]        | [false, false, false]              | 0              | true
        [0, 1, 2]        | [true, false, false]               | 0              | true
        [0, 1, 2]        | [true, true, true]                 | 0              | true
        [0, 0, 1, 1]     | [false, true, true, false]         | 0              | true
        [0, 0, 1, 2]     | [false, true, false, false]        | 0              | true
        [0, 1, 2, 1, 2]  | [true, false, true, true, false]   | 0              | true

        [0, 0, 0]        | [false, false, false]              | 0              | false
        [0, 0, 0]        | [false, false, false]              | 1              | false
        [0, 0, 0]        | [false, false, false]              | 2              | false
        [0, 0, 0]        | [false, false, false]              | 3              | true
        [0, 0, 0]        | [false, false, false]              | 4              | true
        [0, 0, 0]        | [false, false, true]               | 2              | true
        [0, 0, 0, 1]     | [false, false, true, true]         | 2              | true
        [0, 0, 0, 1]     | [false, false, false, false]       | 3              | true

        [0, 0, 1, 1]     | [false, false, false, false]       | 3              | false
        [0, 0, 1, 1]     | [false, false, false, false]       | 4              | true

        [0, 0, 1, 1]     | [false, false, true, false]        | 0              | false
        [0, 0, 1, 1]     | [false, false, true, false]        | 1              | false
        [0, 0, 1, 1]     | [false, false, true, false]        | 2              | true
        [0, 0, 1, 1]     | [false, false, true, false]        | 3              | true
        [0, 0, 1, 1, 2]  | [false, false, true, false, false] | 3              | true

        [0, 1, 1, 2, 0]  | [false, false, false, false, true] | 0              | false
        [0, 1, 1, 2, 0]  | [false, false, false, false, true] | 1              | false
        [0, 1, 1, 2, 0]  | [false, false, false, false, true] | 2              | true
        [0, 1, 1, 2, 0]  | [false, false, false, false, true] | 3              | true
    }

    boolean apply(List<Integer> subjectIdx, List<Boolean> inverse, int repeatsLimit) {
        List<MiniQuestGenerator.Condition> conditions = new ArrayList<>()
        for (int i = 0; i < subjectIdx.size(); i++) {
            conditions.add(new MiniQuestGenerator.Condition(i, subjectIdx.get(i), inverse.get(i)))
        }

        return noRepeatsValidator.valid(conditions, repeatsLimit)
    }

}
