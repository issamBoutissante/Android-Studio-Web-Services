package com.example.webservices;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadJsonAsync extends AsyncTask<String,String,String> {
    TextView myTextView;
    Context context;
    public DownloadJsonAsync(Context context,TextView textView) {
        this.myTextView=textView;
        this.context=context;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        String message=values[0];
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    @Override
    protected String doInBackground(String... strings) {
        String JsonUrl=strings[0];
        InputStream myInputStream=null;
        BufferedReader myBufferReader=null;
        String JsonText="";
        try {
            URL url=new URL(JsonUrl);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setAllowUserInteraction(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("GET");
            connection.connect();
            Integer resCode=connection.getResponseCode();
            if(resCode==HttpURLConnection.HTTP_OK){
                myInputStream=connection.getInputStream();
                myBufferReader = new BufferedReader(new InputStreamReader(myInputStream));
                String tempLine;
                while ((tempLine=myBufferReader.readLine())!=null){
                    JsonText+=tempLine+"\n";
                }
            }else {
                publishProgress("Response Code"+resCode.toString());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            publishProgress("there was an error");
        }
        finally {
            try {
                if (myBufferReader!=null){
                    myBufferReader.close();
                }
                if(myInputStream!=null){
                    myInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JsonText;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null){
            myTextView.setText(s);
        }else {
            publishProgress("the json is empty");
        }
    }
}
