package edu.test

import edu.test.services.MessageService
import spock.lang.Specification

class UnCategorizedSpec extends Specification {

    def messageService = new MessageService()

    def 'Should not be run'() {
        expect: 'Should return the correct message'
        messageService.getMessage() == 'Hello World!'
    }

}
