package staxcxftest

import spock.lang.Specification
import util.handlers.XmlStaxHandler
import util.handlers.XmlStaxHandlerToString

/**
 */
class StaxCxfSpec extends Specification {

    def "test that stax can parse a document correctly"() {
        given:
        File testFile = new File("TestFile.xml")
        XmlStaxHandler staxHandler = new XmlStaxHandler(testFile)

        when:
        staxHandler.testParse()

        then:
        notThrown Exception
    }

    def "test that stax fails when using the toString on the xmlEvent"() {
        given:
        File testFile = new File("TestFile.xml")
        XmlStaxHandlerToString staxHandler = new XmlStaxHandlerToString(testFile)

        when:
        staxHandler.testParse()

        then:
        thrown Exception
    }
}
