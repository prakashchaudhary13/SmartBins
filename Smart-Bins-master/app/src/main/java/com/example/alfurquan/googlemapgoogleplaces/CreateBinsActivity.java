package com.example.alfurquan.googlemapgoogleplaces;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.apache.commons.io.IOUtils;

public class CreateBinsActivity extends AppCompatActivity implements LocationListener {

    Button create;
    EditText id,label;
    String mid,mlabel;
    RequestParams params;
    AsyncHttpClient client;
    ProgressBar progressBar;
    Double latitude,longitude;
    CreateBinsInterface createBinsInterface;
    LocationManager locationManager;
    String myurl ="http://bins-env.a9fhp3bw3z.us-east-2.elasticbeanstalk.com/Add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bins);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Dustbins");
        id = (EditText)findViewById(R.id.binid);
        label = (EditText)findViewById(R.id.binlabel);
        create = (Button)findViewById(R.id.create_bin);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        final Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mid = id.getText().toString();
                mlabel = label.getText().toString();
                if(!mid.isEmpty() && !mlabel.isEmpty())
                    {
   //                     onLocationChanged(location);

                        createBins(mid,mlabel,v);
                      }
                else
                    Snackbar.make(v,"All fields are required",Snackbar.LENGTH_LONG).show();
            }
        });

    }
    void createBins(String mid,String mlabel,final View v){
        progressBar.setVisibility(View.VISIBLE);
        String result="";
        try {
            Gson gson = new Gson ();
            String lat,lon;
            lat = String.valueOf(latitude);
            lon = String.valueOf(longitude);
            Log.d("Myapp" , lat +"  " + lon);
            Bins bins = new Bins(mid,lat,mlabel,lon,"0");
            String json = gson.toJson ( bins );
            Log.d("Json",json);
            java.net.URL url = new java.net.URL ( myurl );
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection ();
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
            result = IOUtils.toString ( in, "UTF-8" );
            Log.d("Myapp",result);
            if(result.equals("{\"status\":1}")){
                 progressBar.setVisibility(View.GONE);
                Snackbar mySnackbar =  Snackbar.make(v,"Bin Created Succesfully",Snackbar.LENGTH_LONG);
                mySnackbar.setAction("View Bins", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateBinsActivity.this,BinsActivity.class);
                        startActivity(intent);
                    }
                });
                mySnackbar.show();
                   // Intent intent = new Intent(CreateBinsActivity.this,BinsActivity.class);
                   // startActivity(intent);
            }
            else{
                progressBar.setVisibility(View.GONE);
                Snackbar.make(v,"Something went wrong",Snackbar.LENGTH_LONG).show();
                }
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

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

