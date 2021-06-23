package com.example.webservices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageAsync extends AsyncTask<String,String, Bitmap> {
    ImageView imageView;
    Context context;
    public DownloadImageAsync(Context context,ImageView imageView){
        this.context=context;
        this.imageView=imageView;
    }
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        String message=values[0];
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String imageUrl=strings[0];
        InputStream in;
        Bitmap imageBitmap=null;
        try{
            URL url=new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setAllowUserInteraction(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("GET");
            connection.connect();
            Integer resCode=connection.getResponseCode();
            if(resCode==HttpURLConnection.HTTP_OK){
                in=connection.getInputStream();
                imageBitmap= BitmapFactory.decodeStream(in);
            }else {
                publishProgress("Request Code = "+resCode.toString());
            }
        }catch (Exception e){
            publishProgress(e.getMessage().toString());
        }
        return imageBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }
}
