package com.example.alfurquan.googlemapgoogleplaces;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinsActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 1;
    Intent intent;
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private List<Bins> binsList;
    private MyBinsInterface myBinsInterface;
    private BinsAdapter binsAdapter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bins);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dustbins");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerBins);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    //    relativeLayout = (RelativeLayout)findViewById(R.id.re)
        progressBar.setVisibility(View.VISIBLE);
        binsList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(BinsActivity.this));
        myBinsInterface = BinsApiClient.getBinsApiClient().create(MyBinsInterface.class);
        Call<List<Bins>> call = myBinsInterface.getBins("Show");
        call.enqueue(new Callback<List<Bins>>() {
            @Override
            public void onResponse(Call<List<Bins>> call, Response<List<Bins>> response) {
                progressBar.setVisibility(View.GONE);
                binsList = response.body();
                binsAdapter = new BinsAdapter(BinsActivity.this,binsList);
                recyclerView.setAdapter(binsAdapter);
            }

            @Override
            public void onFailure(Call<List<Bins>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BinsActivity.this,"Check the internet connection",Toast.LENGTH_LONG).show();
            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                       intent = new Intent(BinsActivity.this,MainActivity.class);
                       startActivity(intent);
                       break;
                    case R.id.bins:
                        break;
                    case R.id.location:
                        intent = new Intent(BinsActivity.this, MapActivity.class);
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
