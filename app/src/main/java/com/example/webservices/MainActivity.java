package com.example.webservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView myImage;
    TextView myText;
    Button downloadImageBtn,downloadJsonBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myImage=findViewById(R.id.imageView);
        myText=findViewById(R.id.textView);
        downloadImageBtn=findViewById(R.id.downloadImage);
        downloadJsonBtn=findViewById(R.id.downloadJson);
        downloadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    DownloadImageAsync downloadImageAsync=new DownloadImageAsync(getBaseContext(),myImage);
                    downloadImageAsync.execute("https://picsum.photos/600/250");
                }
            }
        });
        downloadJsonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    DownloadJsonAsync downloadJsonAsync=new DownloadJsonAsync(getBaseContext(),myText);
                    downloadJsonAsync.execute("https://jsonplaceholder.typicode.com/posts");
                }
            }
        });
    }
    public boolean isConnected(){
        ConnectivityManager connectivityManager= (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null){
            Toast.makeText(this,"No network is active",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!networkInfo.isConnected()){
            Toast.makeText(this,"The network is not connected",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!networkInfo.isAvailable()){
            Toast.makeText(this,"The network is not available",Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(this,"Network is Ok",Toast.LENGTH_SHORT).show();
        return true;
    }
}