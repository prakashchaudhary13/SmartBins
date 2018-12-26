package com.example.alfurquan.googlemapgoogleplaces;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DeleteBinsAdapter extends RecyclerView.Adapter<DeleteBinsAdapter.MyViewHolder> {

    private Context context;
    private View view;
    private List<Bins> binsList;
    private AlertDialog.Builder builder;
    RequestParams params;
    AsyncHttpClient client;
    String myurl ="http://bins-env.a9fhp3bw3z.us-east-2.elasticbeanstalk.com/Rem";


    public DeleteBinsAdapter(Context context, List<Bins> binsList,AlertDialog.Builder builder) {
        this.builder = builder;
        this.context = context;
        this.binsList = binsList;
    }

    @Override
    public DeleteBinsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_bins, parent, false);

        return new DeleteBinsAdapter.MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(DeleteBinsAdapter.MyViewHolder holder, int position) {

        final Bins bins = binsList.get(position);
        holder.title.setText(bins.getLabel());
        holder.imageView.setImageResource(R.drawable.bin2);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                String id1 = bins.getId();
                                deleteBins(id1,v);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                builder.setMessage("Are you sure you want to remove" + " " + bins.getLabel() + "?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }
    public void deleteBins(String id,View v)
    {
       // progressBar.setVisibility(View.VISIBLE);
        String result="";
        try {
            com.google.gson.Gson gson = new com.google.gson.Gson ();
            Bins bins = new Bins(id," ","","","");
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
            if(result.equals("{\"status\":1}")){
         //        progressBar.setVisibility(View.GONE);
                  Snackbar mySnackbar = Snackbar.make(v,"Bin Deleted Succesfully",Snackbar.LENGTH_LONG);
                  mySnackbar.setAction("View Bins", new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Intent intent = new Intent(context,BinsActivity.class);
                          context.startActivity(intent);
                      }
                  });
                  mySnackbar.show();
            }
            else{
                //progressBar.setVisibility(View.GONE);
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
    public int getItemCount() {
        return binsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.event_type);
            imageView = (ImageView)itemView.findViewById(R.id.bin_img);
        }
    }
}
