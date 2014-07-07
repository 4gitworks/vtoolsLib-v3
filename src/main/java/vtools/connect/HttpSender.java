package vtools.connect;


import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;



public class HttpSender {
    private static final Logger log = Logger.getLogger(HttpSender.class);

    private HttpClient httpclient = HttpClientBuilder.create().build();
    private HttpHost host = null;
    private HttpHost proxy = null;

    @SuppressWarnings("UnusedDeclaration")
    public HttpSender(Map<String, String> hostParams) {
        httpclient = HttpClientBuilder.create().build();
        initHost(hostParams);
    }

    public HttpSender(Map<String, String> hostParams, TreeMap<String, String> proxyParams) {
        httpclient = HttpClientBuilder.create().build();
        initHost(hostParams);
        initProxy(proxyParams);
    }

    @SuppressWarnings("WeakerAccess")
    public boolean initHost(Map<String, String> hostParams){
        host = new HttpHost(hostParams.get("mainURL"), Integer.parseInt(hostParams.get("port")), hostParams.get("type"));

        return checkHostInit(host);
    }

    @SuppressWarnings("WeakerAccess")
    public boolean initProxy(Map<String, String> proxyParams){
        proxy = new HttpHost(proxyParams.get("proxyURL"), Integer.parseInt(proxyParams.get("port")), proxyParams.get("type"));

        return checkHostInit(proxy);
    }

    private boolean checkHostInit(HttpHost host){
        return ((host.getHostName() != null && host.getPort() != -1 && host.getSchemeName() != null));
    }

    public String sendToVtools(String request, Map<String, String> params, Map<String, File> files, boolean useProxy) throws IOException {
        log.warn("DBG: " + "sendToVtools() enter");

        MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );
        HttpPost post = new HttpPost(request);
        for(String param: params.keySet()){
            entity.addPart( param, new StringBody(( params.get(param) ), "text/plain" , Charset.forName("windows-1251")));
        }
        if(files != null){
            for( String fileName : files.keySet()) {
                if(fileName.equals("csvFile")) {
                    entity.addPart(params.get("csvFileParamName"), new FileBody(( files.get("csvFile")), "text/plain"));
                } else if(fileName.equals("mediaFile")) {
                    entity.addPart(params.get("mediaFileParamName"), new FileBody((files.get("mediaFile")), "audio/wav"));
                }
            }
        }
        post.setEntity(entity);
        HttpResponse response;
        if(useProxy){
            if(checkHostInit(proxy)){
                DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
                httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();

                response = httpclient.execute(host, post);
            } else{
                return "Error: you tried to invoke sending via Proxy server without initializing it";
            }
        } else {
            response = httpclient.execute(post);
        }

        HttpEntity responseEntity = response.getEntity();

        return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
    }

}
