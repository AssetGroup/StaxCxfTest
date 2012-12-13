package util.handlers;

import org.codehaus.stax2.ri.evt.CharactersEventImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;


public class XmlStaxHandler {

    private XMLEventReader eventReader;
    private BufferedReader reader;
    
    public XmlStaxHandler(File file) throws Exception{
        XMLInputFactory factory = XMLInputFactory.newInstance();
        reader = new BufferedReader(new FileReader(file));
        eventReader = factory.createXMLEventReader(reader);
    }
    
    /**
     * Here's the method that tests the parsing
     * @throws Exception
     */
    public void testParse() throws Exception{
        parseStartDocument(true);
        parseStartTag("root", true);
        
        String tmp = parseTaggedChars("first", true);
        if(!tmp.equals("1")) throw new Exception("Expected 1, found "+tmp);
        
        tmp = parseTaggedChars("second", true);
        if(!tmp.equals("2")) throw new Exception("Expected 2, found "+tmp);
        
        parseEndTag("root", false);
        
        close();
    }
    
    private void parseStartDocument(boolean newLine) throws Exception{
        XMLEvent xmlEvent = eventReader.nextEvent();
        
        if(xmlEvent == null) throw new Exception("No start document found");
        if(xmlEvent.getEventType() != XMLEvent.START_DOCUMENT)
            throw new Exception("Element is not start document");

//        if(newLine) parseNewLine();
    }
    
    private void parseStartTag(String tagName, boolean newLine) throws Exception{
        parseTag(tagName, true);
        if(newLine) parseNewLine();
    }
    
    private void parseEndTag(String tagName, boolean newLine) throws Exception{
        parseTag(tagName, false);
        if(newLine) parseNewLine();
    }
    
    private String parseTaggedChars(String tagName, boolean newLine) throws Exception{
        parseStartTag(tagName, false);
        String chars = parseChars();
        parseEndTag(tagName, newLine);
        return chars;
    }
    
    private void parseNewLine() throws Exception{
        String newLine = parseChars();
        if(newLine.equals(System.getProperty("line.separator"))) throw new Exception("No new line found");
    }
    
    private void parseTag(String tagName, boolean start) throws Exception{
        XMLEvent xmlEvent = eventReader.nextEvent();
        
        if(xmlEvent == null) throw new Exception("No element found for "+tagName);
        
        String parsedTag = null;
        
        if(start){
            if(xmlEvent.getEventType() != XMLEvent.START_ELEMENT)
                throw new Exception("Element "+tagName+" is not start tag");
            
            parsedTag = xmlEvent.asStartElement().getName().getLocalPart();
                    
        }else{
            if(xmlEvent.getEventType() != XMLEvent.END_ELEMENT)
                throw new Exception("Element "+tagName+" is not end tag");
            
            parsedTag = xmlEvent.asEndElement().getName().getLocalPart();
        }
        
        if(!parsedTag.equals(tagName))
            throw new Exception("Invalid tag, expected: "+tagName+" found:"+parsedTag);
    }
    
    private String parseChars() throws Exception{
        XMLEvent xmlEvent = eventReader.nextEvent();
        
        if(xmlEvent == null) throw new Exception("No characters found");
        if(xmlEvent.getEventType() != XMLEvent.CHARACTERS)
            throw new Exception("No characters found");

        //todo: cto - I had to change this to get the parse working.  Apparently the toString was returning the event type
        return ((CharactersEventImpl)xmlEvent).getData();
    }
    
    private void close() throws Exception{
        eventReader.close();
        reader.close();
    }
    
}
