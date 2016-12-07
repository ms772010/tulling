package com.example.administrator.tullingrobot;

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class HttpData extends AsyncTask<String,Void,String> {
    private String myword;
    private URL url=null;
    private HttpURLConnection urlConnection=null;
    private String returnword;
    private HttpGetDataListener Listener;
    public HttpData(String mywords,HttpGetDataListener Listener){
        this.myword=mywords;
        this.Listener=Listener;
    }

    @Override
    //http://www.tuling123.com/openapi/api
    protected String doInBackground(String... params) {
        try {
            url =new URL("http://www.tuling123.com/openapi/api?key=eb25a2730cad4c85bfa1031bd7e617ea&info="+myword);
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            //OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
           //writeStream(out);   //写要发送给网站的数据，写为json格式

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            returnword=MainActivity.readStream(in);    //读网站传回的json数据，输出string
            return returnword;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           urlConnection.disconnect();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Listener.getDataUrl(result);
        super.onPostExecute(result);
    }



}
