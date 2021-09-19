package edu.test

import org.junit.experimental.categories.Category
import spock.lang.Specification

@Category(UnitTest.class)
class FirstSpecification extends Specification {

    def "one plus one should equal two"() {
        expect:
        1 + 1 == 3
    }

}
