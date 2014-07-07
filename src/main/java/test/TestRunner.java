package test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import vtools.parser.ParsedNode;
import vtools.parser.VToolsParser;
import vtools.connect.HttpSender;
import org.apache.commons.codec.digest.DigestUtils;
import vtools.api.VToolsAPI;


import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;


public class TestRunner {
    private static final Logger log = Logger.getLogger(TestRunner.class);
    private static Properties prop;
    private VToolsAPI vToolsAPIInstance;
    private HttpSender vtoolsSender;
    private VToolsParser vToolsParser;

    public static void main(String[] args) {
        TestRunner testRunnerInstance = new TestRunner();
        testRunnerInstance.initProperties("src\\main\\java\\test\\vtools.properties");
        log.warn("DBG: " + "main.java.test.TestRunner() enter");


        testRunnerInstance.getANIList();
        //testRunnerInstance.createTemplate();
        //testRunnerInstance.createAutocallTask();
        //testRunnerInstance.createAutocallTaskTempCSV();
        //testRunnerInstance.getTaskStatus();
        //testRunnerInstance.uploadMediaFile();
        testRunnerInstance.checkBalance();



        log.warn("DBG: " + "main.java.test.TestRunner() exit");

    }

    public void checkBalance(){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("username", prop.getProperty("vtools.username"));
        params.put("password", DigestUtils.md5Hex(prop.getProperty("vtools.password")));

        String result = vToolsAPIInstance.checkBalance(vtoolsSender, params, false);
        //log.warn("result: " + result);

        //parse result
        List<ParsedNode> parsedNodes = vToolsParser.autoParse(result);

        printParsedResults(parsedNodes, "checkBalance");
    }

    ///*
    @SuppressWarnings("UnusedDeclaration")
    public void getTaskStatus(){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("username", prop.getProperty("vtools.username"));
        params.put("password", DigestUtils.md5Hex(prop.getProperty("vtools.password")));

        //String taskId = "17259047";
        String taskId = "17265037";
        params.put("taskid", taskId);
        String result = vToolsAPIInstance.getTaskStatus(vtoolsSender, params, false);
        //log.warn("result: " + result);

        //parse result
        List<ParsedNode> parsedNodes = vToolsParser.autoParse(result);

        printParsedResults(parsedNodes, "getTaskStatus");
    }

    @SuppressWarnings("UnusedDeclaration")
    public void createAutocallTask(){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("username", prop.getProperty("vtools.username"));
        params.put("password", DigestUtils.md5Hex(prop.getProperty("vtools.password")));

        //this autocall config will play sound specified in mediaFile for each phone number in passed CSV file
        //for example string in CSV file:  89101234567;DESCR2;;choose;1;; - here will play sound specified in mediaFile
        //for details see "4. Autocall API" on http://www.virtualofficetools.ru/aiapi.html
        File mediaFile = new File("C:\\___files\\___notes\\misc\\sparta.mp3");
        //File mediaFile = new File("D:\\___files\\___notes\\choose.wav");
        String schemeId = "test7";
        params.put("schemeid", schemeId);
        //for temp CSV file usage example - see main.java.test.TestRunner.createAutocallTaskTempCSV()
        File csvFile = new File("C:\\___files\\___notes\\misc\\flist3.csv");
        TreeMap<String, File> files = new TreeMap<>();
        files.put("csvFile", csvFile);
        files.put("mediaFile", mediaFile);
        //result = vToolsAPIInstance.createAutocallTask(vtoolsSender, params, csvFile, mediaFile, useProxy);
        String result = vToolsAPIInstance.createAutocallTask(vtoolsSender, params, files, false);
        //log.warn("result: " + result);

        //parse result
        List<ParsedNode> parsedNodes = vToolsParser.autoParse(result);

        printParsedResults(parsedNodes, "createAutocallTask");
    }

    @SuppressWarnings("UnusedDeclaration")
    public void createTemplate(){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("username", prop.getProperty("vtools.username"));
        params.put("password", DigestUtils.md5Hex(prop.getProperty("vtools.password")));

        //this template config will allow play sound which ID is specified in passed CSV file for each phone number respectively
        //for details see "2. AI Template API" on http://www.virtualofficetools.ru/aiapi.html
        String templateName = "test7";
        String aniID = "247163";
        String checkPlayGreeting = "1";
        String greetingFromFile = "0";
        params.put("TemplateName", templateName);
        params.put("AniID", aniID);
        params.put("CheckPlayGreeting", checkPlayGreeting);
        params.put("GreetingFromFile", greetingFromFile);
        //params.put("TemplateDescription", "descr2");
        String result = vToolsAPIInstance.createTemplate(vtoolsSender, params, false);
        //log.warn("result: " + result);

        //parse result
        List<ParsedNode> parsedNodes = vToolsParser.autoParse(result);

        printParsedResults(parsedNodes, "createTemplate");
    }

    public void getANIList(){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("username", prop.getProperty("vtools.username"));
        params.put("password", DigestUtils.md5Hex(prop.getProperty("vtools.password")));

        String result = vToolsAPIInstance.getANIList(vtoolsSender, params, false);
        //log.warn("result: " + result);

        //parse result
        List<ParsedNode> parsedNodes = vToolsParser.autoParse(result);

        printParsedResults(parsedNodes, "createTemplate");
    }

    @SuppressWarnings("UnusedDeclaration")
    public void uploadMediaFile(){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("username", prop.getProperty("vtools.username"));
        params.put("password", DigestUtils.md5Hex(prop.getProperty("vtools.password")));

        //this upload specified mediaFile to server with ID "sparta" which you can use after that to play media on autocall task
        //for example you can specify this mediaFile ID in CSV: 89101234567;DESCR2;;sparta;1;;  - TODO currently not work
        //for details see "4. Autocall API" on http://www.virtualofficetools.ru/aiapi.html
        String mediaFileId = "sparta3";
        File mediaFile = new File("C:\\___files\\___notes\\misc\\sparta.mp3");
        TreeMap<String, File> files = new TreeMap<>();
        files.put("mediaFile", mediaFile);
        String result = vToolsAPIInstance.uploadMediaFile(vtoolsSender, params, files, mediaFileId, false);
        //log.warn("result: " + result);

        //parse result
        List<ParsedNode> parsedNodes = vToolsParser.autoParse(result);

        printParsedResults(parsedNodes, "createTemplate");
    }

    public String makeCSVString(){
        TreeMap<String, String> callData = new TreeMap<>();
        String phone = "89101234567";
        /*
        String descr = "";
        String time = "";
        String fileId = "";
        String voiceId = "";
        String voiceText = "";

        callData.put("descr", descr);
        callData.put("time", time);
        callData.put("fileId", fileId);
        callData.put("voiceId", voiceId);
        callData.put("voiceText", voiceText);
        */
        callData.put("phone", phone);
        VToolsAPI vToolsAPIInstance = new VToolsAPI("src\\main\\java\\test\\vtools.properties");
        String result = vToolsAPIInstance.makeCSVString(callData);
        log.warn("result: " + result);

        return result;
    }

    public String makeCSVString(String phone){
        TreeMap<String, String> callData = new TreeMap<>();
        //String phone = "89101234567";
        /*
        String descr = "";
        String time = "";
        String fileId = "";
        String voiceId = "";
        String voiceText = "";

        callData.put("descr", descr);
        callData.put("time", time);
        callData.put("fileId", fileId);
        callData.put("voiceId", voiceId);
        callData.put("voiceText", voiceText);
        */
        callData.put("phone", phone);
        VToolsAPI vToolsAPIInstance = new VToolsAPI("src\\main\\java\\test\\vtools.properties");
        String result = vToolsAPIInstance.makeCSVString(callData);
        log.warn("result: " + result);

        return result;
    }

    private boolean initProperties(String configFilePath){
        prop = new Properties();
        try {
            InputStream is = new FileInputStream(configFilePath);
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PropertyConfigurator.configure(prop.getProperty("log4j.configFile"));

        TreeMap<String, String> hostParams = new TreeMap<>();
        hostParams.put("mainURL", prop.getProperty("vtools.mainURL"));
        hostParams.put("port", prop.getProperty("vtools.mainPort"));
        hostParams.put("type", prop.getProperty("vtools.mainType"));
        TreeMap<String, String> proxyParams = new TreeMap<>();
        proxyParams.put("proxyURL", prop.getProperty("vtools.proxyURL"));
        proxyParams.put("port", prop.getProperty("vtools.proxyPort"));
        proxyParams.put("type", prop.getProperty("vtools.proxyType"));

        vToolsAPIInstance = new VToolsAPI("src\\main\\java\\test\\vtools.properties");
        vtoolsSender = new HttpSender(hostParams, proxyParams);
        //HttpSender vtoolsSender = new HttpSender(hostParams);

        vToolsParser = new VToolsParser();

        return true;
    }

    private boolean printParsedResults(List<ParsedNode> parsedNodes, String methodName){
        log.warn("\n\n===========");
        log.warn("results for " + methodName + ":");
        for(ParsedNode parsedNode: parsedNodes){
            log.warn("===========");
            log.warn("results for node '" + parsedNode.getNodeName() + "':");
            TreeMap<String, String> values = new TreeMap<>(parsedNode.getValues());
            for(String key: values.keySet()){
                log.warn(key + "='" + values.get(key) + "'");
                if(key.equals("jobstatusstr")){
                    log.warn(key + "='" + vToolsAPIInstance.decode(values.get(key)) + "'");
                }
            }
        }
        log.warn("===========");

        return true;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void createAutocallTaskTempCSV(){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("username", prop.getProperty("vtools.username"));
        params.put("password", DigestUtils.md5Hex(prop.getProperty("vtools.password")));

        //this autocall config will play sound specified in mediaFile for each phone number in passed CSV file
        //for example string in CSV file:  89101234567;DESCR2;;choose;1;; - here will play sound specified in mediaFile
        //for details see "4. Autocall API" on http://www.virtualofficetools.ru/aiapi.html
        File mediaFile = new File("C:\\___files\\___notes\\misc\\sparta.mp3");
        String schemeId = "test7";
        params.put("schemeid", schemeId);
        String csvString = this.makeCSVString();
        String csvString2 = this.makeCSVString("89102234567");
        File tempCSVFile = new File("../tempFile.csv");
        try{
            PrintStream printStream = new PrintStream(new FileOutputStream(tempCSVFile));
            printStream.print(csvString);
            printStream.print(csvString2);
            log.warn("tempCSVFile.toString()= " + tempCSVFile.toString());
            TreeMap<String, File> files = new TreeMap<>();
            files.put("csvFile", tempCSVFile);
            files.put("mediaFile", mediaFile);
            BufferedReader in = new BufferedReader(new FileReader(tempCSVFile));
            String line;
            while((line = in.readLine()) != null){
                log.warn(line);
            }
            in.close();
            String result = vToolsAPIInstance.createAutocallTask(vtoolsSender, params, files, false);

            //parse result
            List<ParsedNode> parsedNodes = vToolsParser.autoParse(result);

            printParsedResults(parsedNodes, "createAutocallTaskTempCSV");
        } catch(IOException ioe){
            log.warn("Error - CSV file handling problem");
        }
    }

}
