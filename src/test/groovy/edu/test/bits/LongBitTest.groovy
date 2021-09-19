package edu.test.bits

import spock.lang.Specification

class LongBitTest extends Specification {

    def "should write and read correctly"() {
        setup:
        LongBit longBit = new LongBit()

        when:
        longBit.setRight(169)

        then:
        longBit.getRight() == 169
        longBit.getLeft() == 0

        when:
        longBit.setLeft(999)

        then:
        longBit.getRight() == 169
        longBit.getLeft() == 999

        when:
        longBit.setRight(13)

        then:
        longBit.getRight() == 13
        longBit.getLeft() == 999

        when:
        longBit.setRight(777)

        then:
        longBit.getRight() == 777
        longBit.getLeft() == 999

        when:
        longBit.setLeft(9)

        then:
        longBit.getRight() == 777
        longBit.getLeft() == 9

        when:
        longBit.setLeft(3333)

        then:
        longBit.getRight() == 777
        longBit.getLeft() == 3333
    }

}
