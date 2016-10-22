package com.example.administrator.tullingrobot;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;


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
            url =new URL("http://www.tuling123.com/openapi/api?key=eb25a2730cad4c85bfa1031bd7e617ea&info=hello");
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            //OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
           //writeStream(out);   //写要发送给网站的数据，写为json格式

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            int i;
            String content="";
            while ((i=in.read())!=-1){
                content=content+(char)i;
            }
            return content;

            //returnword=readStream(in);    //读网站传回的json数据，输出string
            //return returnword;

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

    private void writeStream(OutputStream out)throws IOException{
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writer.beginObject();
        writer.name("key").value("eb25a2730cad4c85bfa1031bd7e617ea");
        writer.name("info").value(myword);
        writer.name("userid").value("12345678");
        writer.endObject();

    }

    private String readStream(InputStream in) throws IOException{
        String robotsword = null;
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("info")){
                robotsword=reader.nextString();
            }
            else{
                reader.skipValue();
            }
        }
        return robotsword;
    }
}
