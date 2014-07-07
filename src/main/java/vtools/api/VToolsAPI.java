package vtools.api;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import vtools.connect.HttpSender;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;


public class VToolsAPI {
    private static final Logger log = Logger.getLogger(VToolsAPI.class);
    private Properties prop;

    public VToolsAPI(String configFilePath){
        initProperties(configFilePath);
    }

    //play specified mediaFile for each phone number in csvFile
    public String createAutocallTask(HttpSender vtoolsSender, Map<String, String> params, Map<String, File> files, boolean useProxy)  {
        log.warn("DBG: " + "createAutocallTask() enter");
        String result = null;
        String request = (prop.getProperty("vtools.mainURL") + prop.getProperty("vtools.api.AUTOCALL_LINK"));
        try{
            params.put("mediaFileParamName", "FGREETING");
            params.put("csvFileParamName", "FLIST");
            result = vtoolsSender.sendToVtools(request, params, files, useProxy);

        } catch (Throwable x_ex){
            x_ex.printStackTrace();
        }

        return result;
    }

    public String getANIList(HttpSender vtoolsSender, Map<String, String> params, boolean useProxy)  {
        String result = null;
        String request = (prop.getProperty("vtools.mainURL") + prop.getProperty("vtools.api.GET_ANI_LINK"));
        try{
            result = vtoolsSender.sendToVtools(request, params, null, useProxy);

        } catch (Throwable x_ex){
            x_ex.printStackTrace();
        }

        return result;
    }

    public String getTaskStatus(HttpSender vtoolsSender, Map<String, String> params, boolean useProxy)  {
        String result = null;
        String request = (prop.getProperty("vtools.mainURL") + prop.getProperty("vtools.api.GET_STATUS_LINK"));
        try{
            result = vtoolsSender.sendToVtools(request, params, null, useProxy);

        } catch (Throwable x_ex){
            x_ex.printStackTrace();
        }

        return result;
    }

    public String checkBalance(HttpSender vtoolsSender, Map<String, String> params, boolean useProxy)  {
        String result = null;
        String request = (prop.getProperty("vtools.mainURL") + prop.getProperty("vtools.api.BALANCE_LINK"));
        try{
            result = vtoolsSender.sendToVtools(request, params, null, useProxy);

        } catch (Throwable x_ex){
            x_ex.printStackTrace();
        }

        return result;
    }

    public String createTemplate(HttpSender vtoolsSender, Map<String, String> params, boolean useProxy)  {
        String result = null;
        String request = (prop.getProperty("vtools.mainURL") + prop.getProperty("vtools.api.TEMPLATE_LINK"));
        try{
            result = vtoolsSender.sendToVtools(request, params, null, useProxy);

        } catch (Throwable x_ex){
            x_ex.printStackTrace();
        }

        return result;
    }

    public String uploadMediaFile(HttpSender vtoolsSender, Map<String, String> params, Map<String, File> files, String mediaFileId, boolean useProxy)  {
        String result = null;
        String request = (prop.getProperty("vtools.mainURL") + prop.getProperty("vtools.api.MEDIAFILE_LINK"));
        try{
            params.put("UserID", mediaFileId);
            params.put("mediaFileParamName", "userfile");
            result = vtoolsSender.sendToVtools(request, params, files, useProxy);

        } catch (Throwable x_ex){
            x_ex.printStackTrace();
        }

        return result;
    }

    public String makeCSVString(Map callData)  {
        if(callData.get("phone") == null){
            return "Error - phone number parameter is necessary";
        } else {
            String csvSeparator = ";";
            String lineSeparator = System.getProperty("line.separator");
            String result = "";
            result += callData.get("phone") + csvSeparator; //#1
            result += ((callData.get("descr") != null) ? callData.get("descr") : "") + csvSeparator; //#2
            result += ((callData.get("time") != null) ? callData.get("time") : "") + csvSeparator; //#3
            result += ((callData.get("fileId") != null) ? callData.get("fileId") : "") + csvSeparator; //#4
            result += ((callData.get("voiceId") != null) ? callData.get("voiceId") : "") + csvSeparator; //#5
            result += ((callData.get("voiceText") != null) ? callData.get("voiceText") : "") + csvSeparator; //#6
            result += csvSeparator + lineSeparator;

            return result;
        }
    }

    public String decode(String inputString)  {
        return Jsoup.parse(inputString).text();
    }

    private boolean initProperties(String configFilePath){
        prop = new Properties();
        try {
            InputStream is = new FileInputStream(configFilePath);
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


}
