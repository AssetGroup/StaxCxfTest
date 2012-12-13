package staxcxftest

import spock.lang.Specification
import util.handlers.XmlStaxHandler

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
}
