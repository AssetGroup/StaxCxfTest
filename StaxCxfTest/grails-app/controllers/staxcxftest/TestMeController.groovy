package staxcxftest

import util.handlers.XmlStaxHandler


class TestMeController {

    def index = {
        
        println "Testing..."
        
        try{
            File testFile = new File("TestFile.xml")
            
            XmlStaxHandler staxHandler = new XmlStaxHandler(testFile)
            staxHandler.testParse()
            
            println "Test OK"
            
        }catch(Exception e){
            println "Test KO:"
            e.printStackTrace()
        }
        
        redirect(uri: "/")
        return
    }
    
}
