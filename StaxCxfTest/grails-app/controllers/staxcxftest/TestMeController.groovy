package staxcxftest

import util.handlers.XmlStaxHandler


class TestMeController {

    def index = {
        
        println "Testing..."
        
        try{

            
            render "Test OK"
            return
            
        }catch(Exception e){
            println "Test KO:"
            e.printStackTrace()
            render e.toString()
            return
        }
        
        redirect(uri: "/")
        return
    }
    
}
