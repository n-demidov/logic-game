package logicgame.service.miniquest

import spock.lang.Specification

class NoContradictionsValidatorTest extends Specification {

    NoContradictionsValidator validator = new NoContradictionsValidator()

    def "should valid correctly"() {
        expect:
        apply(subjectIdx, inverse, contrad) == result

        where:
        subjectIdx       | inverse                            | contrad   | result
        [0, 0]           | [false, false]                     | true      | true
        [0, 0, 1, 2]     | [false, false, false, false]       | true      | true
        [0, 0, 1, 2]     | [true, true, true, true]           | true      | true
        [0, 0, 1, 2]     | [true, false, true, true]          | true      | true

        [0, 0, 1, 2]     | [false, false, false, false]       | false     | true
        [0, 0, 1, 2]     | [true, true, true, true]           | false     | true
        [0, 0, 1, 2, 2]  | [true, true, false, false, false]  | false     | true
        [0, 0, 0, 2, 2]  | [true, true, true, false, false]   | false     | true

        [0, 0, 1, 2]     | [true, false, true, true]          | false     | false
        [0, 0, 1, 2]     | [false, true, false, false]        | false     | false
        [0, 0, 1, 2, 2]  | [true, true, false, false, true]   | false     | false
        [0, 0, 0, 2, 2]  | [true, true, false, false, false]  | false     | false
    }

    boolean apply(List<Integer> subjectIdx, List<Boolean> inverse, boolean contrad) {
        List<MiniQuestGenerator.Condition> conditions = new ArrayList<>()
        for (int i = 0; i < subjectIdx.size(); i++) {
            conditions.add(new MiniQuestGenerator.Condition(i, subjectIdx.get(i), inverse.get(i)))
        }

        return validator.valid(conditions, contrad)
    }

}
