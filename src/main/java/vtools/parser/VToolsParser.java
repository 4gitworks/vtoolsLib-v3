package vtools.parser;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VToolsParser {
    private static final Logger log = Logger.getLogger(VToolsParser.class);

    public VToolsParser(){

    }

    //parse without predefined node names
    public List<ParsedNode> autoParse(String responseText){
        log.warn("DBG: " + "autoParse() enter");
        List<ParsedNode> nodes = new ArrayList<>();
        String[] results = getNodeName(responseText);
        while(results[0] != null){
            if(results[0].contains("=")){
                int index1 = results[0].indexOf("<") + 1;
                int index2 = results[0].indexOf(" ");
                if(index1 != -1 && index2 != -1 ){
                    ParsedNode parsedNode = new ParsedNode();
                    parsedNode.setNodeName(results[0].substring(index1, index2));
                    parsedNode.setValues(getValues(results[0]));
                    parsedNode.setRawNodeText(results[0]);
                    nodes.add(parsedNode);
                }
            }
            results = getNodeName(results[1]);
        }


        return nodes;
    }

    private String[] getNodeName(String sourceText){
        //log.warn("DBG: " + "getNodeName() enter");
        String foundValue = null;
        int index1 = sourceText.indexOf("<");
        if(index1 >= 0){
            int index2 = sourceText.indexOf(">") + 1;
            if(index2 == -1){
                index2 = sourceText.indexOf(" ") + 1;
            }
            if(index2 > 0 ) {
                foundValue = sourceText.substring(index1, index2);
                sourceText = sourceText.substring(index2, sourceText.length());
            }
        }
        String[] results = new String[2];
        results[0] = foundValue;
        results[1] = sourceText;

        return results;
    }

    private String[] getValue(String sourceText){
        int indexEqual = sourceText.indexOf("=");
        String foundValue = null;
        if(indexEqual > 1){
            foundValue = sourceText.substring(0, indexEqual);
            foundValue = foundValue.substring(foundValue.lastIndexOf(" ") + 1, indexEqual);
        }
        String name = foundValue;

        foundValue = null;
        int indexQuotes = sourceText.indexOf("\"", indexEqual + 2);
        if(indexQuotes > 1){
            foundValue = sourceText.substring(indexEqual + 2, indexQuotes);
            sourceText = sourceText.substring(indexEqual + foundValue.length() + 3, sourceText.length());
        } else{
            sourceText = null;
        }
        String[] results = new String[3];
        results[0] = name;
        results[1] = foundValue;
        results[2] = sourceText;

        return results;
    }

    private Map<String, String> getValues(String sourceText){
        TreeMap<String, String> values = new TreeMap<>();
        while(sourceText != null){
            String[] results = getValue(sourceText);
            sourceText = results[2];
            if(results[0] != null){
                values.put(results[0], results[1]);
            }
        }

        return values;
    }


}
