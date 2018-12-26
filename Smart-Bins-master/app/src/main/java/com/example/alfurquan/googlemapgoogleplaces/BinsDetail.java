package com.example.alfurquan.googlemapgoogleplaces;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.StringWriter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BinsDetail extends AppCompatActivity {

    ProgressBar cpogressBar;
    TextView progress;
    Bins bins;
    View view;
    int distance =21;
    int mydist,val1;
    String myurl ="http://bins-env.a9fhp3bw3z.us-east-2.elasticbeanstalk.com/ShowD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bins_detail);
        Bundle data = getIntent().getExtras();
        final String value = data.getString("name");
        String me = data.getString("id");
      //  final String me = String.valueOf(id1);
        Log.d("MyApp",me);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(value);
        cpogressBar = (ProgressBar) findViewById(R.id.circularProgressBar);
        progress = (TextView) findViewById(R.id.textView1);
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
      //  Bins bins = new Bins(me, "", "", "", "");
        String result="";
        try {
            com.google.gson.Gson gson = new com.google.gson.Gson ();
            Bins bins = new Bins(me," ","","","");
            String json = gson.toJson ( bins );
            Log.d("Json",json);
            java.net.URL url = new java.net.URL ( myurl );
            final java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection ();
            conn.setConnectTimeout ( 5000 );
            conn.setRequestProperty ( "Content-Type", "application/json; charset=UTF-8" );
            conn.setDoOutput ( true );
            conn.setDoInput ( true );
            conn.setRequestMethod ( "POST" );
            java.io.OutputStream os = conn.getOutputStream ();
            os.write ( json.getBytes ( "UTF-8" ) );
            os.close ();
            // read the response
            java.io.InputStream in = new java.io.BufferedInputStream ( conn.getInputStream () );
            result = org.apache.commons.io.IOUtils.toString ( in, "UTF-8" );
            Log.d("Myapp",result);
            try{
                mydist = Integer.parseInt(result);
                Log.d("abhi",mydist+"");
            }catch (NumberFormatException e){
                Log.d("Error", String.valueOf(e));
            }
            if(mydist!=0){
                mydist = distance - mydist;
            }
                int val = (mydist*100/distance);
            Log.d("abhi",val+"");
                cpogressBar.setIndeterminate(false);
                cpogressBar.setProgress(val);
                progress.setText(val+ "%full");
            in.close ();
            conn.disconnect ();

        } catch (Exception e) {
            //  Toast.makeText ( this.getActivity ().getApplicationContext (), ""+e, Toast.LENGTH_LONG ).show ();
            //pDialog.setMessage ( e.getMessage () );
            //  pDialog.dismiss ();
            //  pd.setMessage ( ""+e );
            Log.e ("Myapp","Exception",e);
            //Log.d ( result,"response" );
        }
            }



}
