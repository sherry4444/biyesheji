package com.luosee.common;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by server1 on 2016/11/15.
 */
public class HttpConnection {
    private String url;
    private String method="GET";
    private JSONObject jsonParam = new JSONObject();
    private Map request=new HashMap<String,Object>();

    public HttpConnection(String url)
    {
        this.url=url;
    }
    public HttpConnection(String url,String method)
    {
        this.url=url;
        this.method=method;
    }

    public void setParamKey(String key,Object value)
    {
        request.put(key,value);
    }

    public String EstablishConnection()
    {
        try {
            String data=jsonParam.fromObject(request).toString();

            URL httpUrl=new URL(HttpInfo.URL+url);
            URLConnection urlConnection=httpUrl.openConnection();
            HttpURLConnection httpURLConnection= (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(300000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-Type",HttpInfo.CONTENTTYPE);
            httpURLConnection.connect();
            OutputStreamWriter os=new OutputStreamWriter(httpURLConnection.getOutputStream());
            os.write(data);
            os.flush();
            os.close();

            BufferedReader bis=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String result=null;
            StringBuffer stringBuffer=new StringBuffer();
            while ((result=bis.readLine())!=null)
            {
                stringBuffer.append(result);
            }
            return  stringBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
