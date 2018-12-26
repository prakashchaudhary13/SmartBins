package com.example.alfurquan.googlemapgoogleplaces;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteBinsActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 1;

    private RecyclerView recyclerView;
    Intent intent;
    private List<Bins> binsList;
    private ProgressBar progressBar;
    private DeleteBinsInterface deleteBinsInterface;
    private DeleteBinsAdapter deleteBinsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bins);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Delete Dustbins");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerBins);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        //    relativeLayout = (RelativeLayout)findViewById(R.id.re)
        progressBar.setVisibility(View.VISIBLE);
        binsList = new ArrayList<>();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        recyclerView.setLayoutManager(new LinearLayoutManager(DeleteBinsActivity.this));
        deleteBinsInterface = DeleteBinsApiClient.getBinsApiClient().create(DeleteBinsInterface.class);
        Call<List<Bins>> call = deleteBinsInterface.getBins("Show");
        call.enqueue(new Callback<List<Bins>>() {
            @Override
            public void onResponse(Call<List<Bins>> call, Response<List<Bins>> response) {
                progressBar.setVisibility(View.GONE);
                binsList = response.body();
                android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
                android.os.StrictMode.setThreadPolicy(policy);
                deleteBinsAdapter = new DeleteBinsAdapter(DeleteBinsActivity.this,binsList,builder);
                recyclerView.setAdapter(deleteBinsAdapter);
            }

            @Override
            public void onFailure(Call<List<Bins>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DeleteBinsActivity.this,"Check the internet connection",Toast.LENGTH_LONG).show();

            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        intent = new Intent(DeleteBinsActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.bins:
                        break;
                    case R.id.location:
                        intent = new Intent(DeleteBinsActivity.this, MapActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
