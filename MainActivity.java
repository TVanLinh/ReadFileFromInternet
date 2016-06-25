package com.linhtran.vnua.readfilefrominternet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView txtNodung;
    EditText edtNhap;
    String str;
    Button btnClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNodung=(TextView)findViewById(R.id.txtNoiDung);
        edtNhap=(EditText)findViewById(R.id.edtNhap);
        btnClick=(Button)findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ReadXMl().execute(  edtNhap.getText().toString());
                    }
                });
            }
        });
    }

    private class ReadXMl extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String noidung=getXmlFromUrl(strings[0]);
            return noidung;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtNodung.setText(s);
            Toast.makeText(MainActivity.this,"OK Da load xong",Toast.LENGTH_LONG).show();
        }
    }
    private String getXmlFromUrl(String urlString)
    {
        String xml=null;
        try
        {
            //DefaultHttpClient lay toan bo du lieu trong ht tp do vao mot string
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlString);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            xml= EntityUtils.toString(entity, HTTP.UTF_8);
        } catch(ClientProtocolException e)
        {
            if(e!=null)
            // e.printStackTrace();
                Log.e("Lỗi", e.toString());
            //Toast.makeText(MainActivity.this,"Loi.!",Toast.LENGTH_LONG);
        }
        catch (IOException e)
        {
            if(e!=null)
                Log.e("Lỗi", e.toString());
          //  e.printStackTrace();
           // Toast.makeText(MainActivity.this,"Loi.!",Toast.LENGTH_LONG);
        }
        return xml;
    }

}
