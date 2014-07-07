package vtools.parser;


import java.util.Map;


public class ParsedNode {
    private Map<String, String> values;
    private String nodeName;
    private String rawNodeText;

    public String getNodeName()  {
        return nodeName;
    }

    public void setNodeName(String nodeName)  {
        this.nodeName = nodeName;
    }

    public String getRawNodeText()  {
        return rawNodeText;
    }

    public void setRawNodeText(String rawNodeText)  {
        this.rawNodeText = rawNodeText;
    }

    public Map<String, String> getValues()  {
        return values;
    }

    public void setValues(Map<String, String> values)  {
        this.values = values;
    }


}
